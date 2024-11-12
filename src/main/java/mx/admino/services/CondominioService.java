package mx.admino.services;

import jakarta.validation.Valid;
import mx.admino.models.entities.Condominio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CondominioService {

	Condominio findFirst();

	Condominio save(@Valid Condominio condominio);

	Page<Condominio> findByAdministrador(String username, Pageable page);

    Condominio findByNombre(String nombre);

	Condominio findById(Long condominioId);
}
