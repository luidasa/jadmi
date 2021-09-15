package mx.admino.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PanelController {

	@GetMapping("/panel")
	public String getIndex() {
		return "panel";
	}
}
