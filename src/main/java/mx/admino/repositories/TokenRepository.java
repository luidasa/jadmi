package mx.admino.repositories;

import mx.admino.models.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Token findOneByCode(String code);
}
