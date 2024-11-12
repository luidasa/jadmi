package mx.admino.services.impl;

import java.util.Date;
import java.util.List;

import jakarta.validation.Valid;

import mx.admino.models.entities.Casa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mx.admino.exceptions.CargoNotFound;
import mx.admino.models.CargoEstatus;
import mx.admino.models.entities.Cargo;
import mx.admino.repositories.CargoRepository;
import mx.admino.services.CargoService;

@Service
public class CargoServiceImpl implements CargoService {
	
	@Autowired
	CargoRepository cargoRepository;

	@Override
	public Page<Cargo> findAll(Pageable pageable) {
		return cargoRepository.findAll(pageable);
	}

	@Override
	public Cargo save(@Valid Cargo cargo) {
		return cargoRepository.save(cargo);
	}

	@Override
	public Cargo findById(Long id) {
		return cargoRepository.findById(id).orElseThrow(() -> new CargoNotFound());
	}

	@Override
	public List<Cargo> saveAll(List<Cargo> cargos) {
		List<Cargo> x = cargoRepository.saveAll(cargos);
		x.stream().forEach(c -> System.out.println(c));
		return x;
	}

	@Override
	public List<Cargo> findByFechaVencimientoBetween(Date fechaCorte, Date fechaFinal) {
		return cargoRepository.findByFechaVencimientoBetweenAndEstatus(fechaCorte, fechaFinal, CargoEstatus.PENDIENTE);
	}

	@Override
	public List<Cargo> findByFechaVencimientoBeforeAndEstatus(Date fechaCorte, CargoEstatus estatus) {
		return cargoRepository.findByFechaVencimientoBeforeAndEstatus(fechaCorte, estatus);
	}

	@Override
	public List<Cargo> findByFechaVencimientoBetweenAndEstatus(Date fechaInicio, Date fechaCorte,
			CargoEstatus estatus) {

		return cargoRepository.findByFechaVencimientoBetweenAndEstatus(fechaInicio, fechaCorte, estatus);
	}

	@Override
	public Page<Cargo> findByCasa(Casa casa, Pageable pageable) {
		return cargoRepository.findByCasa(casa, pageable);
	}
}
