package mx.admino.controllers.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignUpController {

	@GetMapping("/signup")
	public String getSignup() {
		return "auth/signup";
	}
}
