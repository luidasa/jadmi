package mx.admino.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import mx.admino.models.entities.Usuario;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
	
	Usuario findByUsername(String username);

    Usuario findByEmail(String correo);
}
