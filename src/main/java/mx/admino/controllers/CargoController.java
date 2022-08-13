package mx.admino.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import mx.admino.models.entities.Condomino;
import mx.admino.services.CargoService;
import mx.admino.services.CondominoService;

@Controller
public class CargoController {

	@Autowired
	CargoService cargoService;

	@Autowired
	CondominoService condominoService;
	
	private List<Breadcrum> getBreadcrum(Cargo cargo) {

		List<Breadcrum> x = new ArrayList<Breadcrum>();
		x.add(new Breadcrum("Inicio", "/panel", false));
		x.add(new Breadcrum("Cargos", "/cargos", cargo == null));
		if (cargo != null) {
			x.add(new Breadcrum(
					cargo.getId() !=null ? cargo.getConcepto() : "Nuevo", 
					"/cargos/" + (cargo.getId() !=null ? cargo.getId() : "nuevo"),
					true));
		}
		return x ;
	}
	
	@ModelAttribute
	private void getCondominios(Model model) {
		List<Condomino> condominos = condominoService.findAll();
		model.addAttribute("condominos", condominos);
	}	
	
	@GetMapping("/cargos")
	public String index(Model model,
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer rows,
			@RequestParam(required = false) String cid) {
		
		Sort ordenado = Sort.by("fechaVencimiento").descending();
		Pageable pageable = PageRequest.of(page - 1, rows, ordenado);
		Page<Cargo> cargos;
		if (cid == null) {
			cargos = cargoService.findAll(pageable);			
		} else {
			cargos = cargoService.findByCondominoId(cid, pageable);			
		}
		model.addAttribute("breadcrum", getBreadcrum(null));
		model.addAttribute("cargos", cargos);
		return "cargos/index";
	}
	
	@GetMapping("/cargos/nuevo")
	public String getNuevo(
			@RequestParam(required = false) String cid,
			Model model) {
		Cargo cargo = new Cargo();
		if ((cid!=null) && (!cid.isEmpty()) && (!cid.isBlank())) {
			Condomino condomino = condominoService.findById(cid);
			cargo.setCondomino(condomino);
		}
		model.addAttribute("breadcrum", getBreadcrum(cargo));
		model.addAttribute("cargo", cargo);
		return "cargos/formulario";
	}
	
	@PostMapping("/cargos/nuevo")
	public String postNuevo(
			@ModelAttribute @Valid Cargo cargo,
			BindingResult binding,
			RedirectAttributes flash,
			Model model) {
		String viewName = "cargos/formulario";

		model.addAttribute("breadcrum", getBreadcrum(cargo));
		if (binding.hasErrors()) {
			return viewName;
		}
		cargoService.save(cargo);
		viewName = "redirect:/cargos";
		flash.addFlashAttribute("alert_success", "Cargo registrado en el condomino.");
		return viewName;
	}
	
	@GetMapping("/cargos/{id}")
	public String getEditar(@PathVariable String id,
			Model model) {
		Cargo cargo = cargoService.findById(id);
		model.addAttribute("cargo", cargo);
		model.addAttribute("breadcrum", getBreadcrum(cargo));
		return "cargos/formulario";
	}
}
