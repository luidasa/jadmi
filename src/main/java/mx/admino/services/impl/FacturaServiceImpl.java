package mx.admino.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import mx.admino.models.entities.*;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import mx.admino.exceptions.FacturaNotFound;
import mx.admino.models.CargoEstatus;
import mx.admino.models.CuotaEstatus;
import mx.admino.models.FacturaFiltro;
import mx.admino.models.PagoEstatus;
import mx.admino.models.entities.Casa;
import mx.admino.repositories.CorteFacturaRepository;
import mx.admino.repositories.FacturaRepository;
import mx.admino.services.CargoService;
import mx.admino.services.CasasService;
import mx.admino.services.CuotaService;
import mx.admino.services.FacturaService;
import mx.admino.services.PagoService;

@Service
public class FacturaServiceImpl implements FacturaService {
	
	@Autowired
	CorteFacturaRepository corteFacturaRepository;

	@Autowired
	FacturaRepository facturaRepository;
	
	@Autowired
	MongoTemplate template;

	@Autowired
    CasasService casasService;
	
	@Autowired
	PagoService pagosService;
	
	@Autowired
	CargoService cargoService;
	
	@Autowired
	CuotaService cuotasService;
	
	@Override
	public Page<Factura> findAll(Pageable pageable) {
		return facturaRepository.findAll(pageable);
	}
	
	@Override
	public List<Factura> generate(Date fechaInicio, Date fechaCorte, Date fechaVencimiento) {
		
		List<Factura> facturas = new ArrayList<>();
		List<Casa> casas = casasService.findAll();
		Interval periodoEC = new Interval(fechaInicio.getTime(), fechaCorte.getTime());
		// Generamos los cargos que esten en ese rango dependiendo de la cuota que se haya registrado.
		List<Cuota> cuotas = cuotasService.findByEstatus(CuotaEstatus.REGISTRADO).stream()
				.filter(c -> {
						Interval periodoCuota = new Interval(c.getFechaInicio().getTime(), c.getFechaFin().getTime());
						return periodoCuota.overlaps(periodoEC);
				}).collect(Collectors.toList());
		System.out.print("Cuotas a facturar: " + cuotas.size());
		casas.stream().forEach(condomino -> {
				cuotas.forEach(cuota -> {
				Cargo cargo = new Cargo(condomino, cuota, fechaVencimiento);
				cargoService.save(cargo);
			});
		});
		// Buscamos los pagos realizados y que no hayan sido facturados con anterioridad.
		List<Pago> pagos = pagosService.findByFechaPagadoBeforeAndEstatus(fechaCorte, PagoEstatus.PENDIENTE);
		
		// Buscamos las cuotas que van a aplicar en ese periodo y ademas les generamos los cargos a cada condominio.
		List<Cargo> cargos = cargoService.findByFechaVencimientoBetweenAndEstatus(fechaInicio, fechaCorte, CargoEstatus.PENDIENTE);
		
		casas.stream().forEach(c -> {
			Factura nuevaFactura = new Factura();
			nuevaFactura.setSaldoAnterior(c.getSaldo());
			nuevaFactura.setPagos(
					pagos.stream()
						.filter(p -> p.getCondomino().getId().equals(c.getId()))
						.map(p -> {
							p.setEstatus(PagoEstatus.FACTURADO); 
							return p;})
						.collect(Collectors.toList()));
			nuevaFactura.setCargos(
					cargos.stream()
						.filter(p -> p.getCondomino().getId().equals(c.getId()))
						.map(p -> {
							p.setEstatus(CargoEstatus.FACTURADO);
							return p;
						})
						.collect(Collectors.toList()));
			nuevaFactura.setSaldo(
					nuevaFactura.getSaldoAnterior() +
					nuevaFactura.getImporteCargos() -
					nuevaFactura.getImportePagos());
			nuevaFactura.setCondomino(c);
			nuevaFactura.setFechaVencimiento(fechaVencimiento);
			nuevaFactura.setFechaCorte(fechaCorte);
			c.setSaldo(nuevaFactura.getSaldo());
			nuevaFactura.getPagos().stream().forEach(pago -> pago.setEstatus(PagoEstatus.FACTURADO));
			nuevaFactura.getCargos().stream().forEach(cargo -> cargo.setEstatus(CargoEstatus.FACTURADO));
			facturaRepository.save(nuevaFactura);
			facturas.add(nuevaFactura);
			casasService.save(c);
		});

		pagosService.saveAll(
				pagos.stream()
				.map(p -> {
					p.setEstatus(PagoEstatus.FACTURADO);
					return p;
				})
				.collect(Collectors.toList()));

		cargoService.saveAll(
				cargos.stream()
					.map(p -> {
						p.setEstatus(CargoEstatus.FACTURADO);
						return p;
					})
					.collect(Collectors.toList()));
		return facturas;
	}

