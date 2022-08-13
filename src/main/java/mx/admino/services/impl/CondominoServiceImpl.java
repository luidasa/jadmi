package mx.admino.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mx.admino.exceptions.CondominoNotFoundException;
import mx.admino.models.Condomino;
import mx.admino.repositories.CondominoReposity;
import mx.admino.services.CondominoService;

@Service
public class CondominoServiceImpl implements CondominoService {

	@Autowired
	CondominoReposity condominoRepository;
	
	@Override
	public Page<Condomino> findAll(Pageable pageable) {
		return condominoRepository.findAll(pageable);
	}

	@Override
	public Condomino save(Condomino condomino) {
		return condominoRepository.save(condomino);
	}

	@Override
	public Condomino findByInterior(String interior) {
		return condominoRepository.findByInterior(interior);
	}

	@Override
	public Condomino findById(String id) {
		return condominoRepository.findById(id).orElseThrow(() -> new CondominoNotFoundException());
	}

	@Override
	public List<Condomino> findAll() {
		return condominoRepository.findAll();
	}
}
