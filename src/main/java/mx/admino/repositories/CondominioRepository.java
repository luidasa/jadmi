package mx.admino.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.admino.models.entities.Condominio;

@Repository
public interface CondominioRepository extends JpaRepository<Condominio, Long> {

	Condominio findFirstByOrderByNombreAsc();

	List<Condominio> findByNombre(String nombre);

	Page<Condominio> findByAdministrador(String id, Pageable pageable);

    Condominio findFirstByNombre(String nombre);
}
