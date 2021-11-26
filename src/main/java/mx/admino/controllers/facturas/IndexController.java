package mx.admino.controllers.facturas;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import mx.admino.models.Breadcrum;
import mx.admino.models.Condomino;
import mx.admino.models.Factura;
import mx.admino.models.FacturaSolicitud;
import mx.admino.services.CondominoService;
import mx.admino.services.FacturaService;

@Controller
public class IndexController {

	@Autowired
	FacturaService facturaService;
	
	@Autowired
	CondominoService condominoService;
		
	@ModelAttribute
	private void getCondominos(Model model) {
		List<Condomino> condominos = condominoService.findAll();
		model.addAttribute("condominos", condominos);
	}	
	
	private List<Breadcrum> getBreadcrum() {

		List<Breadcrum> x = new ArrayList<Breadcrum>();
		x.add(new Breadcrum("Inicio", "/panel", false));
		x.add(new Breadcrum("Facturas", "/facturas", true));
		return x ;
	}
	
	@GetMapping("/facturas")
	public String getIndex(
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer rows,
			@RequestParam(required = false) String cid,
			@ModelAttribute FacturaSolicitud FacturaSolicitud,
			Model model) {
		
		
		Sort sort = Sort.by("fechaCorte", Direction.DESC.toString()).and(Sort.by("condomino.id"));
		Pageable pageable = PageRequest.of(page - 1, rows, sort);
		Page<Factura> facturas ;
		if (cid == null) {
			facturas = facturaService.findAll(pageable);
		} else {
			facturas = facturaService.findByCondomino_Id(cid, pageable);
		}
		model.addAttribute("breadcrum", getBreadcrum());
		model.addAttribute("facturas", facturas);
		return "facturas/index";
	}
}
