package mx.admino.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador para obtener la información del panel de los administradores del condominio.
 *
 */
@Controller
public class PanelController {

	@GetMapping("/admin/panel")
	public String getIndex() {
		return "panel/index";
	}
}
