package mx.admino.services;

import mx.admino.models.entities.Condominio;
import mx.admino.models.entities.Presupuesto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PresupuestosService {

    Page<Presupuesto> findByCondominio(Condominio condominio, Pageable pageable);
}
