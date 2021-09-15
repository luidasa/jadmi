package mx.admino.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.admino.models.Breadcrum;
import mx.admino.models.Condomino;
import mx.admino.models.Factura;
import mx.admino.services.CondominoService;
import mx.admino.services.FacturaService;

@Controller
public class FacturasController {

	@Autowired
	FacturaService facturaService;
	
	@Autowired
	CondominoService condominoService;
	
	@ModelAttribute
	private void getCondominios(Model model) {
		List<Condomino> condominos = condominoService.findAll();
		model.addAttribute("condominos", condominos);
	}	
	
	private List<Breadcrum> getBreadcrum(Factura factura) {

		List<Breadcrum> x = new ArrayList<Breadcrum>();
		x.add(new Breadcrum("Inicio", "/panel", false));
		x.add(new Breadcrum("Facturas", "/facturas", factura == null));
		if (factura != null) {
			x.add(new Breadcrum(factura.getFechaCorte().toString(), 
					"/facturas/" + factura.getId(),
					true));
		}
		return x ;
	}
	
	@GetMapping("/facturas")
	public String getIndex(
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer rows,
			@RequestParam(required = false) String cid,
			@ModelAttribute Factura factura,
			Model model) {
		
		Pageable pageable = PageRequest.of(page - 1, rows);
		Page<Factura> facturas ;
		if (cid == null) {
			facturas = facturaService.findAll(pageable);
		} else {
			facturas = facturaService.findByCondomino_Id(cid, pageable);
		}
		model.addAttribute("breadcrum", getBreadcrum(null));
		model.addAttribute("facturas", facturas);
		return "facturas/index";
	}
	
	@PostMapping("/facturas/generar")
	public String postGenerar(
			@Valid Factura factura,
			BindingResult binding,
			RedirectAttributes flash,
			Model model) {
		
		String viewName = "redirect:/facturas";
		
		if (binding.hasErrors()) {
			flash.addFlashAttribute("alert_danger", "Favor de verificar los datos que introduciste");
			return viewName;
		}
		
		facturaService.generate(
				factura.getFechaCorte(),
				factura.getFechaVencimiento());
		
		flash.addFlashAttribute("alert_success","Se agregan generaron las facturas para el periodo indicado");
		viewName = "redirect:/facturas";
		
		return viewName;
	}
	
	@GetMapping("/facturas/{id}")
	public String getFactura(
			@PathVariable String id,
			Model model) {
		return "facturas/factura";
	}
}
