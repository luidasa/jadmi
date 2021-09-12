package mx.admino.services;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.admino.models.Pago;

public interface PagoService {

	Pago findById(String id) throws NotFoundException;

	Page<Pago> findAll(Pageable pageable);

	Pago save(@Valid Pago pago);

	Page<Pago> findByCondominoId(String cid, Pageable pageable);

	List<Pago> findByCondomino_IdAndFechaPagadoBetween(String cid, Date fechaInicial, Date fechaFinal);

	List<Pago> saveAll(List<Pago> pagos);
}
