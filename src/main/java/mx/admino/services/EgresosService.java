package mx.admino.services;

import mx.admino.models.entities.Condominio;
import mx.admino.models.entities.Egreso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EgresosService {

    Page<Egreso> findByCondominio(Condominio condominio, Pageable pageable);

    Egreso crear(Egreso egreso);
}
