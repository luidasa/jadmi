package mx.admino.services.impl;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mx.admino.exceptions.CargoNotFound;
import mx.admino.models.Cargo;
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

}
