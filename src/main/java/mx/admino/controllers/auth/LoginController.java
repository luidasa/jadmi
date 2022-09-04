package mx.admino.controllers.auth;

import mx.admino.models.entities.Token;
import mx.admino.models.entities.Usuario;
import mx.admino.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
public class LoginController {

	@Autowired
	UsuarioService usuarioSrv;
	
	@GetMapping("/login")
	public String getLogin() {
		return "auth/login";
	}

	@GetMapping("/commit/{code}")
	public String getCommit(
		@PathVariable String code,
		RedirectAttributes flash,
		Model model) {

		Token token = usuarioSrv.findTokenByCode(code);
		if (token == null) {
			flash.addFlashAttribute("alert_error", "N pudimos confirmar tu registro. Si no te has regitsrado aun Registrate, si ya lo hiciste utiliza la recuperación de contraseña para activar tu cuenta");
		} else if (!token.isVigente()) {
			flash.addFlashAttribute("alert_error", "Lo siento no pudimos confirmar tu registro. Si no lo has hecho aun te invitamos a que te registres, si ya lo hiciste utiliza la recuperación de contraseña para realizarlo");
		} else if (token.getFechaVencimiento().isBefore(LocalDate.now())) {
			flash.addFlashAttribute("alert_error", "La liga ha expirado. Utiliza la recuperación de contraseña para enviarte uno nuevo");
		} else {
			flash.addFlashAttribute("alert_success", "Inicia sesión con tu usuario y contraseña por favor.");
		}
		Usuario usuario = token.getUsuario();
		usuario.setEnabled(true);
		usuarioSrv.save(usuario);
		return "redirect:/login";
	}
}
