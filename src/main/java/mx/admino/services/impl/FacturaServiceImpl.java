package mx.admino.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mx.admino.exceptions.FacturaNotFound;
import mx.admino.models.Cargo;
import mx.admino.models.CargoEstatus;
import mx.admino.models.Condomino;
import mx.admino.models.Cuota;
import mx.admino.models.CuotaEstatus;
import mx.admino.models.Factura;
import mx.admino.models.Pago;
import mx.admino.models.PagoEstatus;
import mx.admino.repositories.FacturaRepository;
import mx.admino.services.CargoService;
import mx.admino.services.CondominoService;
import mx.admino.services.CuotaService;
import mx.admino.services.FacturaService;
import mx.admino.services.PagoService;

@Service
public class FacturaServiceImpl implements FacturaService {

	@Autowired
	FacturaRepository facturaRepository;

	@Autowired
	CondominoService condominoService;
	
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
	public void generate(Date fechaInicio, Date fechaCorte, Date fechaVencimiento) {
		
		List<Condomino> condominos = condominoService.findAll();
		Interval periodoEC = new Interval(fechaInicio.getTime(), fechaCorte.getTime());
		// Generamos los cargos que esten en ese rango.
		List<Cuota> cuotas = cuotasService.findByEstatus(CuotaEstatus.REGISTRADO).stream()
				.filter(c -> {
						Interval periodoCuota = new Interval(c.getFechaInicio().getTime(), c.getFechaFin().getTime());
						return periodoCuota.overlaps(periodoEC);
				}).toList();
		condominos.stream().forEach(condomino -> {
				cuotas.forEach(cuota -> {
				Cargo cargo = new Cargo(condomino, cuota, fechaVencimiento);
				cargoService.save(cargo);
			});			
		});
		// Buscamos los pagos realizados
		List<Pago> pagos = pagosService.findByFechaPagadoBetweenAndStatus(fechaInicio, fechaCorte, PagoEstatus.PENDIENTE);
		
		// Buscamos las cuotas que van a aplicar en ese periodo y ademas les generamos los cargos a cada condominio.
		List<Cargo> cargos = cargoService.findByFechaVencimientoBetweenAndEstatus(fechaInicio, fechaCorte, CargoEstatus.PENDIENTE);
		
		condominos.stream().forEach(c -> {
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
			condominoService.save(c);
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
	}

	@Override
	public Page<Factura> findByCondomino_Id(String cid, Pageable pageable) {
		return facturaRepository.findByCondomino_Id(cid, pageable);
	}

	@Override
	public Factura findById(String id) {
		return facturaRepository.findById(id).orElseThrow(() -> new FacturaNotFound());
	}
}
