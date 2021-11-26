package mx.admino.controllers.facturas;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.admino.models.FacturaSolicitud;
import mx.admino.services.CondominioService;
import mx.admino.services.CondominoService;
import mx.admino.services.FacturaService;

@Controller
public class CreateController {

	@Autowired
	FacturaService facturaService;
	
	@Autowired
	CondominoService condominoService;
	
	@Autowired
	CondominioService condominioService;
	
	
	@PostMapping("/facturas/generar")
	public String postGenerar(
			@ModelAttribute @Valid FacturaSolicitud facturaSolicitud,
			BindingResult binding,
			RedirectAttributes flash,
			Model model) {
		
		String viewName = "redirect:/facturas";
		
		if (binding.hasErrors()) {
			flash.addFlashAttribute("alert_danger", "Favor de verificar los datos que introduciste");
			return viewName;
		}
		
		facturaService.generate(
				facturaSolicitud.getFechaInicioCorte(),
				facturaSolicitud.getFechaCorte(),
				facturaSolicitud.getFechaVencimiento());
		
		flash.addFlashAttribute("alert_success","Se agregan generaron las facturas para el periodo indicado");
		viewName = "redirect:/facturas";
		
		return viewName;
	}
}
