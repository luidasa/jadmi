package mx.admino.repositories;

import mx.admino.models.entities.Banco;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BancoRepository extends MongoRepository<Banco, String> {
}
