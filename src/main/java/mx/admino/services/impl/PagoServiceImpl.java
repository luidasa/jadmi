package mx.admino.services.impl;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import mx.admino.models.entities.Casa;
import mx.admino.models.entities.Condominio;
import mx.admino.services.CondominioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import mx.admino.models.PagoEstatus;
import mx.admino.models.entities.Pago;
import mx.admino.repositories.PagoRepository;
import mx.admino.services.PagoService;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PagoServiceImpl implements PagoService {

	@Autowired
	MongoTemplate template;
	
	@Autowired
	PagoRepository pagoRepository;

	@Autowired
	CondominioService condominioService;
	
	@Override
	public Pago findById(String id) throws NotFoundException {
		return pagoRepository.findById(id).orElseThrow(() -> new NotFoundException());
	}

	@Override
	public Page<Pago> findAll(Pageable pageable) {
		return pagoRepository.findAll(pageable);
	}

	@Override
	public Pago save(@Valid Pago pago) {
		return pagoRepository.save(pago);
	}

	@Override
	public Page<Pago> findByCasa(Casa casa, Pageable pageable) {
		return pagoRepository.findByCasa(casa, pageable);
	}

	@Override
	public List<Pago> findByCasaAndFechaPagadoBetween(Casa casa, Date fechaInicial, Date fechaFinal) {
		
		return pagoRepository.findByCasaAndFechaPagadoBetween(casa, fechaInicial, fechaFinal);
	}

	@Override
	public List<Pago> saveAll(List<Pago> pagos) {
		return pagoRepository.saveAll(pagos);
	}

	@Override
	public List<Pago> findByFechaPagadoBetweenAndStatus(Date fechaInicio, Date fechaFinal, PagoEstatus estatus) {

		List<Pago> pagos = template.find(
				  Query.query(
						  Criteria
						  	.where("FechaPagado")
						  	.gte(fechaInicio)
						  	.lte(fechaFinal)
						  	.and("estatus")
						  	.is(estatus)), Pago.class);
		//return pagoRepository.findByFechaPagadoGreaterThanAndLessThan(fechaCorte, fechaFinal);
		return pagos;
	}

	@Override
	public List<Pago> findByCasa(Casa casa) {
		return pagoRepository.findByCasa(casa);
	}

	@Override
	public Page<Pago> findByCondominio(Condominio condominio, Pageable pageable) {
		return pagoRepository.findByCondominio(condominio, pageable);
	}

	@Override
	public List<Pago> findByFechaPagadoBetween(Date fechaInicio, Date fechaFinal) {

		List<Pago> pagos = template.find(
				  Query.query(
						  Criteria
						  	.where("FechaPagado")
						  	.gte(fechaInicio)
						  	.lte(fechaFinal)), Pago.class);
		//return pagoRepository.findByFechaPagadoGreaterThanAndLessThan(fechaCorte, fechaFinal);
		return pagos;
	}

	@Override
	public List<Pago> findByFechaPagadoBeforeAndEstatus(Date fechaFinal, PagoEstatus estatus) {
		List<Pago> pagos = template.find(
				  Query.query(
						  Criteria
						  	.where("FechaPagado").lte(fechaFinal)
						  	.and("estatus").is(estatus)), Pago.class);
		return pagos;
	}

	@Override
	public Pago update(Pago pagodb) {
		
		// Si ya esta conciliado y esta registrado el pago. Entonces movemos el estatus.
		if (pagodb.getConciliado() && (pagodb.getEstatus().equals(PagoEstatus.PENDIENTE))) {
			pagodb.setEstatus(PagoEstatus.CONCILIADO);
		}
		
		return this.save(pagodb);
	}

	@Override
	@Transactional(isolation = Isolation.DEFAULT)
	public Pago aplicarPago(Condominio condominio, Pago pago) {

		if (pago.getConciliado()) {
			condominio.ingreso(pago);
		}

		this.condominioService.save(condominio);
		return this.save(pago);
	}
}
