package mx.admino.controllers.cuotas;

import java.util.ArrayList;
import java.util.List;

import mx.admino.models.entities.Condominio;
import mx.admino.services.CondominioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.admino.models.Breadcrum;
import mx.admino.models.CuotaEstatus;
import mx.admino.models.entities.Cuota;
import mx.admino.services.CuotaService;

@Controller
public class CuotasIndexController {

	@Autowired
	CondominioService condominioService;

	@Autowired
	CuotaService cuotaService;

	private List<Breadcrum> getBreadcrum(Condominio condominio) {

		List<Breadcrum> x = new ArrayList<Breadcrum>();
		x.add(new Breadcrum("Inicio", "/panel", false));
		x.add(new Breadcrum("Condominios", "/condominios", false));
		x.add(new Breadcrum(condominio.getNombre(), "/condominios/" +  condominio.getId() , false));
		x.add(new Breadcrum("Cuotas", "/condominios/" +  condominio.getId() + "/cuotas" , true));
		return x;
	}
	
	@GetMapping("/condominios/{cid}/cuotas")
	public String index(
			@PathVariable String cid,
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer rows,
			Model model) {

		var condominio = condominioService.findById(cid);

		Pageable pageable = PageRequest.of(page - 1, rows, Sort.by("fechaInicio", "desc"));
		model.addAttribute("breadcrum", getBreadcrum(condominio));
		Page<Cuota> cuotas = cuotaService.findAll(pageable);
		model.addAttribute("pagina", cuotas );
		model.addAttribute("condominio", condominio );

		return "cuotas/index";
	}

	@PostMapping("/condominios/{cid}/cuotas/{id}/borrar")
	public String getDelete(
			@PathVariable String id,
			RedirectAttributes flash,
			Model model) {
		
		cuotaService.deleteById(id);
		flash.addFlashAttribute("alert_success", "La cuota ha sido eliminado.");
		return "redirect:/cuotas";
	}
	
	@GetMapping("/condominios/{cid}/cuotas/{id}/planear")
	public String getSchedule(
			@PathVariable String id,
			RedirectAttributes flash,
			Model model) {
		Cuota cuota = cuotaService.findById(id);
		if (cuota.getEstatus() == CuotaEstatus.REGISTRADO) {
			cuotaService.schedule(cuota);
			flash.addFlashAttribute("alert_success", "Se hizo el calendario de pagos para los condominos.");
		} else {
			flash.addFlashAttribute("alert_danger", "No se puede generar el calendario para esta cuota.");
		}
		return "redirect:/admin/cuotas";
	}
}
