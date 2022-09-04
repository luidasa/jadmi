package mx.admino.repositories;

import mx.admino.models.entities.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends MongoRepository<Token, String> {

    Token findOneByCode(String code);
}
