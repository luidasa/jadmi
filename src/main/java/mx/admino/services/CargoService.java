package mx.admino.services;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.admino.models.Cargo;
import mx.admino.models.CargoEstatus;

public interface CargoService {
	
	Page<Cargo> findAll(Pageable pageable);

	Page<Cargo> findByCondominoId(String id, Pageable pageable);

	Cargo save(@Valid Cargo cargo);

	Cargo findById(String id);

	List<Cargo> findByCondomino_IdAdFechaVencimientoBetween(String cid, Date fechaCorte, Date fechaVencimiento);

	List<Cargo> saveAll(List<Cargo> cargos);

	List<Cargo> findByFechaVencimientoBetween(Date fechaCorte, Date fechaFinal);

	List<Cargo> findByFechaVencimientoBeforeAndEstatus(Date fechaCorte, CargoEstatus estatus);

	List<Cargo> findByFechaVencimientoBetweenAndEstatus(Date fechaInicio, Date fechaCorte, CargoEstatus pendiente);

}
