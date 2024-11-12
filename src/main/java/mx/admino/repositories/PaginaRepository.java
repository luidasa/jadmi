package mx.admino.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.admino.models.entities.Pagina;

@Repository
public interface PaginaRepository extends JpaRepository<Pagina, Long> {

	Pagina findBySlug(String slug);
}
