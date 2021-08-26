package mx.admino.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mx.admino.models.Cargo;
import mx.admino.services.CargoService;

@Service
public class CargoServiceImpl implements CargoService {

	@Override
	public Page<Cargo> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