	@Override
	public Page<Factura> findByCondomino_Id(String cid, Pageable pageable) {
		return facturaRepository.findByCondomino_Id(cid, pageable);
	}

	@Override
	public Factura findById(String id) {
		return facturaRepository.findById(id).orElseThrow(() -> new FacturaNotFound());
	}


	@Override
	public Factura generate(Date fechaInicio, Date fechaCorte, Date fechaVencimiento, Casa casa) {
		// TODO Debe de implementarse el metodo que genera la factura de un condominio.
		return null;
	}


	@Override
	public Page<Factura> search(FacturaFiltro filtro, Pageable pageable) {
		
		Criteria criterios = Criteria.where("fechaCorte").is(filtro.getFechaCorte());
		
		if (filtro.hasCondomino()) {
			criterios.and("condomino.id").is(filtro.getCondomino().getId());
		}
		
		if (filtro.hasFecha()) {
			criterios.and("fechaCorte")
				.gte(filtro.getFechaMinimo())
				.lt(filtro.getFechaMaxima());
		}
		
		if (filtro.hasSaldo()) {
			criterios.and("saldo").gte(filtro.getSaldoMinimo()).lte(filtro.getSaldoMaximo());
		}
		
		Query query = Query.query(criterios);
		List<Factura> facturas = template.find(query.with(pageable), Factura.class);		
		return PageableExecutionUtils.getPage(
				facturas, 
		        pageable,
		        () -> template.count(Query.of(query).limit(-1).skip(-1), Factura.class));	
		}

	@Override
	public List<Factura> generate(Date fechaCorte, Date fechaVencimiento) {
		
		List<Factura> facturas = new ArrayList<>();
		List<Casa> casas = casasService.findAll();
		
		// Buscamos los pagos realizados y que no hayan sido facturados con anterioridad.
		List<Pago> pagos = pagosService.findByFechaPagadoBeforeAndEstatus(fechaCorte, PagoEstatus.PENDIENTE);
		
		// Buscamos las cuotas que van a aplicar en ese periodo y ademas les generamos los cargos a cada condominio.
		List<Cargo> cargos = cargoService.findByFechaVencimientoBeforeAndEstatus(fechaCorte, CargoEstatus.PENDIENTE);
		
		casas.stream().forEach(c -> {
			Factura nuevaFactura = new Factura();
			nuevaFactura.setSaldoAnterior(c.getSaldo());
			nuevaFactura.setPagos(
					pagos.stream()
						.filter(p -> p.getCondomino().getId().equals(c.getId()))
						.map(p -> {
							p.setEstatus(PagoEstatus.FACTURADO); 
							return p;})
						.collect(Collectors.toList()));
			nuevaFactura.setCargos(
					cargos.stream()
						.filter(p -> p.getCondomino().getId().equals(c.getId()))
						.map(p -> {
							p.setEstatus(CargoEstatus.FACTURADO);
							return p;
						})
						.collect(Collectors.toList()));
			nuevaFactura.setSaldo(
					nuevaFactura.getSaldoAnterior() +
					nuevaFactura.getImporteCargos() -
					nuevaFactura.getImportePagos());
			nuevaFactura.setCondomino(c);
			nuevaFactura.setFechaVencimiento(fechaVencimiento);
			nuevaFactura.setFechaCorte(fechaCorte);
			c.setSaldo(nuevaFactura.getSaldo());
			nuevaFactura.getPagos().stream().forEach(pago -> pago.setEstatus(PagoEstatus.FACTURADO));
			nuevaFactura.getCargos().stream().forEach(cargo -> cargo.setEstatus(CargoEstatus.FACTURADO));
			facturaRepository.save(nuevaFactura);
			facturas.add(nuevaFactura);
			casasService.save(c);
		});

		pagosService.saveAll(
				pagos.stream()
				.map(p -> {
					p.setEstatus(PagoEstatus.FACTURADO);
					return p;
				})
				.collect(Collectors.toList()));

		cargoService.saveAll(
				cargos.stream()
					.map(p -> {
						p.setEstatus(CargoEstatus.FACTURADO);
						return p;
					})
					.collect(Collectors.toList()));
		return facturas;
	}

