package mx.admino.controllers;

import org.springframework.stereotype.Controller;

@Controller
public class PanelController {

	public String getIndex() {
		return "panel";
	}
}
