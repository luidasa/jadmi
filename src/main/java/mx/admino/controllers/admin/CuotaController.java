package mx.admino.controllers.admin;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.admino.models.Breadcrum;
import mx.admino.models.CuotaEstatus;
import mx.admino.models.entities.Cuota;
import mx.admino.services.CuotaService;

@Controller
@RequestMapping("/admin/cuotas")
public class CuotaController {

	@Autowired
	CuotaService cuotaService;
		
	private List<Breadcrum> getBreadcrum(Cuota cuota) {

		List<Breadcrum> x = new ArrayList<Breadcrum>();
		x.add(new Breadcrum("Inicio", "/panel", false));
		x.add(new Breadcrum("Cuotas", "/cuotas", cuota == null));
		if (cuota != null) {
			x.add(new Breadcrum(
					cuota.getId() !=null ? cuota.getNombre() : "Nuevo", 
					"/cuotas/" + (cuota.getId() !=null ? cuota.getId() : "nuevo"),
					true));
		}
		return x ;
	}
	
	@GetMapping
	public String index(
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer rows,
			Model model) {
		Pageable pageable = PageRequest.of(page - 1, rows, Sort.by("fechaInicio", "desc"));
		model.addAttribute("breadcrum", getBreadcrum(null));
		Page<Cuota> cuotas = cuotaService.findAll(pageable);
		model.addAttribute("cuotas", cuotas );

		return "cuotas/index";
	}
	
	@GetMapping("/nuevo")
	public String getNuevo(
			@ModelAttribute Cuota cuota,
			Model model) {
		
		model.addAttribute("breadcrum", getBreadcrum(cuota));
		return "cuotas/formulario";
	}
	
	@PostMapping("/nuevo")
	public String postNuevo(@ModelAttribute @Valid Cuota cuota,
			BindingResult binding,
			RedirectAttributes flash,
			Model model) {
		
		String viewName = "cuotas/formulario";
		if (binding.hasErrors()) {
			model.addAttribute("breadcrum", getBreadcrum(cuota));
			return viewName;
		}
		cuotaService.save(cuota);
		viewName = "redirect:/admin/cuotas/" + cuota.getId();
		flash.addFlashAttribute("alert_success", "Se agrego una nueva cuota para todos los condominos");
		return viewName;
	}
	
	@GetMapping("/{id}")
	public String getEdit(@PathVariable String id,
			Model model) {
		String viewName = "cuotas/formulario";
		
		Cuota cuota = cuotaService.findById(id);
		model.addAttribute("breadcrum", getBreadcrum(cuota));
		model.addAttribute("cuota", cuota );
		return viewName ;
	}
	
	@PostMapping("/{id}")
	public String postEdit(@PathVariable String id,
			@ModelAttribute @Valid Cuota cuota,
			BindingResult binding,
			RedirectAttributes flash,
			Model model) {
		String viewName = "cuotas/formulario";
		Cuota cuotaDb = cuotaService.findById(id);
		if (binding.hasErrors()) {
			model.addAttribute("breadcrum", getBreadcrum(cuotaDb));
			return viewName;
		}
		
		cuotaDb.merge(cuota);
		cuotaService.save(cuotaDb);
		viewName = "redirect:/cuotas";
		flash.addFlashAttribute("alert_success", "Se actualizo la cuota para todos los condominos");		
		return viewName ;
	}
	
	@GetMapping("/delete/{id}")
	public String getDelete(
			@PathVariable String id,
			RedirectAttributes flash,
			Model model) {
		
		cuotaService.deleteById(id);
		flash.addFlashAttribute("alert_success", "La cuota ha sido eliminado.");
		return "redirect:/cuotas";
	}
	
	@GetMapping("/schedule/{id}")
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
