package mx.admino.repositories;

import mx.admino.models.entities.Condominio;
import mx.admino.models.entities.Presupuesto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresupuestosRepository extends JpaRepository<Presupuesto, Long> {

    Page<Presupuesto> findByCondominio(Condominio condominio, Pageable pageable);
}
