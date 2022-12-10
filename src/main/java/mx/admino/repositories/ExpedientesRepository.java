package mx.admino.repositories;

import mx.admino.models.Archivo;
import mx.admino.models.entities.Casa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpedientesRepository extends MongoRepository<Archivo, String> {
    Page<Archivo> findByCasa(Casa casa, Pageable pageable);

}
