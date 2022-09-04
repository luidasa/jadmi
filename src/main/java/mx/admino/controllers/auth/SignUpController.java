package mx.admino.controllers.auth;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import mx.admino.components.VerifyReCAPTCHA;
import mx.admino.events.OnUserRegisteredEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.admino.models.entities.Usuario;
import mx.admino.services.UsuarioService;

import java.io.IOException;

@Controller
public class SignUpController {

	@Value("${server.baseUrl}")
	String urlBase;

	@Value("${reCAPTCHA.site.key}")
	String reCAPTCHASiteKey;

	@Autowired
	ApplicationEventPublisher eventPublisher;

	@Autowired
	UsuarioService usuarioSrv;

	@Autowired
	PasswordEncoder encoder;

	@GetMapping("/signup")
	public String getSignup(@ModelAttribute Usuario usuario, Model model) {

		model.addAttribute("reCAPTCHASiteKey", reCAPTCHASiteKey);
		return "auth/signup";
	}
	
	@PostMapping("/signup")
	public String postSignup(@ModelAttribute @Valid Usuario usuario,
			BindingResult binding,
			RedirectAttributes flash,
			Model model,
			 HttpServletRequest req) {

		model.addAttribute("reCAPTCHASiteKey", reCAPTCHASiteKey);

		String gRecaptchaResponse = req.getParameter("g-recaptcha-response");
		System.out.println(gRecaptchaResponse);
		boolean verify = false;
		try {
			verify = VerifyReCAPTCHA.verify(gRecaptchaResponse);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		// Verificamos el catpcha.
		if (!verify) {
			System.err.print("No es un humano");
			binding.addError(new FieldError("usuario", "name", "Fallo la validación de humanidad."));
		}

		// Verificamos que el administrador no este registrado.
		if (usuarioSrv.findByUsername(usuario.getUsername()) != null) {
			System.err.print("El administrador del condominio ya existe");
			binding.addError(new FieldError("usuario", "username", "El nombre de usuario ya ha sido registrado."));
		}

		// Verificamos que el administrador no este registrado.
		if (usuarioSrv.findByEmail(usuario.getEmail()) != null) {
			System.err.print("Este correo ya esta registrado");
			binding.addError(new FieldError("usuario", "email", "El correo ya ha sido registrado."));
		}

		// Verificamos que la confirmación de la contraseña y la contraseña sean las mismas
		if (!usuario.getPassword().equals(usuario.getConfirmation())) {
			System.err.print("La contraseña y su confirmación no son iguales.");
			binding.addError(new FieldError("usuario", "confirmation", "La contraseña y su confirmación no son iguales."));
		}

		// Verificamos que los objetos del dominio no tengan error.
		if (binding.hasErrors()) {
			flash.addFlashAttribute("error", "Ocurrio un error al registrar el condominio.");
			return "auth/signup";
		}
		Usuario usuarioDb = usuarioSrv.create(
				new Usuario(
						usuario.getUsername(),
						encoder.encode(usuario.getPassword()),
						usuario.getName(),
						usuario.getPhone(),
						usuario.getEmail(),
						false));

		eventPublisher.publishEvent(new OnUserRegisteredEvent(usuarioDb, urlBase));
		flash.addAttribute("alert_success", "Gracias por registrarse con nosotros " + usuarioDb.getName() + ". Hemos enviado un correo electrónico a " + usuarioDb.getEmail() + " para confirmar su registro." );
		return "redirect:/login";		
	}
}
