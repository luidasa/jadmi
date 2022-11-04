package mx.admino.controllers.egresos;

import mx.admino.models.entities.Egreso;
import mx.admino.services.CondominioService;
import mx.admino.services.EgresosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class EgresoCrearController {

    @Autowired
    CondominioService condominioService;

    @Autowired
    EgresosService egresosService;

    @PostMapping("/condominios/{cid}/egresos/nuevo")
    public String postCrear(
            @PathVariable String cid,
            @ModelAttribute @Valid Egreso egreso,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
            ) {
        String viewName = "redirect:/condominios/" + cid + "/egresos";

        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().stream().forEach(error -> {
                System.err.println(error.getField() + " - " + error.getDefaultMessage());
            });
            redirectAttributes.addFlashAttribute("alert_danger", "Los datos que introduciste con incorrectos. Favor de verificar y vovler a intentar");
            return viewName;
        }
        var condominio = condominioService.findById(cid);
        egreso.setCondominio(condominio);
        egresosService.crear(egreso);
        redirectAttributes.addFlashAttribute("alert_success", "Se registro el gasto satisfactoriamente");

        return viewName;
    }
}
