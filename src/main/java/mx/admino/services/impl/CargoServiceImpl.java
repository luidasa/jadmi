package mx.admino.services.impl;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import mx.admino.exceptions.CargoNotFound;
import mx.admino.models.Cargo;
import mx.admino.models.CargoEstatus;
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
	public Page<Cargo> findByCondominoId(String id, Pageable pageable) {
		return cargoRepository.findByCondomino_Id(id, pageable);
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
	public List<Cargo> findByCondomino_IdAdFechaVencimientoBetween(String cid, Date fechaCorte, Date fechaVencimiento) {
		return cargoRepository.findByCondomino_IdAndFechaVencimientoBetween(cid, fechaCorte, fechaVencimiento);
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

}
