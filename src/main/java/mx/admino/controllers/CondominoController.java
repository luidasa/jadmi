package mx.admino.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mx.admino.services.CondominoService;

@Controller
@RequestMapping("condominos")
public class CondominoController {

	@Autowired
	CondominoService condominoService;
	
	@GetMapping()
	public String index(
			@RequestParam(required = false, defaultValue =  "0") Integer page,
			@RequestParam(required = false, defaultValue =  "10")Integer size,
			Model model) {
		
		Pageable pageable = PageRequest.of(page, size);
		model.addAttribute("condominos", condominoService.findAll(pageable));
		return "condominos/index";
	}

}
