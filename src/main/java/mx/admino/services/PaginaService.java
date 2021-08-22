package mx.admino.services;

import mx.admino.models.Pagina;

public interface PaginaService {
		
	Pagina findBySlug(String slug);
	
	Pagina update(Pagina pagina);
}
