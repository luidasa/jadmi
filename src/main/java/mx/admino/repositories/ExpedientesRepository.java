package mx.admino.repositories;

import mx.admino.models.entities.Archivo;
import mx.admino.models.entities.Casa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpedientesRepository extends JpaRepository<Archivo, Long> {
    Page<Archivo> findByCasa(Casa casa, Pageable pageable);

}
