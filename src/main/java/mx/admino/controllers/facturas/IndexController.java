package mx.admino.controllers.facturas;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import mx.admino.models.Breadcrum;
import mx.admino.models.FacturaFiltro;
import mx.admino.models.entities.Casa;
import mx.admino.models.entities.CorteFactura;
import mx.admino.models.entities.Factura;
import mx.admino.services.CasasService;
import mx.admino.services.FacturaService;

@Controller
public class IndexController {

	@Autowired
	FacturaService facturaService;
	
	@Autowired
    CasasService casasService;
	
	@ModelAttribute
	private void getCondominos(Model model) {
		List<Casa> casas = casasService.findAll();
		model.addAttribute("condominos", casas);
		
		FacturaFiltro filtro = new FacturaFiltro();
		model.addAttribute("filtro", filtro);

		CorteFactura solicitud = new CorteFactura();
		model.addAttribute("solicitud", solicitud);
}	
	
	private List<Breadcrum> getBreadcrum() {

		List<Breadcrum> x = new ArrayList<Breadcrum>();
		x.add(new Breadcrum("Inicio", "/panel", false));
		x.add(new Breadcrum("Facturas", "/facturas", true));
		return x ;
	}
	
	@GetMapping("/admin/facturas")
	public String getIndex(
			@Valid FacturaFiltro facturaFiltro,
			BindingResult result,
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer rows,
			@RequestParam(required = false) String cid,
			Model model) {
		
		
		Sort sort = Sort.by("fechaCorte", Direction.DESC.toString()).and(Sort.by("condomino.id"));
		Pageable pageable = PageRequest.of(page - 1, rows, sort);
		if (cid != null) {
			facturaFiltro.setCondomino(casasService.findById(cid));
		}
		System.out.println(facturaFiltro);
		Page<Factura> facturas = facturaService.search(facturaFiltro, pageable);
		model.addAttribute("breadcrum", getBreadcrum());
		model.addAttribute("facturas", facturas);
		return "facturas/index";
	}
}
