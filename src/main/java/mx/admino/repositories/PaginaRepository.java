package mx.admino.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import mx.admino.models.Pagina;

@Repository
public interface PaginaRepository extends MongoRepository<Pagina, String> {

	Pagina findBySlug(String slug);
}
