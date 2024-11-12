package mx.admino.repositories;

import mx.admino.models.entities.Casa;
import mx.admino.models.entities.Condominio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CondominoReposity extends JpaRepository<Casa, Long> {

	Casa findByInterior(String interior);

    Page<Casa> findByCondominio(Condominio condominio, Pageable pageable);

    List<Casa> findByCondominio(Condominio condominio);
}
