package mx.admino.controllers.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecoverController {

	@GetMapping("/recover")
	public String getRocover() {
		return "auth/recover";
	}
}
