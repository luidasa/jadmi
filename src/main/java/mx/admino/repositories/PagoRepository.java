package mx.admino.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import mx.admino.models.Pago;

@Repository
public interface PagoRepository extends MongoRepository<Pago, String> {

}
