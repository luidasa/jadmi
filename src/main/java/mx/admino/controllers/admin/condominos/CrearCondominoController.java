package mx.admino.controllers.admin.condominos;

import mx.admino.models.Breadcrum;
import mx.admino.models.entities.Condomino;
import mx.admino.services.CondominoService;
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
    CondominoService condominoSrv;

    @Autowired
    UsuarioService usuarioSrv;

    @Autowired
    PasswordEncoder encoder;

    private List<Breadcrum> getBreadcrum(Condomino condomino) {

        List<Breadcrum> x = new ArrayList<Breadcrum>();
        x.add(new Breadcrum("Inicio", "/admin/panel", false));
        x.add(new Breadcrum("Condominos", "/admin/condominos", condomino == null));
        if ((condomino != null) && (condomino.getId() != null))  {
            x.add(new Breadcrum(condomino.getInterior(), "/admin/condominos/" + condomino.getId(), true));
        } else {
            x.add(new Breadcrum("Nuevo", "/admin/condominos/nuevo", true));
        }
        return x ;
    }

    @GetMapping("/nuevo")
    public String getCreate(Model model) {
        model.addAttribute("breadcrum", getBreadcrum(null));
        model.addAttribute("condomino", new Condomino());
        return "condominos/formulario";
    }

    @PostMapping("/nuevo")
    public String postEdit(
            @ModelAttribute @Valid Condomino condomino,
            BindingResult binding,
            RedirectAttributes flash,
            Model model) {
        String viewName = "condominos/formulario";

        if (binding.hasErrors()) {
            return viewName;
        }

        condominoSrv.save( condomino);
        flash.addFlashAttribute("alert_success", "Hemos guardado la información actualizada del condomino.");
        viewName = "redirect:/admin/condominos";
        return viewName;
    }
}
