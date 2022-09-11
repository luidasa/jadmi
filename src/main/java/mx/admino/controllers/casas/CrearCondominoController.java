package mx.admino.controllers.casas;

import mx.admino.models.Breadcrum;
import mx.admino.models.entities.Casa;
import mx.admino.services.CasasService;
import mx.admino.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CrearCondominoController {

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
        if ((casa != null) && (casa.getId() != null))  {
            x.add(new Breadcrum(casa.getInterior(), "/admin/condominos/" + casa.getId(), true));
        } else {
            x.add(new Breadcrum("Nuevo", "/admin/condominos/nuevo", true));
        }
        return x ;
    }

    @GetMapping("/nuevo")
    public String getCreate(Model model) {
        model.addAttribute("breadcrum", getBreadcrum(null));
        model.addAttribute("condomino", new Casa());
        return "condominos/formulario";
    }

    @PostMapping("/nuevo")
    public String postEdit(
            @ModelAttribute @Valid Casa casa,
            BindingResult binding,
            RedirectAttributes flash,
            Model model) {
        String viewName = "condominos/formulario";

        if (binding.hasErrors()) {
            return viewName;
        }

        condominoSrv.save(casa);
        flash.addFlashAttribute("alert_success", "Hemos guardado la información actualizada del condomino.");
        viewName = "redirect:/admin/condominos";
        return viewName;
    }
}
