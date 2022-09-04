package mx.admino.controllers.admin.condominos;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.admino.models.Breadcrum;
import mx.admino.models.entities.Condomino;
import mx.admino.services.CondominoService;

@Controller
@RequestMapping("/admin/condominos")
public class CondominoController {

	@Autowired
	CondominoService condominoService;

	@Autowired
	ApplicationEventPublisher eventPublisher;

	private List<Breadcrum> getBreadcrum(Condomino condomino) {

		List<Breadcrum> x = new ArrayList<Breadcrum>();
		x.add(new Breadcrum("Inicio", "/admin/panel", false));
		x.add(new Breadcrum("Condominos", "/admin/condominos", condomino == null));
		if (condomino != null) {
			x.add(new Breadcrum(condomino.getInterior(), "/admin/condominos/" + condomino.getId(), true));
		} else {
			x.add(new Breadcrum("Nuevo", "/admin/condominos/nuevo", true));
		}
		return x ;
	}
	
	@GetMapping()
	public String index(
			@RequestParam(required = false, defaultValue =  "1") Integer page,
			@RequestParam(required = false, defaultValue =  "10")Integer size,
			Model model) {
		
		Pageable pageable = PageRequest.of(page - 1, size);
		PageImpl<Condomino> condominos = (PageImpl<Condomino>) condominoService.findAll(pageable);
		model.addAttribute("condominos", condominos);
		model.addAttribute("breadcrum", getBreadcrum(null));
		return "condominos/index";
	}
}
