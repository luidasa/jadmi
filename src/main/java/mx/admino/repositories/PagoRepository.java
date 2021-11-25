package mx.admino.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import mx.admino.models.Pago;
import mx.admino.models.PagoEstatus;

@Repository
public interface PagoRepository extends MongoRepository<Pago, String> {

	Page<Pago> findByCondomino_Id(String id, Pageable pageable);

	List<Pago> findByCondomino_idAndFechaPagadoBetween(String cid, Date fechaInicial, Date fechaFinal);

	List<Pago> findByFechaPagadoBetween(Date fechaCorte, Date fechaFinal);

	List<Pago> findByFechaPagadoBeforeAndEstatus(Date fechaCorte, PagoEstatus estatus);
}
