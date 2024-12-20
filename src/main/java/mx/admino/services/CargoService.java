package mx.admino.services;

import java.util.Date;
import java.util.List;

import jakarta.validation.Valid;

import mx.admino.models.entities.Casa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.admino.models.CargoEstatus;
import mx.admino.models.entities.Cargo;

public interface CargoService {
	
	Page<Cargo> findAll(Pageable pageable);

	Cargo save(@Valid Cargo cargo);

	Cargo findById(Long id);

	List<Cargo> saveAll(List<Cargo> cargos);

	List<Cargo> findByFechaVencimientoBetween(Date fechaCorte, Date fechaFinal);

	List<Cargo> findByFechaVencimientoBeforeAndEstatus(Date fechaCorte, CargoEstatus estatus);

	List<Cargo> findByFechaVencimientoBetweenAndEstatus(Date fechaInicio, Date fechaCorte, CargoEstatus pendiente);

    Page<Cargo> findByCasa(Casa casa, Pageable pageable);
}
