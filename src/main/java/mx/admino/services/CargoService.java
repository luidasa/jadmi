package mx.admino.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.admino.models.Cargo;

public interface CargoService {
	
	Page<Cargo> findAll(Pageable pageable);

}
