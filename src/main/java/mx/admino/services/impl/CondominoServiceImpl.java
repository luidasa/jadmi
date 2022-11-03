package mx.admino.services.impl;

import java.util.List;

import mx.admino.models.entities.Casa;
import mx.admino.models.entities.Condominio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mx.admino.exceptions.CondominoNotFoundException;
import mx.admino.repositories.CondominoReposity;
import mx.admino.services.CasasService;

@Service
public class CondominoServiceImpl implements CasasService {

	@Autowired
	CondominoReposity condominoRepository;
	
	@Override
	public Page<Casa> findAll(Pageable pageable) {
		return condominoRepository.findAll(pageable);
	}

	@Override
	public Casa save(Casa casa) {
		
		return condominoRepository.save(casa);
	}

	@Override
	public Casa findByInterior(String interior) {
		return condominoRepository.findByInterior(interior);
	}

	@Override
	public Casa findById(String id) {
		return condominoRepository.findById(id).orElseThrow(() -> new CondominoNotFoundException());
	}

	@Override
	public List<Casa> findAll() {
		return condominoRepository.findAll();
	}

	@Override
	public Page<Casa> findByCondominio(Condominio condominio, Pageable pageable) {
		return condominoRepository.findByCondominio(condominio, pageable);
	}

	@Override
	public List<Casa> findByCondominio(Condominio condominio) {
		return condominoRepository.findByCondominio(condominio);
	}
}
