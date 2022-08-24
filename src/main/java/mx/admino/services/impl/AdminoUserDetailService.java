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
		
		System.out.println(username);
		var adminoUser = service.findByUsername(username);
		
		if (adminoUser == null) {
			System.out.println("No existe");
			throw new UsernameNotFoundException(username + " no esta registrado");
		}
		
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        adminoUser.getAuthorities()
          .forEach(role -> {
              grantedAuthorities.add(new SimpleGrantedAuthority(role));
          });

		System.out.println(adminoUser);
        return new User(
        		adminoUser.getUsername(),
        		adminoUser.getPassword(), 
        		grantedAuthorities);
	}
}
