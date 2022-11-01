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
import org.springframework.web.bind.annotation.*;
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
	
	@GetMapping("/condominios/{cid}/casas/{id}/pagos/nuevo")
	public String crear(
			@PathVariable String cid,
			@PathVariable String id,
			Model model) {
		
		Pago pago = new Pago();
		Casa casa = casasService.findById(id);
		pago.setCondomino(casa);

		model.addAttribute("pago", pago  );
		model.addAttribute("breadcrum", getBreadcrum(pago));
		return "pagos/formulario";
	}
	
	@PostMapping("/condominios/{cid}/casas/{pid}/pagos/nuevo")
	public String postAdd(
			@PathVariable String cid,
			@PathVariable String pid,
			@ModelAttribute(name = "abono") @Valid Pago pago,
			BindingResult binding,
			RedirectAttributes flash,
			Model model
			) {
		String viewName = "pagos/index";
		var casa = casasService.findById(pid);
		model.addAttribute("breadcrum", getBreadcrum(pago));
		if (binding.hasErrors()) {
			binding.getAllErrors().stream().forEach(error -> {
				System.out.println(error.getObjectName() +" - " + error.getDefaultMessage());
			});
			return viewName;
		}
		pago.setCasa(casa);
		pagoService.save(pago);
		viewName= "redirect:/condominios/" + cid + "/casas/" + pid + "/pagos";
		flash.addFlashAttribute("alert_success", "El pago ha quedado registrado");
		return viewName;
	}	
}
