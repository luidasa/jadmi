package mx.admino.controllers.condominios;

import mx.admino.factories.BreadcrumFactory;
import mx.admino.models.entities.Condominio;
import mx.admino.models.entities.Usuario;
import mx.admino.services.CondominioService;
import mx.admino.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class EditarCondominioController {

    @Autowired
    CondominioService condominioService;

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/condominios/{id}")
    public String getEditar(
            @PathVariable String id,
            Model model) {

        var condominio = condominioService.findById(id);
        model.addAttribute("condominio", condominio);
        model.addAttribute("breadcrum", BreadcrumFactory.getBreadcrum(condominio) );
        return "condominio/formulario";
    }

    @PostMapping("/condominios/{id}")
    public String postEditar(
            @PathVariable String id,
            @ModelAttribute @Valid Condominio condominio,
            BindingResult binding,
            RedirectAttributes flash,
            Model model,
            Principal auth
    ) {

        // Verificamos que el nombre no este duplicado
        Condominio ccheckName = condominioService.findByNombre(condominio.getNombre());
        if ((ccheckName != null) && (!condominio.getId().equals(ccheckName.getId()))) {
            binding.addError(new FieldError("condominio", "nombre", "El nombre del condominio ya ha sido registrado."));
        }

        // Verificamos que exista el id
        var cdb = condominioService.findById(id);

        // Verificamos que se trate del administrador.
        var currentUser = usuarioService.findByUsername(auth.getName());
        if (!cdb.getAdministrador().getId().equals(currentUser.getId())) {
            binding.addError(new FieldError("condominio", "nombre", "Solo el administrador puede modificar los campos del condominio"));
        }
        if (binding.hasErrors()) {
            binding.getAllErrors().stream().forEach(error -> System.out.println(error));
            model.addAttribute("alert_warning", "La información que proporacionaste tiene errores. Favor de corregir y vovler a enviar");
            model.addAttribute("breadcrum", BreadcrumFactory.getBreadcrum(condominio) );
            return "condominio/formulario";
        }

        cdb.merge(condominio);
        // Guardamos el condominio
        cdb = condominioService.save(cdb);

        flash.addFlashAttribute("alert_success", "La información del condominio se actualizo satisfactoriamente.");
        return "redirect:/condominios";


    }
}
