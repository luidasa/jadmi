package mx.admino.services.impl;

import jakarta.validation.Valid;

import mx.admino.exceptions.CondominioNotFound;
import mx.admino.exceptions.UsuarioNotFound;
import mx.admino.models.entities.Usuario;
import mx.admino.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mx.admino.models.entities.Condominio;
import mx.admino.repositories.CondominioRepository;
import mx.admino.services.CondominioService;

import java.util.List;

@Service
public class CondominioServiceImpl implements CondominioService {

	@Autowired
	UsuarioService usuarioSrv;

	@Autowired
	CondominioRepository condominioRepository;

	public Condominio findFirst() {
		return condominioRepository.findFirstByOrderByNombreAsc();
	}

	@Override
	public Condominio save(Condominio condominio) {
		var x = condominioRepository.save(condominio);
		System.out.println(x.getId() + "este es el id");
		return  x;
	}

	@Override
	public Page<Condominio> findByAdministrador(String username, Pageable pageable) {
		Usuario administrador = usuarioSrv.findByUsername(username);
		if (administrador == null) {
			throw new UsuarioNotFound(username);
		}
		return condominioRepository.findByAdministrador(administrador.getId(), pageable);
	}

	@Override
	public Condominio findByNombre(String nombre) {
		return condominioRepository.findFirstByNombre(nombre);
	}

	@Override
	public Condominio findById(String condominioId) {
		return condominioRepository.findById(condominioId).orElseThrow(() -> new CondominioNotFound());
	}
}
