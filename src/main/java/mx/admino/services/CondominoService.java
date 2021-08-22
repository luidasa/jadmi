package mx.admino.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.admino.models.Condomino;


public interface CondominoService {

	Page<Condomino> findAll(Pageable pageable);

	Condomino save(Condomino condomino);
}
