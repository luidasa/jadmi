package mx.admino.controllers.facturas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import mx.admino.models.entities.Casa;
import mx.admino.services.CondominioService;
import mx.admino.services.CasasService;
import mx.admino.services.FacturaService;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/facturas")
public class DetailController {

	@Autowired
	FacturaService facturaService;
	
	@Autowired
    CasasService casasService;
	
	@Autowired
	CondominioService condominioService;
	
	@ModelAttribute
	private void getCondominios(Model model) {
		List<Casa> casas = casasService.findAll();
		model.addAttribute("condominos", casas);
	}

	@GetMapping("/{id}")
	public String getFactura(
			@PathVariable String id,
			Model model) {
		
		model.addAttribute("factura", facturaService.findById(id));
		model.addAttribute("condominio", condominioService.findFirst());
		return "facturas/factura";
	}
}
