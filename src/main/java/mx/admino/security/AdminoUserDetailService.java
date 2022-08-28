package mx.admino.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
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
		
		System.out.println(username);
		var adminoUser = service.findByUsername(username);
		
		if (adminoUser == null) {
			System.out.println("No existe");
			throw new UsernameNotFoundException(username + " no esta registrado");
		}
		return adminoUser;
	}
}
