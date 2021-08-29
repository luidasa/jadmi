package mx.admino.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.admino.models.Factura;

public interface FacturaService {

	Page<Factura> findAll(Pageable pageable);

}
