package mx.admino.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import mx.admino.models.Pago;

@Repository
public interface PagoRepository extends MongoRepository<Pago, String> {

	Page<Pago> findByCondomino_Id(String id, Pageable pageable);

}
