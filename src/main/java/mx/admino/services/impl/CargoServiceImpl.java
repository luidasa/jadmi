package mx.admino.services.impl;

import java.util.Date;
import java.util.List;

import jakarta.validation.Valid;

import mx.admino.models.entities.Casa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import mx.admino.exceptions.CargoNotFound;
import mx.admino.models.CargoEstatus;
import mx.admino.models.entities.Cargo;
import mx.admino.repositories.CargoRepository;
import mx.admino.services.CargoService;

@Service
public class CargoServiceImpl implements CargoService {
	
	@Autowired
	MongoTemplate template;
	
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
	public Cargo findById(String id) {
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
		List<Cargo> cargos = template.find(
				Query.query(Criteria.where("FechaVencimiento")
						.gte(fechaCorte)
						.lte(fechaFinal)
						.and("estatus")
						.is(CargoEstatus.PENDIENTE)), Cargo.class);
		//return cargoRepository.findByFechaVencimientoBetween(fechaCorte, fechaFinal);
		return cargos;
	}

	@Override
	public List<Cargo> findByFechaVencimientoBeforeAndEstatus(Date fechaCorte, CargoEstatus estatus) {
		return cargoRepository.findByFechaVencimientoBeforeAndEstatus(fechaCorte, estatus);
	}

	@Override
	public List<Cargo> findByFechaVencimientoBetweenAndEstatus(Date fechaInicio, Date fechaCorte,
			CargoEstatus estatus) {

		List<Cargo> cargos = template.find(
				Query.query(Criteria.where("FechaVencimiento")
						.gte(fechaInicio)
						.lte(fechaCorte)
						.and("estatus")
						.is(estatus)), Cargo.class);
		return cargos;
	}

	@Override
	public Page<Cargo> findByCasa(Casa casa, Pageable pageable) {
		return cargoRepository.findByCasa(casa, pageable);
	}
}
