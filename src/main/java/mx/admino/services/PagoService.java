package mx.admino.services;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import mx.admino.models.entities.Casa;
import mx.admino.models.entities.Condominio;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.admino.models.PagoEstatus;
import mx.admino.models.entities.Pago;

public interface PagoService {

	Pago findById(String id) throws NotFoundException;

	Page<Pago> findAll(Pageable pageable);

	Pago save(@Valid Pago pago);

	Page<Pago> findByCasa(Casa casa, Pageable pageable);

	List<Pago> findByCasaAndFechaPagadoBetween(Casa casa, Date fechaInicial, Date fechaFinal);

	List<Pago> saveAll(List<Pago> pagos);

	List<Pago> findByFechaPagadoBetween(Date fechaCorte, Date fechaFinal);

	List<Pago> findByFechaPagadoBeforeAndEstatus(Date fechaCorte, PagoEstatus pendiente);

	List<Pago> findByFechaPagadoBetweenAndStatus(Date fechaInicio, Date fechaFinal, PagoEstatus estatus);

    List<Pago> findByCasa(Casa casa);

	Page<Pago> findByCondominio(Condominio condominio, Pageable pageable);

	Pago update(Pago pagodb);
}
