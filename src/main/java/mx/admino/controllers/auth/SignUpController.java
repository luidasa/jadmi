package mx.admino.controllers.auth;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.admino.models.entities.Condominio;
import mx.admino.services.CondominioService;
import mx.admino.services.UsuarioService;

@Controller
public class SignUpController {
	
	@Autowired
	UsuarioService usuarioSrv;
	
	@Autowired
	CondominioService condominioSrv;
		

	@GetMapping("/signup")
	public String getSignup(@ModelAttribute Condominio condominio) {
		return "auth/signup";
	}
	
	@PostMapping("/signup")
	public String postSignup(@ModelAttribute @Valid Condominio condominio,
			BindingResult binding,
			RedirectAttributes flash) {
		
		if (binding.hasErrors()) {
			flash.addFlashAttribute("mensaje", "Ocurrio un error al registrar el condominio.");
			return "auth/signup";
		}
		
		var condominiodb = condominioSrv.save(condominio);
		
		return "redirect:/login";
		
	}
}
