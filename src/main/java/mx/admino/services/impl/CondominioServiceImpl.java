package mx.admino.services.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.admino.models.entities.Condominio;
import mx.admino.repositories.CondominioRepository;
import mx.admino.services.CondominioService;

@Service
public class CondominioServiceImpl implements CondominioService {

	@Autowired
	CondominioRepository condominioRepository;

	public Condominio findFirst() {
		return condominioRepository.findFirstByOrderByNombreAsc();
	}

	@Override
	public Condominio save(@Valid Condominio condominio) {
		return condominioRepository.save(condominio);
	}

	@Override
	public List<Condominio> findByNombre(String nombre) {

		return condominioRepository.findByNombre(nombre);
	}
}
