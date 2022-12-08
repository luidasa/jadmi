package mx.admino.controllers.facturas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import mx.admino.services.CondominioService;
import mx.admino.services.CasasService;
import mx.admino.services.FacturaService;

@Controller
public class DetailController {

	@Autowired
	FacturaService facturaService;
	
	@Autowired
    CasasService casasService;
	
	@Autowired
	CondominioService condominioService;
	

	@GetMapping("/condominios/{cid}/casas/{uid}/facturas/{fid}")
	public String getFactura(
			@PathVariable String cid,
			@PathVariable String uid,
			@PathVariable String fid,
			Model model) {
		
		model.addAttribute("factura", facturaService.findById(fid));
		model.addAttribute("casa", casasService.findById(uid));
		model.addAttribute("condominio", condominioService.findById(cid));
		return "facturas/factura";
	}
}
