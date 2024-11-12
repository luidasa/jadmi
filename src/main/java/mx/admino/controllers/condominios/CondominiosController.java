package mx.admino.controllers.condominios;

import jakarta.validation.Valid;

import mx.admino.factories.BreadcrumFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.admino.models.entities.Condominio;
import mx.admino.services.CondominioService;

import java.security.Principal;

@Controller
public class CondominiosController {

	@Autowired
	CondominioService condominioService;

	@GetMapping("/condominios")
	public String getCondominio(
			Principal auth,
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "20") Integer rows,
			Model model) {

		Pageable pageable = PageRequest.of(page - 1, rows);
		Page<Condominio> condominios = condominioService.findByAdministrador(auth.getName(), pageable);
		model.addAttribute("condominios", condominios);
		model.addAttribute("breadcrum", BreadcrumFactory.getBreadcrum(null));
		BreadcrumFactory.getBreadcrum(null).stream().forEach(x-> System.out.println(x.getEtiqueta()));
		return "condominio/index";
	}
}
