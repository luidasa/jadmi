package mx.admino.controllers.auth;

import java.util.Arrays;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.admino.models.entities.Condominio;
import mx.admino.models.entities.Condomino;
import mx.admino.models.entities.Roles;
import mx.admino.models.entities.Usuario;
import mx.admino.services.CondominioService;
import mx.admino.services.CondominoService;
import mx.admino.services.UsuarioService;

@Controller
public class SignUpController {
	
	@Autowired
	UsuarioService usuarioSrv;
	
	@Autowired
	CondominioService condominioSrv;
	
	@Autowired
	CondominoService condominoSrv;
	
	@Autowired
	PasswordEncoder encoder;

	@GetMapping("/signup")
	public String getSignup(@ModelAttribute Condominio condominio) {
		return "auth/signup";
	}
	
	@PostMapping("/signup")
	public String postSignup(@ModelAttribute @Valid Condominio condominio,
			BindingResult binding,
			RedirectAttributes flash) {
		
		// Verificamos que el condominio no este registrado
		if (condominioSrv.findByNombre(condominio.getNombre()).size() > 0) {
			System.err.print("El nombre del condominio ya existe");
			binding.addError(new FieldError("Condominio", "nombre", "El condominio ya ha sido registrado"));
		}
		
		// Verificamos que el administrador no este registrado.
		if (usuarioSrv.findByUsername(condominio.getUsername()) != null) {
			System.err.print("El administrador del condominio ya existe");
			binding.addError(new FieldError("Condominio", "username", "El administrador ya ha sido registrado. Inicie sesión para registrar el condominio."));			
		}
		
		// Verificamos que la confirmación de la contraseña y la contraseña sean las mismas
		if (!condominio.getPassword().equals(condominio.getConfirmacion())) {
			System.err.print("La contraseña y su confirmación no son iguales.");
			binding.addError(new FieldError("Condominio", "confirmacion", "La contraseña y su confirmación no son iguales."));						
		}

		// Verificamos que los objetos del dominio no tengan error.
		if (binding.hasErrors()) {
			flash.addFlashAttribute("alert_danger", "Ocurrio un error al registrar el condominio.");
			return "auth/signup";
		}		
		Usuario usuarioDb = usuarioSrv.create(
				new Usuario(
						condominio.getUsername(),
						encoder.encode(condominio.getPassword()), 
						Arrays.asList(new Roles[] {
							Roles.ROLE_ADMINISTRADOR
						}),
						condominio.getAdministrador(),
						condominio.getTelefono(),
						condominio.getCorreo(),
						false));
		
		var condominiodb = condominioSrv.save(condominio);
		
		// Agregamos el numero de unidades privativas que nos ha indicado el que se registra.
		for(Integer i = 0; i < condominiodb.getUnidades(); i++ ) {
			String interior = "Casa - " + (i + 1);
			String nombre = UUID.randomUUID().toString();
			if (condominoSrv.findByInterior(interior) == null) {
				condominoSrv.save(new Condomino(interior, nombre));
			}
		}
		flash.addAttribute("alert_success", "Gracias por confianza al registrar el condominio " + condominiodb.getNombre() + ". Hemos enviado un correo electrónico a " + usuarioDb.getEmail() + " para confirmar su registro" );
		return "redirect:/login";		
	}
}
