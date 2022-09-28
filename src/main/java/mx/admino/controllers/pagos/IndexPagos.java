package mx.admino.controllers.pagos;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import mx.admino.models.entities.Casa;
import mx.admino.models.entities.Condominio;
import mx.admino.services.CargoService;
import mx.admino.services.CondominioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import mx.admino.models.Breadcrum;
import mx.admino.models.entities.Pago;
import mx.admino.services.CasasService;
import mx.admino.services.PagoService;

@Controller
public class IndexPagos {

	@Autowired
	PagoService pagoService;
	
	@Autowired
    CasasService casasService;

	@Autowired
	CondominioService condominioService;

	private List<Breadcrum> getBreadcrum(Condominio condominio, Casa casa) {

		DateFormatter format = new DateFormatter("dd/MM/yyyy");
		
		List<Breadcrum> x = new ArrayList<Breadcrum>();
		x.add(new Breadcrum("Inicio", "/panel", false));
		x.add(new Breadcrum("Condominios", "/condominios", false));
		x.add(new Breadcrum(condominio.getNombre(), "/condominios/" + condominio.getId(), false));
		x.add(new Breadcrum("Casas", "/condominios/" + condominio.getId() + "/casas", false));
		x.add(new Breadcrum(casa.getInterior(), "/condominios/" + condominio.getId() + "/casas/" + casa.getId(), false));
		x.add(new Breadcrum("Pagos", "/condominios/" + condominio.getId() + "/casas/" + casa.getId() + "/pagos", true));
		return x ;
	}

	@GetMapping("/condominios/{cid}/casas/{id}/pagos")
	public String index(Model model,
			@PathVariable String cid,
			@PathVariable String id,
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer size) {

		var condominio = condominioService.findById(cid);
		var casa = casasService.findById(id);

		Sort ordenado = Sort.by("fechaPagado").descending();
		Pageable pageable = PageRequest.of(page - 1, size, ordenado);
		Page<Pago> pagos = pagoService.findByCasa(casa, pageable);
		model.addAttribute("casa", casa);
		model.addAttribute("condominio", condominio);
		model.addAttribute("pagos", pagos);
		model.addAttribute("breadcrum", getBreadcrum(condominio, casa));
		return "pagos/index";
	}
}
