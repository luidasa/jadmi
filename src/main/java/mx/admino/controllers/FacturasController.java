package mx.admino.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mx.admino.models.Breadcrum;
import mx.admino.models.Factura;
import mx.admino.services.FacturaService;

@Controller
public class FacturasController {

	@Autowired
	FacturaService facturaService;
	
	@GetMapping("/facturas")
	public String getIndex(
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer rows,
			@RequestParam(required = false) String cid,
			Model model) {
		
		List<Breadcrum> breadcrum = Arrays.asList(new Breadcrum[] {
				new Breadcrum("Inicio", "/", false),
		        new Breadcrum("Facturas", "/facturas", true)
			});
		Pageable pageable = PageRequest.of(page, rows);
		Page<Factura> facturas = facturaService.findAll(pageable);
		model.addAttribute("breadcrum", breadcrum);
		model.addAttribute("facturas", facturas);
		return "facturas/index";
	}
}
