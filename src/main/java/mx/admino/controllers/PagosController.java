package mx.admino.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.admino.models.Breadcrum;
import mx.admino.models.Condomino;
import mx.admino.models.Pago;
import mx.admino.services.CondominoService;
import mx.admino.services.PagoService;

@Controller
public class PagosController {

	@Autowired
	PagoService pagoService;
	
	@Autowired
	CondominoService condominoService;
	
	private List<Breadcrum> getBreadcrum(Pago pago) {

		List<Breadcrum> x = new ArrayList<Breadcrum>();
		x.add(new Breadcrum("Inicio", "/panel", false));
		x.add(new Breadcrum("Pagos", "/pagos", pago == null));
		if (pago != null) {
			x.add(new Breadcrum((pago.getId() !=null ?  pago.getId() : "Nuevo"), "/pagos/" + (pago.getId() !=null ?  pago.getId() : "nuevo"), true));			
		}
		return x ;
	}
	
	@ModelAttribute
	private void getCondominios(Model model) {
		List<Condomino> condominos = condominoService.findAll();
		model.addAttribute("condominos", condominos);
	}
	
	@GetMapping("/pagos")
	public String index(Model model, 
			@RequestParam(required = false, defaultValue = "0") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer size) {

		Pageable pageable = PageRequest.of(page, size);
		Page<Pago> pagos = pagoService.findAll(pageable);
		model.addAttribute("pagos", pagos);
		model.addAttribute("breadcrum", getBreadcrum(null));
		return "pagos/index";
	}
	
	@GetMapping("/pagos/nuevo")
	public String crear(
			@RequestParam(required = false) String cid,
			Model model) {
		
		Pago pago = new Pago();
		if (cid != null) {
			Condomino condomino = condominoService.findById(cid);
			pago.setCondomino(condomino);
		}
		model.addAttribute("pago", pago  );
		model.addAttribute("breadcrum", getBreadcrum(pago));
		return "pagos/formulario";
	}
	
	@PostMapping("/pagos/nuevo")
	public String postAdd(
			@ModelAttribute @Valid Pago pago,
			BindingResult binding,
			RedirectAttributes flash,
			Model model
			) {
		String viewName = "pagos/formulario";		
		model.addAttribute("breadcrum", getBreadcrum(pago));
		if (binding.hasErrors()) {			
			return viewName;
		}
		pagoService.save(pago);
		viewName= "redirect:/pagos";
		flash.addFlashAttribute("alert-success", "El pago ha quedado registrado");
		return viewName;
	}

	@GetMapping("/pagos/editar/{id}")
	public String editar(@PathVariable String id, Model model) throws NotFoundException {
		
		Pago pago = pagoService.findById(id);
		model.addAttribute("pago", pago);
		return "pagos/formulario";
	}
}
