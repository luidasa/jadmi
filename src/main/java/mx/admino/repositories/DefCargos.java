package mx.admino.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import mx.admino.models.DefCargo;

@Repository
public interface DefCargos extends MongoRepository<DefCargo, String> {

}
