package mx.admino.controllers.condomino;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import mx.admino.models.Breadcrum;
import mx.admino.models.entities.Cargo;
import mx.admino.services.IndicadoresService;

/**
 * Controlador que muestra el panel de control para cada uno de los condominos, es decir,
 * aquellos que no son administradores del condominio.
 */
@Controller
public class PanelCondominioController {
	
	@Autowired
	IndicadoresService service;

	private List<Breadcrum> getBreadcrum(Cargo cargo) {

		List<Breadcrum> x = new ArrayList<Breadcrum>();
		x.add(new Breadcrum("Inicio", "/condominio", true));
		return x ;
	}

	@GetMapping("/condominio/panel")
	public String getPanel(Model model) {
		
		LocalDate fin = LocalDate.now();
		LocalDate inicio = fin.minusDays(fin.getDayOfMonth());
		
		var indicadores = service.findByPeriod(
				Date.from(inicio.atStartOfDay(ZoneId.systemDefault()).toInstant()), 
				Date.from(fin.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		
		model.addAttribute("breadcrum", getBreadcrum(null));
		model.addAttribute("indicadores", indicadores);
		
		return "condominio/panel";
	}
}
