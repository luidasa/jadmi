package mx.admino.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.admino.models.entities.Condominio;
import mx.admino.services.CondominioService;

@Controller
@RequestMapping("/admin")
public class CondominioController {

	@Autowired
	CondominioService condominioService;

	
	@GetMapping("/condominio")
	public String getCondominio(
		Model model) {
		Condominio condominio = condominioService.findFirst();
		if (condominio == null) {
			condominio = new Condominio();
		}
		model.addAttribute("condominio", condominio);
		return "condominio/formulario";
	}
	
	@PostMapping("/condominio")
	public String postCondominio(@ModelAttribute @Valid Condominio condominio,
			BindingResult binding,
			RedirectAttributes flash,
			Model model) {
		
		String viewName = "redirect:/condominio";
		
		if (binding.hasErrors()) {
			viewName = "condominio/formulario";
			return viewName;
		}

		condominioService.save(condominio);
		flash.addFlashAttribute("alert_success", "Datos del condominio actualizados.");
		return viewName;
	}
}
