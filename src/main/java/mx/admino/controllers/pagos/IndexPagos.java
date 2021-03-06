package mx.admino.controllers.pagos;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import mx.admino.models.Breadcrum;
import mx.admino.models.Condomino;
import mx.admino.models.Pago;
import mx.admino.services.CondominoService;
import mx.admino.services.PagoService;

@Controller
public class IndexPagos {

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
	
	@GetMapping("/pagos")
	public String index(Model model, 
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer size,
			@RequestParam(required = false) String cid) {

		Page<Pago> pagos ;
		Sort ordenado = Sort.by("fechaPagado").descending();
		Pageable pageable = PageRequest.of(page - 1, size, ordenado);
		if (cid != null) {
			pagos = pagoService.findByCondominoId(cid, pageable);			
		} else {
			pagos = pagoService.findAll(pageable);			
		}
		model.addAttribute("pagos", pagos);
		model.addAttribute("breadcrum", getBreadcrum(null));
		return "pagos/index";
	}
}
