package mx.admino.services.impl;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import mx.admino.models.entities.Usuario;
import mx.admino.repositories.UsuarioRepository;
import mx.admino.services.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	UsuarioRepository repository;
	
	@Autowired
	PasswordEncoder encoder;

	@Override
	public Usuario findByUsername(String username) {
		return repository.findByUsername(username);
	}

	@Override
	public Usuario create(@Valid Usuario usuario) {
		
		usuario.setPassword(encoder.encode(usuario.getPassword()));
		return repository.save(usuario);
	}
}
