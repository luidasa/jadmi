package mx.admino.services;

import jakarta.validation.Valid;

import mx.admino.models.entities.Condominio;
import mx.admino.models.entities.Figura;
import mx.admino.models.entities.Token;
import mx.admino.models.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioService {

	Usuario findByUsername(String username);

	Usuario create(@Valid Usuario usuario);

    Usuario findByEmail(String correo);

    Token save(Token newToken);

    Token findTokenByCode(String code);

    Usuario save(Usuario usuario);

    Page<Figura> findByCondominio(Condominio condominio, Pageable pageable);
}
