package mx.admino.repositories;

import mx.admino.models.entities.Condominio;
import mx.admino.models.entities.Presupuesto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresupuestosRepository extends MongoRepository<Presupuesto, String> {

    Page<Presupuesto> findByCondominio(Condominio condominio, Pageable pageable);
}
