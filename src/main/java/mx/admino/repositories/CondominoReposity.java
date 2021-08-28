package mx.admino.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import mx.admino.models.Condomino;

@Repository
public interface CondominoReposity extends MongoRepository<Condomino, String> {

	Condomino findByInterior(String interior);

}
