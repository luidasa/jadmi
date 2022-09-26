package mx.admino.controllers.pagos;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.admino.models.Breadcrum;
import mx.admino.models.entities.Casa;
import mx.admino.models.entities.Pago;
import mx.admino.services.CasasService;
import mx.admino.services.PagoService;
@Controller
public class CreatePagosController {

	@Autowired
	PagoService pagoService;
	
	@Autowired
    CasasService casasService;
	
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
		List<Casa> casas = casasService.findAll();
		model.addAttribute("condominos", casas);
	}
	
	@GetMapping("/pagos/nuevo")
	public String crear(
			@RequestParam(required = false) String cid,
			Model model) {
		
		Pago pago = new Pago();
		if ((cid != null) && (!cid.isBlank()) && (!cid.isEmpty())) {
			Casa casa = casasService.findById(cid);
			pago.setCondomino(casa);
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
		flash.addFlashAttribute("alert_success", "El pago ha quedado registrado");
		return viewName;
	}	
}
