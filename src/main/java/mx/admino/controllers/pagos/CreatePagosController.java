package mx.admino.controllers.pagos;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import mx.admino.models.entities.Condominio;
import mx.admino.services.CargoService;
import mx.admino.services.CondominioService;
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

	@Autowired
	CondominioService condominioService;

	private List<Breadcrum> getBreadcrum(Pago pago) {

		DateFormatter format = new DateFormatter("dd/MM/yyyy");
		
		List<Breadcrum> x = new ArrayList<Breadcrum>();
		x.add(new Breadcrum("Inicio", "/panel", false));
		x.add(new Breadcrum("Pagos", "/pagos", pago == null));
		if (pago != null) {
			x.add(new Breadcrum((pago.getId() !=null ?  
					(pago.getCasa() != null ? pago.getCasa().getInterior() : "No identificado") + " del " + format.print(pago.getFechaPagado(), Locale.FRANCE)  : "Nuevo"), "/pagos/" + (pago.getId() !=null ?  pago.getId() : "nuevo"), true));
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
		Condominio condominio = condominioService.findById(cid);
		Casa casa = casasService.findById(id);
		pago.setCasa(casa);
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
		String viewName = "redirect:/condominios/" + cid + "/casas/" + pid + "/pagos";
		var casa = casasService.findById(pid);
		var condominio = condominioService.findById(cid);
		model.addAttribute("breadcrum", getBreadcrum(pago));
		if (binding.hasErrors()) {
			flash.addFlashAttribute("alert_danger", "Los datos del pago son incorrectos. Favor de corregir y volver a intentar");
			binding.getAllErrors().stream().forEach(error -> {
				System.out.println(error.getObjectName() +" - " + error.getDefaultMessage());
			});
			return viewName;
		}
		pago.setCasa(casa);
		pago.setCondominio(condominio);
		pagoService.save(pago);
		flash.addFlashAttribute("alert_success", "El pago ha quedado registrado");
		return viewName;
	}

	@PostMapping("/condominios/{cid}/pagos/nuevo")
	public String postAdd(
			@PathVariable String cid,
			@ModelAttribute(name = "abono") @Valid Pago pago,
			BindingResult binding,
			RedirectAttributes flash,
			Model model
	) {
		var viewName = "redirect:/condominios/" + cid + "/ingresos";
		var condominio = condominioService.findById(cid);
		if (binding.hasErrors()) {
			binding.getFieldErrors().stream().forEach(error -> {
				System.out.println(error.getField() +" - " + error.getDefaultMessage());
			});
			flash.addFlashAttribute("alert_danger", "Los datos del pago son incorrectos. Favor de corregir y volver a intentar");
			return viewName;
		}
		pago.setCondominio(condominio);
		pagoService.save(pago);
		flash.addFlashAttribute("alert_success", "El pago ha quedado registrado");
		return viewName;
	}
}
