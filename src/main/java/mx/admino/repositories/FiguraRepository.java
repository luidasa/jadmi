package mx.admino.repositories;

import mx.admino.models.entities.Condominio;
import mx.admino.models.entities.Figura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FiguraRepository extends MongoRepository<Figura, String> {
    Page<Figura> findByCondominio(Condominio condominio, Pageable pageable);
}
