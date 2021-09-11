package mx.admino.services;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.admino.models.Factura;

public interface FacturaService {

	Page<Factura> findAll(Pageable pageable);

	void generate(Date fechaCorte, Date fechaVencimiento);

	Page<Factura> findByCondomino_Id(String cid, Pageable pageable);

}
