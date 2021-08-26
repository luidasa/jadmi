package mx.admino.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import mx.admino.models.Pago;
import mx.admino.services.PagoService;

@Controller
public class PagosController {

	@Autowired
	PagoService pagoService;
	
	@GetMapping("/pagos")
	public String index(Model model, 
			@RequestParam(required = false, defaultValue = "0") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer size) {

		Pageable pageable = PageRequest.of(page, size);
		Page<Pago> pagos = pagoService.findAll(pageable);
		model.addAttribute("pagos", pagos);
		return "pagos/index";
	}
	
	@GetMapping("/pagos/nuevo")
	public String crear() {
		return "pagos/formulario";
	}

	@GetMapping("/pagos/editar/{id}")
	public String editar(@PathVariable String id, Model model) throws NotFoundException {
		
		Pago pago = pagoService.findById(id);
		model.addAttribute("pago", pago);
		return "pagos/formulario";
	}
}
