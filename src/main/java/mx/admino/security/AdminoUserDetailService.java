package mx.admino.security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import mx.admino.services.UsuarioService;
import org.springframework.stereotype.Service;

@Service
public class AdminoUserDetailService implements UserDetailsService {

	private static Logger logger = LoggerFactory.getLogger(AdminoUserDetailService.class);
	
	@Autowired
	UsuarioService service;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		logger.debug("Buscando autenticar al usuario " + username);
		
		UserDetails adminoUser = service.findByUsername(username);
		if (adminoUser == null) {
			throw new UsernameNotFoundException(username + " no esta registrado");
		}
		logger.debug(adminoUser.toString());
		return adminoUser;
	}
}
