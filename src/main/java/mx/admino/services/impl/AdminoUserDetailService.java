package mx.admino.services.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import mx.admino.services.UsuarioService;

@Component
public class AdminoUserDetailService implements UserDetailsService {
	
	@Autowired
	UsuarioService service;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		var adminoUser = service.findByUsername(username);
		
		if (adminoUser == null) {
			throw new UsernameNotFoundException(username + " no esta registrado");
		}
		
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        adminoUser.getAuthorities()
          .forEach(role -> {
              grantedAuthorities.add(new SimpleGrantedAuthority(role));
          });

        return new User(adminoUser.getUsername(), adminoUser.getPassword(), grantedAuthorities);
	}
}
