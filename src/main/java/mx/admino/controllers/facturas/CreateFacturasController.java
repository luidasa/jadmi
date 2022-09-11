package mx.admino.controllers.facturas;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.admino.models.entities.CorteFactura;
import mx.admino.services.CondominioService;
import mx.admino.services.CasasService;
import mx.admino.services.FacturaService;

@Controller
@RequestMapping("/admin/facturas")
public class CreateFacturasController {

	@Autowired
	FacturaService facturaService;
	
	@Autowired
    CasasService casasService;
	
	@Autowired
	CondominioService condominioService;
	
	
	@PostMapping("/generar")
	public String postGenerar(
			@ModelAttribute @Valid CorteFactura facturaSolicitud,
			BindingResult binding,
			RedirectAttributes flash,
			Model model) {
		
		String viewName = "redirect:/facturas";
		
		if (binding.hasErrors()) {
			flash.addFlashAttribute("alert_danger", "Favor de verificar los datos que introduciste");
			return viewName;
		}
		
		facturaService.generate(facturaSolicitud);
		
		flash.addFlashAttribute("alert_success","Se agregan generaron las facturas para el periodo indicado");
		viewName = "redirect:/admin/facturas";
		
		return viewName;
	}
}
