package mx.admino.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.admino.models.Cuota;
import mx.admino.models.CuotaEstatus;

public interface CuotaService {

	Page<Cuota> findAll(Pageable pageable);

	Cuota save(Cuota cuota);

	Cuota findById(String id);

	void deleteById(String id);

	void schedule(Cuota cuota);

	List<Cuota> findByEstatus(CuotaEstatus estatus);
}
