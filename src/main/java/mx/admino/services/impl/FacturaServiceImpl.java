package mx.admino.services.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mx.admino.models.Factura;
import mx.admino.repositories.FacturaRepository;
import mx.admino.services.CargoService;
import mx.admino.services.CondominoService;
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
	
	@Override
	public Page<Factura> findAll(Pageable pageable) {
		return facturaRepository.findAll(pageable);
	}
	
	@Override
	public void generate(Date fechaCorte, Date fechaVencimiento) {
	
		condominoService.findAll().stream().forEach(c -> {
			Factura nuevaFactura = new Factura();
			nuevaFactura.setSaldoAnterior(c.getSaldo());
			nuevaFactura.setPagos(pagosService.findByCondomino_IdAndFechaPagadoBetween(c.getId(), fechaCorte, fechaVencimiento));
			nuevaFactura.setCargos(cargoService.findByCondomino_IdAdFechaVencimientoBetween(c.getId(), fechaCorte, fechaVencimiento));
			nuevaFactura.setSaldo(c.getSaldo() +
					nuevaFactura.getImporteCargos() +
					nuevaFactura.getImporteCargos());
			nuevaFactura.setCondomino(c);
			nuevaFactura.setFechaVencimiento(fechaVencimiento);
			nuevaFactura.setFechaCorte(fechaVencimiento);
			c.setSaldo(nuevaFactura.getSaldo());
			facturaRepository.save(nuevaFactura);
			condominoService.save(c);
		});
	}
	
	
}
