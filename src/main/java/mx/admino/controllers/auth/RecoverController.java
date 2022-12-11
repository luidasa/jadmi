package mx.admino.controllers.auth;

import mx.admino.events.OnUserRecoverEvent;
import mx.admino.events.OnUserRegisteredEvent;
import mx.admino.models.entities.Token;
import mx.admino.models.entities.Usuario;
import mx.admino.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
public class RecoverController {

	@Autowired
	ApplicationEventPublisher eventPublisher;

	@Autowired
	UsuarioService usuarioSrv;

	@GetMapping("/recover")
	public String getRecover() {
		return "auth/recover";
	}

	@PostMapping("/recover")
	public String postRecover(
			@RequestParam String email,
			RedirectAttributes flash,
			Model model) {

		Usuario usuariodb = usuarioSrv.findByEmail(email);
		if (usuariodb == null) {
			System.out.println("No se encontro el email");
			model.addAttribute("alert_info", "El correo que ingresado no lo tenemos registrado. Favor de verificarlo.");
			return "auth/recover";
		}

		eventPublisher.publishEvent(new OnUserRecoverEvent(usuariodb, "http://localhost:8080"));
		flash.addFlashAttribute("alert_info", "Hemos enviado un correo con las instrucciones para recuperar su contraseña.");
		return "redirect:/login";
	}

	@GetMapping("/reset/{code}")
	public String getReset (
			@PathVariable String code,
			Model model,
			RedirectAttributes flash) {

		// Buscamos el usuario que es dueño del token.
		Token tokendb = usuarioSrv.findTokenByCode(code);

		// Verificamos existe el token
		if (tokendb == null) {
			flash.addFlashAttribute("mensaje_error", "El token no existe.");
			return "redirect:/recover";
		}
		// Verificamos si esta vigente  es decir, si el token no ha sido utilizado aun.
		if (!tokendb.isVigente()) {
			flash.addFlashAttribute("mensaje_error", "El token ya ha sido utlizado. Deseas uno nuevo?");
			return "redirect:/recover";
		}
		// Verificamos si no esta vencido.
		if (!tokendb.getFechaVencimiento().isAfter(LocalDate.now())) {
			flash.addFlashAttribute("mensaje_error", "El token ya ha expirado. Deseas uno nuevo?");
			return "redirect:/recover";
		}

		model.addAttribute("usuario", tokendb.getUsuario());
		return "auth/reset";
	}

	@PostMapping("/reset/{code}")
	public String postReset(
			@PathVariable String code,
			@RequestParam String password,
			@RequestParam String confirmation,
			Model model,
			RedirectAttributes flash) {

		// Buscamos el usuario que es dueño del token.
		Token tokendb = usuarioSrv.findTokenByCode(code);

		// Verificamos existe el token
		if (tokendb == null) {
			flash.addFlashAttribute("error", "El token no existe.");
			return "redirect:/recover";
		}
		// Verificamos si esta vigente  es decir, si el token no ha sido utilizado aun.
		if (!tokendb.isVigente()) {
			flash.addFlashAttribute("error", "El token ya ha sido utlizado. Deseas uno nuevo?");
			return "redirect:/recover";
		}
		// Verificamos si no esta vencido.
		if (!tokendb.getFechaVencimiento().isAfter(LocalDate.now())) {
			flash.addFlashAttribute("error", "El token ya ha expirado. Deseas uno nuevo?");
			return "redirect:/recover";
		}
		// Cambiamos de la contraseña
		model.addAttribute("usuario", tokendb.getUsuario());

		return "redirect:/login";
	}
}
