package mx.admino.controllers.casas;

import mx.admino.factories.BreadcrumFactory;
import mx.admino.models.entities.Casa;
import mx.admino.models.entities.Condominio;
import mx.admino.services.CasasService;
import mx.admino.services.CondominioService;
import mx.admino.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class EditarCasaController {

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    CasasService casasService;

    @Autowired
    CondominioService condominioService;

    @Autowired
    UsuarioService usuarioSrv;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/condominios/{cid}/casas/{id}")
    public String getEdit(
            @PathVariable String cid,
            @PathVariable String id,
            RedirectAttributes flash,
            Model model) {

        Condominio condominio = condominioService.findById(cid);
        Casa casa = casasService.findById(id);

        if (!casa.getCondominio().getId().equals(condominio.getId())) {
            flash.addFlashAttribute("alert_warning", "La casa indicada no corresponde al condominio.");
            return "redirect:/condominios/" + cid + "/casas";
        }

        model.addAttribute("condominio", condominio);
        model.addAttribute("casa", casa);
        model.addAttribute("breadcrum", BreadcrumFactory.getBreadcrum(condominio, casa));
        return "casas/formulario";
    }


    @PostMapping("/condominios/{cid}/casas/{id}")
    public String postEdit(
            @PathVariable String cid,
            @PathVariable String id,
            @ModelAttribute @Valid Casa casa,
            BindingResult binding,
            RedirectAttributes flash,
            Model model) {

        Condominio condominio = condominioService.findById(cid);
        Casa itemdb = casasService.findById(id);

        if (binding.hasErrors()) {
            binding.getAllErrors().stream().forEach(x -> System.out.println(x));
            model.addAttribute("alert_warning", "Los datos sons inorrectos. Favor de verificar y vovler a intentar");
            model.addAttribute("condominio", condominio);
            model.addAttribute("breadcrum", BreadcrumFactory.getBreadcrum(condominio, casa));
            return "casas/formulario";
        }
        itemdb.merge(casa);
        casasService.save(itemdb);
        flash.addFlashAttribute("alert_success", "Hemos guardado la información actualizada del condomino.");
        return "redirect:/condominios/" + condominio.getId() + "/casas";
    }

}
