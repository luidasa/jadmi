package mx.admino.services;

import java.util.List;

import mx.admino.models.entities.Casa;
import mx.admino.models.entities.Condominio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CasasService {

	Page<Casa> findAll(Pageable pageable);

	Casa save(Casa casa);

	Casa findByInterior(String interior);

	Casa findById(String id);

	List<Casa> findAll();

	Page<Casa> findByCondominio(Condominio condominio, Pageable pageable);
}
