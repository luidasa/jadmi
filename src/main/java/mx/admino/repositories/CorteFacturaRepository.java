package mx.admino.repositories;

import java.util.Date;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import mx.admino.models.entities.CorteFactura;

@Repository
public interface CorteFacturaRepository extends MongoRepository<CorteFactura, String> {

	CorteFactura findFirstByFechaCorte(Date fechaCorte);

}
