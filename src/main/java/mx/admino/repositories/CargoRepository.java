package mx.admino.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import mx.admino.models.Cargo;

@Repository
public interface CargoRepository extends MongoRepository<Cargo, String> {

	Page<Cargo> findByCondomino_Id(String id, Pageable pageable);

}
