package mx.admino.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.admino.models.Usuario;
import mx.admino.repositories.UsuarioRepository;
import mx.admino.services.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	UsuarioRepository repository;

	@Override
	public Usuario findByUsername(String username) {
		return repository.findByUsername(username);
	}
	
	

}
