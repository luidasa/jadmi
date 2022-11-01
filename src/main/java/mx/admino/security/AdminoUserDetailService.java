package mx.admino.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import mx.admino.services.UsuarioService;
import org.springframework.stereotype.Service;

@Service
public class AdminoUserDetailService implements UserDetailsService {
	
	@Autowired
	UsuarioService service;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserDetails adminoUser = service.findByUsername(username);
		
		if (adminoUser == null) {
			throw new UsernameNotFoundException(username + " no esta registrado");
		}
		return adminoUser;
	}
}
