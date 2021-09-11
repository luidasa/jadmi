package mx.admino.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mx.admino.exceptions.CuotaNotFound;
import mx.admino.models.Cuota;
import mx.admino.repositories.CuotaRepository;
import mx.admino.services.CuotaService;

@Service
public class CuotaServiceImpl implements CuotaService {

	@Autowired
	CuotaRepository cuotaRepository;
	
	@Override
	public Page<Cuota> findAll(Pageable pageable) {
		return cuotaRepository.findAll(pageable);
	}

	@Override
	public Cuota save(Cuota cuota) {
		return cuotaRepository.save(cuota);
	}

	@Override
	public Cuota findById(String id) {
		return cuotaRepository.findById(id).orElseThrow(() -> new CuotaNotFound());
	}

}
