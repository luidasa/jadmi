package mx.admino.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
		return condominoRepository.insert(condomino);
	}

}
