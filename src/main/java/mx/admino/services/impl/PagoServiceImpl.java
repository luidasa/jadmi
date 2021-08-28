package mx.admino.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mx.admino.models.Pago;
import mx.admino.repositories.PagoRepository;
import mx.admino.services.PagoService;

@Service
public class PagoServiceImpl implements PagoService {

	@Autowired
	PagoRepository pagoRepository;
	
	@Override
	public Pago findById(String id) throws NotFoundException {
		return pagoRepository.findById(id).orElseThrow(() -> new NotFoundException());
	}

	@Override
	public Page<Pago> findAll(Pageable pageable) {
		return pagoRepository.findAll(pageable);
	}
}