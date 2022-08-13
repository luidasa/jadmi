package mx.admino.services;

import mx.admino.models.Usuario;

public interface UsuarioService {

	Usuario findByUsername(String username);

	
}
