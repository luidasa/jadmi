package mx.admino.services;

import javax.validation.Valid;

import mx.admino.models.entities.Token;
import mx.admino.models.entities.Usuario;

public interface UsuarioService {

	Usuario findByUsername(String username);

	Usuario create(@Valid Usuario usuario);

    Usuario findByEmail(String correo);

    Token save(Token newToken);
}
