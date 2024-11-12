package mx.admino.services;

import mx.admino.models.entities.Archivo;
import mx.admino.models.entities.Casa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExpedientesService {

    Page<Archivo> findByCasa(Casa casa, Pageable pageable);

    Archivo insert(Archivo archivo);

    Archivo findById(Long aid);

    void deleteById(Long aid);
}
