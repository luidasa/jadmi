package mx.admino.controllers.admin.pagos;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.admino.models.Breadcrum;
import mx.admino.models.entities.Condomino;
import mx.admino.models.entities.Pago;
import mx.admino.services.CondominoService;
import mx.admino.services.PagoService;

@Controller
public class UpdatePagos {

	@Autowired
	PagoService pagoService;
	
	@Autowired
	CondominoService condominoService;
	
	private List<Breadcrum> getBreadcrum(Pago pago) {

		DateFormatter format = new DateFormatter("dd/MM/yyyy");
		
		List<Breadcrum> x = new ArrayList<Breadcrum>();
		x.add(new Breadcrum("Inicio", "/panel", false));
		x.add(new Breadcrum("Pagos", "/pagos", pago == null));
		if (pago != null) {
			x.add(new Breadcrum((pago.getId() !=null ?  
					(pago.getCondomino() != null ? pago.getCondomino().getInterior() : "No identificado") + " del " + format.print(pago.getFechaPagado(), Locale.FRANCE)  : "Nuevo"), "/pagos/" + (pago.getId() !=null ?  pago.getId() : "nuevo"), true));			
		}
		return x ;
	}
	
	@ModelAttribute
	private void getCondominios(Model model) {
		List<Condomino> condominos = condominoService.findAll();
		model.addAttribute("condominos", condominos);
	}
		
	@GetMapping("/pagos/{id}")
	public String editar(
			@PathVariable String id, 
			Model model) throws NotFoundException {
		
		Pago pago = pagoService.findById(id);
		model.addAttribute("breadcrum", getBreadcrum(pago));
		model.addAttribute("pago", pago);
		return "pagos/formulario";
	}
	
	@PostMapping("/pagos/{id}")
	public String actualizar(
			@PathVariable String id,
			@ModelAttribute @Valid Pago pago,
			BindingResult binding,
			RedirectAttributes flash,
			Model model
		) throws NotFoundException {
		
		if (binding.hasErrors()) {
			model.addAttribute("breadcrum", getBreadcrum(pago));
			flash.addFlashAttribute("alert-danger", "Ocurrio un error. Favor de validar y volver a enviar");
			return "pagos/formulario";
		}
		Pago pagodb = pagoService.findById(id);
		pagodb.merge(pago);
		pagoService.save(pagodb);
		flash.addFlashAttribute("alert-success", "El pago ha sido registratdo.");
		return "redirect:/pagos/" + pagodb.getId();
	}
}
