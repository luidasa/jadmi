package mx.admino.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import mx.admino.models.Cuota;

@Repository
public interface CuotaRepository extends MongoRepository<Cuota, String> {

}
