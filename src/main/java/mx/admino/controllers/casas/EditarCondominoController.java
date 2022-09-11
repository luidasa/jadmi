package mx.admino.controllers.casas;

import mx.admino.models.Breadcrum;
import mx.admino.models.entities.Casa;
import mx.admino.services.CasasService;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/condominos")
public class EditarCondominoController {

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    CasasService condominoSrv;

    @Autowired
    UsuarioService usuarioSrv;

    @Autowired
    PasswordEncoder encoder;

    private List<Breadcrum> getBreadcrum(Casa casa) {

        List<Breadcrum> x = new ArrayList<Breadcrum>();
        x.add(new Breadcrum("Inicio", "/admin/panel", false));
        x.add(new Breadcrum("Condominos", "/admin/condominos", casa == null));
        if (casa != null) {
            x.add(new Breadcrum(casa.getInterior(), "/admin/condominos/" + casa.getId(), true));
        }
        return x ;
    }

    @GetMapping("/{id}")
    public String getEdit(
            @PathVariable String id,
            Model model) {

        Casa casa = condominoSrv.findById(id);
        model.addAttribute("condomino", casa);
        model.addAttribute("breadcrum", getBreadcrum(casa));
        return "condominos/formulario";
    }


    @PostMapping("/{id}")
    public String postEdit(
            @PathVariable String id,
            @ModelAttribute @Valid Casa casa,
            BindingResult binding,
            RedirectAttributes flash,
            Model model) {
        String viewName = "condominos/formulario";

        if (binding.hasErrors()) {
            return viewName;
        }
        Casa itemdb = condominoSrv.findById(id);
        itemdb.merge(casa);
        condominoSrv.save(itemdb);
        flash.addFlashAttribute("alert_success", "Hemos guardado la información actualizada del condomino.");
        viewName = "redirect:/admin/condominos";
        return viewName;
    }

}
