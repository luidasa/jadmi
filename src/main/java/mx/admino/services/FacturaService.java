package mx.admino.services;

import java.util.Date;
import java.util.List;

import jakarta.validation.Valid;

import mx.admino.models.entities.Casa;
import mx.admino.models.entities.Condominio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.admino.models.FacturaFiltro;
import mx.admino.models.entities.CorteFactura;
import mx.admino.models.entities.Factura;

public interface FacturaService {

	Page<Factura> findAll(Pageable pageable);

	List<Factura> generate(Date fechaInicioCorte, Date fechaCorte, Date fechaVencimiento);

	Factura findById(String id);

	Page<Factura> search(FacturaFiltro facturaFormulario, Pageable pageable);

	List<Factura> generate(Date fechaCorte, Date fechaVencimiento);

	List<Factura> generate(@Valid CorteFactura facturaSolicitud);

    Page<Factura> findByCasa(Casa casa, Pageable pageable);

	Page<Factura> findByCondominio(Condominio condominio, Pageable pageable);
}
