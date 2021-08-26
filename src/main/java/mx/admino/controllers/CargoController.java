package mx.admino.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import mx.admino.models.Breadcrum;
import mx.admino.models.Cargo;
import mx.admino.services.CargoService;

@Controller
public class CargoController {

	@Autowired
	CargoService cargoService;
	
	@GetMapping("/cargos")
	public String index(Model model,
			@RequestParam Integer page,
			@RequestParam Integer rows) {
		
		List<Breadcrum> breadcrum = Arrays.asList(new Breadcrum[] {
			new Breadcrum("Inicio", "/", false),
	        new Breadcrum("Cargos", "/cargos", true)
		});
		Pageable pageable = PageRequest.of(page, rows);
		Page<Cargo> cargos = cargoService.findAll(pageable);
		model.addAttribute("breadcrum", breadcrum);
		model.addAttribute("cargos", cargos);
		return "cargos/index";
	}
	
	@GetMapping("/cargos/nuevo")
	public String getNuevo() {
		return "cargos/formulario";
	}
	
	@GetMapping("/cargos/editar/{id}")
	public String getEditar(@PathVariable String id,
			Model model) {
		return "cargos/formulario";
	}
}
