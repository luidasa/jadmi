package mx.admino.services;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.admino.models.Cargo;

public interface CargoService {
	
	Page<Cargo> findAll(Pageable pageable);

	Page<Cargo> findByCondominoId(String id, Pageable pageable);

	Cargo save(@Valid Cargo cargo);

	Cargo findById(String id);

}
