package mx.admino.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.admino.models.entities.Pagina;
import mx.admino.repositories.PaginaRepository;
import mx.admino.services.PaginaService;

@Service
public class PaginaServiceImpl implements PaginaService {

	@Autowired
	PaginaRepository paginaRepository;
	
	@Override
	public Pagina findBySlug(String slug) {
		return paginaRepository.findBySlug(slug);
	}

	@Override
	public Pagina update(Pagina pagina) {
		return paginaRepository.save(pagina);
	}
}
