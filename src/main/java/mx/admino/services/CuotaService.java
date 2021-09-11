package mx.admino.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.admino.models.Cuota;

public interface CuotaService {

	Page<Cuota> findAll(Pageable pageable);

	Cuota save(Cuota cuota);

	Cuota findById(String id);

}
