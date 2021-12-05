package mx.admino.services;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.admino.models.Condomino;
import mx.admino.models.CorteFactura;
import mx.admino.models.Factura;
import mx.admino.models.FacturaFiltro;

public interface FacturaService {

	Page<Factura> findAll(Pageable pageable);

	List<Factura> generate(Date fechaInicioCorte, Date fechaCorte, Date fechaVencimiento);
	
	Factura generate(Date fechaInicio, Date fechaCorte, Date fechaVencimiento, Condomino condomino);

	Page<Factura> findByCondomino_Id(String cid, Pageable pageable);

	Factura findById(String id);

	Page<Factura> search(FacturaFiltro facturaFormulario, Pageable pageable);

	List<Factura> generate(Date fechaCorte, Date fechaVencimiento);

	List<Factura> generate(@Valid CorteFactura facturaSolicitud);
}
