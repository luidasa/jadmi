package mx.admino.controllers.auth;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.admino.models.entities.Usuario;
import mx.admino.services.UsuarioService;

@Controller
public class SignUpController {
	
	@Autowired
	UsuarioService service;

	@GetMapping("/signup")
	public String getSignup() {
		return "auth/signup";
	}
	
	@PostMapping("/signup")
	public String postSignup(@Valid @ModelAttribute Usuario usuario,
			BindingResult binding,
			RedirectAttributes flash) {
		
		if (binding.hasErrors()) {
			flash.addFlashAttribute("mensaje", "Ocurrio un error al crear el usuario.");
			return "auth/signup";
		}

		service.create(usuario);
		
		return "redirect:/panel";
		
	}
}
