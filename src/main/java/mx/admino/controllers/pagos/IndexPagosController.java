package mx.admino.controllers.pagos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import mx.admino.models.entities.Casa;
import mx.admino.models.entities.Condominio;
import mx.admino.services.CondominioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import mx.admino.models.Breadcrum;
import mx.admino.models.entities.Pago;
import mx.admino.services.CasasService;
import mx.admino.services.PagoService;

@Controller
public class IndexPagosController {

	@Autowired
	PagoService pagoService;
	
	@Autowired
    CasasService casasService;

	@Autowired
	CondominioService condominioService;

	public static List<Breadcrum> getBreadcrum(Condominio condominio, Casa casa) {

		List<Breadcrum> x = new ArrayList<Breadcrum>();
		x.add(new Breadcrum("Inicio", "/panel", false));
		x.add(new Breadcrum("Condominios", "/condominios", false));
		x.add(new Breadcrum(condominio.getNombre(), "/condominios/" + condominio.getId(), false));
		if (casa == null) {
			x.add(new Breadcrum("Pagos", "/condominios/" + condominio.getId() + "/ingresos", true));
		} else {
			x.add(new Breadcrum("Casas", "/condominios/" + condominio.getId() + "/casas", false));
			x.add(new Breadcrum(casa.getInterior(), "/condominios/" + condominio.getId() + "/casas/" + casa.getId(), false));
			x.add(new Breadcrum("Pagos", "/condominios/" + condominio.getId() + "/casas/" + casa.getId() + "/pagos", true));

		}
		return x ;
	}

	@GetMapping("/condominios/{cid}/ingresos")
	public String index(Model model,
						@PathVariable Long cid,
						@RequestParam(required = false, defaultValue = "1") Integer page,
						@RequestParam(required = false, defaultValue = "10") Integer size) {

		var condominio = condominioService.findById(cid);
		var casas = casasService.findByCondominio(condominio);
		var pago = new Pago(condominio);

		Sort ordenado = Sort.by("fechaPagado").descending();
		Pageable pageable = PageRequest.of(page - 1, size, ordenado);
		Page<Pago> pagos = pagoService.findByCondominio(condominio, pageable);
		model.addAttribute("condominio", condominio);
		model.addAttribute("pagos", pagos);
		model.addAttribute("casas", casas);
		model.addAttribute("abono", pago);
		String tomorrow  = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		model.addAttribute("tomorrow", tomorrow);
		model.addAttribute("breadcrum", getBreadcrum(condominio, null));
		return "pagos/indexCondominio";
	}

	@GetMapping("/condominios/{cid}/casas/{id}/pagos")
	public String index(Model model,
			@PathVariable Long cid,
			@PathVariable Long id,
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer size) {

		var condominio = condominioService.findById(cid);
		var casa = casasService.findById(id);
		var pago = new Pago(casa);

		Sort ordenado = Sort.by("fechaPagado").descending();
		Pageable pageable = PageRequest.of(page - 1, size, ordenado);
		Page<Pago> pagos = pagoService.findByCasa(casa, pageable);
		model.addAttribute("casa", casa);
		model.addAttribute("condominio", condominio);
		model.addAttribute("pagos", pagos);
		model.addAttribute("abono", pago);
		String tomorrow  = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		model.addAttribute("tomorrow", tomorrow);
		model.addAttribute("breadcrum", getBreadcrum(condominio, casa));
		return "pagos/index";
	}
}
