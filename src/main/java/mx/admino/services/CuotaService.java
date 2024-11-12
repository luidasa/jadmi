package mx.admino.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.admino.models.CuotaEstatus;
import mx.admino.models.entities.Cuota;

public interface CuotaService {

	Page<Cuota> findAll(Pageable pageable);

	Cuota save(Cuota cuota);

	Cuota findById(Long id);

	void deleteById(Long id);

	void schedule(Cuota cuota);

	List<Cuota> findByEstatus(CuotaEstatus estatus);
}
