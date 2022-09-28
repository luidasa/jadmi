package mx.admino.controllers.cargos;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import mx.admino.models.entities.Casa;
import mx.admino.models.entities.Condominio;
import mx.admino.services.CondominioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.admino.models.Breadcrum;
import mx.admino.models.entities.Cargo;
import mx.admino.services.CargoService;
import mx.admino.services.CasasService;

@Controller
public class CargoController {

	@Autowired
	CargoService cargoService;

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
		x.add(new Breadcrum("Cargos", "/condominios/" + condominio.getId() + "/casas/" + casa.getId() + "/cargos", true));
		return x ;
	}
	
	@ModelAttribute
	private void getCondominios(Model model) {
		List<Casa> casas = casasService.findAll();
		model.addAttribute("condominos", casas);
	}	
	
	@GetMapping("/condominios/{cid}/casas/{id}/cargos")
	public String index(
			@PathVariable String cid,
			@PathVariable String id,
			Model model,
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer rows) {

		var condominio = condominioService.findById(cid);
		var casa = casasService.findById(id);

		Sort ordenado = Sort.by("fechaVencimiento").descending();
		Pageable pageable = PageRequest.of(page - 1, rows, ordenado);
		Page<Cargo> cargos = cargoService.findByCasa(casa, pageable);
		model.addAttribute("breadcrum", getBreadcrum(condominio, casa));
		model.addAttribute("condominio", condominio);
		model.addAttribute("casa", casa);
		model.addAttribute("cargos", cargos);
		return "cargos/index";
	}

	@GetMapping("/condominios/{cid}/cargos/{id}")
	public String getEditar(
			@PathVariable String cid,
			@PathVariable String id,
			Model model) {
		Cargo cargo = cargoService.findById(id);
		model.addAttribute("cargo", cargo);
		model.addAttribute("breadcrum", getBreadcrum(null, null));
		return "cargos/formulario";
	}


}
