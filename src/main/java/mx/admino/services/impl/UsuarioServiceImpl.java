package mx.admino.services.impl;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
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

		return repository.save(usuario);
	}
}
