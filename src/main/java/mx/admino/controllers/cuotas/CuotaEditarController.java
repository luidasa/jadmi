package mx.admino.controllers.cuotas;

import mx.admino.models.Breadcrum;
import mx.admino.models.entities.Condominio;
import mx.admino.models.entities.Cuota;
import mx.admino.services.CondominioService;
import mx.admino.services.CuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CuotaEditarController {

    @Autowired
    CondominioService condominioService;

    @Autowired
    CuotaService cuotaService;

    private List<Breadcrum> getBreadcrum(Condominio condominio, Cuota cuota) {

        List<Breadcrum> x = new ArrayList<Breadcrum>();
        x.add(new Breadcrum("Inicio", "/panel", false));
        x.add(new Breadcrum("Condominios", "/condominios", false));
        x.add(new Breadcrum(condominio.getNombre(), "/condominios/" + condominio.getId() , false));
        x.add(new Breadcrum("Cuotas", "/condominios/" + condominio.getId() + "/cuotas", false));
        x.add(new Breadcrum( cuota.getNombre(),"/condominios/" + condominio.getId() + "/cuotas/" + cuota.getId(), true));

        return x ;
    }

    @GetMapping("/condominios/{cid}/cuotas/{id}")
    public String getEdit(
            @PathVariable Long cid,
            @PathVariable Long id,
            Model model) {

        String viewName = "cuotas/formulario";

        var condominio = condominioService.findById(cid);
        var cuota = cuotaService.findById(id);

        model.addAttribute("breadcrum", getBreadcrum(condominio, cuota));
        model.addAttribute("condominio", condominio);
        model.addAttribute("cuota", cuota);
        return viewName ;
    }

    @PostMapping("/condominios/{cid}/cuotas/{id}")
    public String postEdit(
            @PathVariable Long cid,
            @PathVariable Long id,
           @ModelAttribute @Valid Cuota cuota,
           BindingResult binding,
           RedirectAttributes flash,
           Model model) {

        String viewName = "cuotas/formulario";
        var condominio = condominioService.findById(cid);
        var cuotaDb = cuotaService.findById(id);
        if (binding.hasErrors()) {
            model.addAttribute("breadcrum", getBreadcrum(condominio, cuotaDb));
            model.addAttribute("condominio", condominio);
            return viewName;
        }

        cuotaDb.merge(cuota);
        cuotaService.save(cuotaDb);
        viewName = "redirect:/condominios/" + condominio.getId() + "/cuotas/" + cuota.getId();
        flash.addFlashAttribute("alert_success", "Se actualizo la cuota para todos los condominos");
        return viewName ;
    }

}