	@Override
	public List<Factura> generate(@Valid CorteFactura corte) {

		corteFacturaRepository.save(corte);
		List<Factura> facturas = new ArrayList<>();
		List<Casa> casas = casasService.findAll();
		
		// Buscamos los pagos realizados y que no hayan sido facturados con anterioridad.
		List<Pago> pagos = pagosService.findByFechaPagadoBeforeAndEstatus(corte.getFechaCorte(), PagoEstatus.PENDIENTE);
		
		// Buscamos las cuotas que van a aplicar en ese periodo y ademas les generamos los cargos a cada condominio.
		List<Cargo> cargos = cargoService.findByFechaVencimientoBeforeAndEstatus(corte.getFechaCorte(), CargoEstatus.PENDIENTE);
		
		casas.stream().forEach(c -> {
			Factura nuevaFactura = new Factura();
			nuevaFactura.setSaldoAnterior(c.getSaldo());
			nuevaFactura.setPagos(
					pagos.stream()
						.filter(p -> p.getCondomino().getId().equals(c.getId()))
						.map(p -> {
							p.setEstatus(PagoEstatus.FACTURADO); 
							return p;})
						.collect(Collectors.toList()));
			nuevaFactura.setCargos(
					cargos.stream()
						.filter(p -> p.getCondomino().getId().equals(c.getId()))
						.map(p -> {
							p.setEstatus(CargoEstatus.FACTURADO);
							return p;
						})
						.collect(Collectors.toList()));
			nuevaFactura.setSaldo(
					nuevaFactura.getSaldoAnterior() +
					nuevaFactura.getImporteCargos() -
					nuevaFactura.getImportePagos());
			nuevaFactura.setCondomino(c);
			nuevaFactura.setFechaVencimiento(corte.getFechaVencimiento());
			nuevaFactura.setFechaCorte(corte.getFechaCorte());
			nuevaFactura.setCorte(corte);
			c.setSaldo(nuevaFactura.getSaldo());
			nuevaFactura.getPagos().stream().forEach(pago -> pago.setEstatus(PagoEstatus.FACTURADO));
			nuevaFactura.getCargos().stream().forEach(cargo -> cargo.setEstatus(CargoEstatus.FACTURADO));
			facturaRepository.save(nuevaFactura);
			facturas.add(nuevaFactura);
			casasService.save(c);
		});

		pagosService.saveAll(
				pagos.stream()
				.map(p -> {
					p.setEstatus(PagoEstatus.FACTURADO);
					return p;
				})
				.collect(Collectors.toList()));

		cargoService.saveAll(
				cargos.stream()
					.map(p -> {
						p.setEstatus(CargoEstatus.FACTURADO);
						return p;
					})
					.collect(Collectors.toList()));
		return facturas;
	}
}