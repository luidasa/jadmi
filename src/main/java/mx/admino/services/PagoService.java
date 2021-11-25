package mx.admino.services;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.admino.models.Pago;
import mx.admino.models.PagoEstatus;

public interface PagoService {

	Pago findById(String id) throws NotFoundException;

	Page<Pago> findAll(Pageable pageable);

	Pago save(@Valid Pago pago);

	Page<Pago> findByCondominoId(String cid, Pageable pageable);

	List<Pago> findByCondomino_IdAndFechaPagadoBetween(String cid, Date fechaInicial, Date fechaFinal);

	List<Pago> saveAll(List<Pago> pagos);

	List<Pago> findByFechaPagadoBetween(Date fechaCorte, Date fechaFinal);

	List<Pago> findByFechaPagadoBeforeAndEstatus(Date fechaCorte, PagoEstatus pendiente);

	List<Pago> findByFechaPagadoBetweenAndStatus(Date fechaInicio, Date fechaFinal, PagoEstatus estatus);

}
