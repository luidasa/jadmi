package mx.admino.controllers.condominios;

import mx.admino.factories.BreadcrumFactory;
import mx.admino.models.entities.Condominio;
import mx.admino.models.entities.Usuario;
import mx.admino.services.CondominioService;
import mx.admino.services.CasasService;
import mx.admino.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class CrearCondominioController {

    @Autowired
    CondominioService condominioSrv;

    @Autowired
    UsuarioService usuarioSrv;

    @Autowired
    CasasService condominoSrv;

    @ModelAttribute
    private Condominio getCondominio(Model model) {
        Condominio nuevo = new Condominio();
        model.addAttribute("condominio", nuevo);
        model.addAttribute("breadcrum", BreadcrumFactory.getBreadcrum(nuevo));
        return  nuevo;
    }

    @GetMapping("/condominios/nuevo")
    public String getNuevoCondominio() {
        return "condominio/formulario";
    }

    @PostMapping("/condominios/nuevo")
    public String postNuevo(
            @ModelAttribute @Valid Condominio condominio,
            BindingResult binding,
            RedirectAttributes flash,
            Model model,
            Principal auth) {

        Condominio condominiodb = condominioSrv.findByNombre(condominio.getNombre());
        if (condominiodb != null) {
            binding.addError(new FieldError("condominio", "nombre", "El nombre del condominio ya ha sido registrado."));
        }

        if (binding.hasErrors()) {
            binding.getAllErrors().stream().forEach(error -> System.out.println(error));
            model.addAttribute("alert_warning", "La información que proporacionaste tiene errores. Favor de corregir y vovler a enviar");
            model.addAttribute("breadcrum", BreadcrumFactory.getBreadcrum(condominio) );
            return "condominio/formulario";
        }

        // Asignaos el usuario como administrador del condominio
        Usuario administrador = usuarioSrv.findByUsername(auth.getName());
        condominio.setAdministrador(administrador);

        // Guardamos el condominio
        Condominio nuevo = condominioSrv.save(condominio);

        flash.addFlashAttribute("alert_success", "Se registro el condominio con usted como administrador.");
        return "redirect:/condominios";
    }
}
