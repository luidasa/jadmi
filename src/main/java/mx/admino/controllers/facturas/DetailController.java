package mx.admino.controllers.facturas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import mx.admino.models.Condomino;
import mx.admino.services.CondominioService;
import mx.admino.services.CondominoService;
import mx.admino.services.FacturaService;

@Controller
public class DetailController {

	@Autowired
	FacturaService facturaService;
	
	@Autowired
	CondominoService condominoService;
	
	@Autowired
	CondominioService condominioService;
	
	@ModelAttribute
	private void getCondominios(Model model) {
		List<Condomino> condominos = condominoService.findAll();
		model.addAttribute("condominos", condominos);
	}

	@GetMapping("/facturas/{id}")
	public String getFactura(
			@PathVariable String id,
			Model model) {
		
		model.addAttribute("factura", facturaService.findById(id));
		model.addAttribute("condominio", condominioService.findFirst());
		return "facturas/factura";
	}
}
