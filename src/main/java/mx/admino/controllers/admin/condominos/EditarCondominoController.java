package mx.admino.controllers.admin.condominos;

import mx.admino.events.OnUserRegisteredEvent;
import mx.admino.models.Breadcrum;
import mx.admino.models.entities.Condomino;
import mx.admino.models.entities.Roles;
import mx.admino.models.entities.Usuario;
import mx.admino.services.CondominoService;
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
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin/condominos")
public class EditarCondominoController {

    @Autowired
    ApplicationEventPublisher eventPublisher;

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
        if (condomino != null) {
            x.add(new Breadcrum(condomino.getInterior(), "/admin/condominos/" + condomino.getId(), true));
        }
        return x ;
    }

    @GetMapping("/{id}")
    public String getEdit(
            @PathVariable String id,
            Model model) {

        Condomino condomino = condominoSrv.findById(id);
        model.addAttribute("condomino", condomino );
        model.addAttribute("breadcrum", getBreadcrum(condomino));
        return "condominos/formulario";
    }


    @PostMapping("/{id}")
    public String postEdit(
            @PathVariable String id,
            @ModelAttribute @Valid Condomino condomino,
            BindingResult binding,
            RedirectAttributes flash,
            Model model) {
        String viewName = "condominos/formulario";

        if (binding.hasErrors()) {
            return viewName;
        }
        // Verificamos si NO tenemos de alta un usuario con el correo y la dirección
        Usuario usuariodb = usuarioSrv.findByEmail(condomino.getCorreo());
        if (usuariodb == null) {
            usuariodb = new Usuario(
                    condomino.getCorreo(),
                    encoder.encode(UUID.randomUUID().toString()),
                    Arrays.asList(new Roles[] {
                            Roles.ROLE_CONDOMINO
                    }),
                    condomino.getNombre(),
                    condomino.getTelefono(),
                    condomino.getCorreo(),
                    false);
            usuarioSrv.create(usuariodb);
            eventPublisher.publishEvent(new OnUserRegisteredEvent(usuariodb, "http://localhost:8080"));
        }
        condomino.setUsername(usuariodb.getUsername());
        Condomino itemdb = condominoSrv.findById(id);
        itemdb.merge(condomino);
        condominoSrv.save(itemdb);
        flash.addFlashAttribute("alert_success", "Hemos guardado la información actualizada del condomino.");
        viewName = "redirect:/admin/condominos";
        return viewName;
    }

}
