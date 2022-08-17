package mx.admino.services;

import java.util.List;

import javax.validation.Valid;

import mx.admino.models.entities.Condominio;

public interface CondominioService {

	Condominio findFirst();

	Condominio save(@Valid Condominio condominio);

	List<Condominio> findByNombre(String nombre);
}
