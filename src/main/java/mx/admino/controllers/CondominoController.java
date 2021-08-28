package mx.admino.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mx.admino.models.Condomino;
import mx.admino.services.CondominoService;

@Controller
@RequestMapping("condominos")
public class CondominoController {

	@Autowired
	CondominoService condominoService;
	
	@GetMapping()
	public String index(
			@RequestParam(required = false, defaultValue =  "1") Integer page,
			@RequestParam(required = false, defaultValue =  "10")Integer size,
			Model model) {
		
		Pageable pageable = PageRequest.of(page - 1, size);
		PageImpl<Condomino> condominos = (PageImpl<Condomino>) condominoService.findAll(pageable);
		model.addAttribute("condominos", condominos);
		return "condominos/index";
	}

}
