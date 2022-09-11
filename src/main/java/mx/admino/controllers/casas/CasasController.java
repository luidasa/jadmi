package mx.admino.controllers.casas;

import mx.admino.factories.BreadcrumFactory;
import mx.admino.models.entities.Casa;
import mx.admino.models.entities.Condominio;
import mx.admino.services.CondominioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import mx.admino.services.CasasService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CasasController {

	@Autowired
	CondominioService condominioService;

	@Autowired
	CasasService casasService;

	@Autowired
	ApplicationEventPublisher eventPublisher;

	@GetMapping("/condominios/{id}/casas")
	public String index(
			@PathVariable String id,
			@RequestParam(required = false, defaultValue =  "1") Integer page,
			@RequestParam(required = false, defaultValue =  "10")Integer size,
			Model model) {

		Condominio condominio = condominioService.findById(id);

		Pageable pageable = PageRequest.of(page - 1, size);
		Page<Casa> casas = casasService.findByCondominio(condominio, pageable);
		model.addAttribute("condominio", condominio);
		model.addAttribute("casas", casas);
		model.addAttribute("breadcrum", BreadcrumFactory.getBreadcrum(condominio, null));
		return "casas/index";
	}

	@GetMapping("/condominios/{cid}/casas/generar")
	public String getGenerar(@PathVariable String cid,
							 RedirectAttributes flash) {

		Condominio condominio = condominioService.findById(cid);

		for (int i = 1; i  <= condominio.getUnidades(); i++) {
			Casa nuevaCasa = new Casa(condominio, "Interior " + i);
			casasService.save(nuevaCasa);
		}

		flash.addFlashAttribute("alert_succes", "Se generaron las casas del condominio");
		return "redirect:/condominios/" + cid + "/casas";
	}

}
