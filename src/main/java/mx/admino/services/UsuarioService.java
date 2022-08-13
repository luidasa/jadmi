package mx.admino.services;

import mx.admino.models.entities.Usuario;

public interface UsuarioService {

	Usuario findByUsername(String username);

	
}
