package mx.admino.controllers.facturas;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import mx.admino.models.entities.Condominio;
import mx.admino.services.CondominioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
	CondominioService condominioService;

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

	private List<Breadcrum> getBreadcrum(Condominio condominio, Casa casa) {

		DateFormatter format = new DateFormatter("dd/MM/yyyy");

		List<Breadcrum> x = new ArrayList<Breadcrum>();
		x.add(new Breadcrum("Inicio", "/panel", false));
		x.add(new Breadcrum("Condominios", "/condominios", false));
		x.add(new Breadcrum(condominio.getNombre(), "/condominios/" + condominio.getId(), false));
		x.add(new Breadcrum("Casas", "/condominios/" + condominio.getId() + "/casas", false));
		x.add(new Breadcrum(casa.getInterior(), "/condominios/" + condominio.getId() + "/casas/" + casa.getId(), false));
		x.add(new Breadcrum("Facturas", "/condominios/" + condominio.getId() + "/casas/" + casa.getId() + "/facturas", true));
		return x ;
	}
	
	@GetMapping("/condominios/{cid}/casas/{id}/facturas")
	public String getIndex(
			@PathVariable String cid,
			@PathVariable String id,
			@Valid FacturaFiltro facturaFiltro,
			BindingResult result,
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer rows,
			Model model) {

		var condominio = condominioService.findById(cid);
		var casa = casasService.findById(id);
		
		Sort sort = Sort.by("fechaCorte", Direction.DESC.toString()).and(Sort.by("condomino.id"));
		Pageable pageable = PageRequest.of(page - 1, rows, sort);
		Page<Factura> facturas = facturaService.findByCasa(casa, pageable);
		model.addAttribute("breadcrum", getBreadcrum(condominio, casa));
		model.addAttribute("facturas", facturas);
		model.addAttribute("condominio", condominio);
		model.addAttribute("casa", casa);
		return "facturas/index";
	}
}
