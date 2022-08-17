package mx.admino.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import mx.admino.models.entities.Condominio;

@Repository
public interface CondominioRepository extends MongoRepository<Condominio, String> {

	Condominio findFirstByOrderByNombreAsc();

	List<Condominio> findByNombre(String nombre);

}
