package mx.admino.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import mx.admino.models.Factura;

@Repository
public interface FacturaRepository extends MongoRepository<Factura, String> {

}
