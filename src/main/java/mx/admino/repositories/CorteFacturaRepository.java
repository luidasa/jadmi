package mx.admino.repositories;

import java.util.Date;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import mx.admino.models.CorteFactura;

@Repository
public interface CorteFacturaRepository extends MongoRepository<CorteFactura, String> {

	CorteFactura findFirstByFechaCorte(Date fechaCorte);

}
