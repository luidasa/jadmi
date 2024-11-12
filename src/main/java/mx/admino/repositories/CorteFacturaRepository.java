package mx.admino.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.admino.models.entities.CorteFactura;

@Repository
public interface CorteFacturaRepository extends JpaRepository<CorteFactura, Long> {

}
