package mx.admino.repositories;

import mx.admino.models.entities.Condominio;
import mx.admino.models.entities.Egreso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GastosRepository extends MongoRepository<Egreso, String> {

    Page<Egreso> findByCondominio(Condominio condominio, Pageable pageable);
}
