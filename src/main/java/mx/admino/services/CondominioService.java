package mx.admino.services;

import javax.validation.Valid;

import mx.admino.models.Condominio;

public interface CondominioService {

	Condominio findFirst();

	Condominio save(@Valid Condominio condominio);
}
