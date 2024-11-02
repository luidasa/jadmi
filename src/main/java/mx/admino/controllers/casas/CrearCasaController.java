package mx.admino.controllers.casas;

import mx.admino.factories.BreadcrumFactory;
import mx.admino.models.entities.Casa;
import mx.admino.services.CasasService;
import mx.admino.services.CondominioService;
import mx.admino.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
public class CrearCasaController {

    @Autowired
    CondominioService condominioService;

    @Autowired
    CasasService casasService;

    @Autowired
    UsuarioService usuarioSrv;


    @GetMapping("/condominios/{cid}/casas/nuevo")
    public String getCreate(
            @PathVariable String cid,
            Model model) {

        var condominio = condominioService.findById(cid);
        var casa = new Casa(condominio);

        model.addAttribute("breadcrum", BreadcrumFactory.getBreadcrum(condominio, casa));
        model.addAttribute("condominio", condominio);
        model.addAttribute("casa", casa);
        return "casas/formulario";
    }

    @PostMapping("/condominios/{cid}/casas/nuevo")
    public String postEdit(
            @PathVariable String cid,
            @ModelAttribute @Valid Casa casa,
            BindingResult binding,
            RedirectAttributes flash,
            Model model) {

        var condominio = condominioService.findById(cid);

        if (binding.hasErrors()) {
            model.addAttribute("breadcrum", BreadcrumFactory.getBreadcrum(condominio, casa));
            model.addAttribute("condominio", condominio);
            return "casas/formulario";
        }

        casa.setCondominio(condominio);
        casasService.save(casa);
        flash.addFlashAttribute("alert_success", "Hemos agregado una nueva casa al condominio.");
        return "redirect:/condominios/" + cid + "/casas";
    }
}
