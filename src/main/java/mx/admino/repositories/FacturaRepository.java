package mx.admino.repositories;

import mx.admino.models.entities.Casa;
import mx.admino.models.entities.Condominio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import mx.admino.models.entities.Factura;

@Repository
public interface FacturaRepository extends MongoRepository<Factura, String> {

    Page<Factura> findByCasa(Casa casa, Pageable pageable);

    Page<Factura> findByCondominio(Condominio condominio, Pageable pageable);
}
