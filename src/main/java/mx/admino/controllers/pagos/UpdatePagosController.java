package mx.admino.controllers.pagos;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.admino.models.entities.Pago;
import mx.admino.services.CasasService;
import mx.admino.services.CondominioService;
import mx.admino.services.PagoService;

@Controller
public class UpdatePagosController {
	
	@Autowired
	CondominioService condominioService;

	@Autowired
	PagoService pagoService;
	
	@Autowired
    CasasService casasService;
	
		
	@GetMapping("/condominios/{uid}/pagos/{id}")
	public String editar(
			@PathVariable String uid,
			@PathVariable String id, 
			Model model) throws NotFoundException {
		
		var pago = pagoService.findById(id);
		var condominio = condominioService.findById(uid);
		var casas = casasService.findByCondominio(condominio);
		model.addAttribute("casas", casas);
		model.addAttribute("condominio", condominio);
		model.addAttribute("breadcrum", IndexPagosController.getBreadcrum(condominio, null));
		model.addAttribute("pago", pago);
		return "pagos/formulario";
	}
	
	@PostMapping("/condominios/{uid}/pagos/{id}")
	public String actualizar(
			@PathVariable String uid,
			@PathVariable String id,
			@ModelAttribute @Valid Pago pago,
			BindingResult binding,
			RedirectAttributes flash,
			Model model
		) throws NotFoundException {
		

		if (binding.hasErrors()) {
			var condominio = condominioService.findById(uid);
			var casas = casasService.findByCondominio(condominio);
			model.addAttribute("casas", casas);
			model.addAttribute("condominio", condominio);
			model.addAttribute("breadcrum", IndexPagosController.getBreadcrum(condominio, null));
			flash.addAttribute("alert-danger", "Ocurrio un error. Favor de validar y volver a enviar");
			return "pagos/formulario";
		}
		Pago pagodb = pagoService.findById(id);
		pagodb.merge(pago);
		pagoService.update(pagodb);
		flash.addFlashAttribute("alert-success", "La información del pago ha sido actualizada.");
		return "redirect:/condominios/" + uid + "/pagos/" + id;
	}
}
