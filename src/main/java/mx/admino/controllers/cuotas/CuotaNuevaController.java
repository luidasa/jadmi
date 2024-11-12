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
public class CuotaNuevaController {

    @Autowired
    CuotaService cuotaService;

    @Autowired
    CondominioService condominioService;

    private List<Breadcrum> getBreadcrum(Condominio condominio) {

        List<Breadcrum> x = new ArrayList<Breadcrum>();
        x.add(new Breadcrum("Inicio", "/panel", false));
        x.add(new Breadcrum("Condominios", "/panel/condominios", false));
        x.add(new Breadcrum(condominio.getNombre(), "/panel/condominios/" + condominio.getId() , false));
        x.add(new Breadcrum("Cuotas", "/panel/condominios/" + condominio.getId() + "/cuotas", false));
        x.add(new Breadcrum( "Nuevo","/panel/condominios/" + condominio.getId() + "/cuotas/nuevo", true));

        return x ;
    }

    @GetMapping("/condominios/{cid}/cuotas/nuevo")
    public String getNuevo(
            @PathVariable String cid,
            @ModelAttribute Cuota cuota,
            Model model) {

        var condominio = condominioService.findById(cid);
        model.addAttribute("breadcrum", getBreadcrum(condominio));
        model.addAttribute("condominio", condominio);
        return "cuotas/formulario";
    }

    @PostMapping("/condominios/{cid}/cuotas/nuevo")
    public String postNuevo(
            @PathVariable String cid,
            @ModelAttribute @Valid Cuota cuota,
            BindingResult binding,
            RedirectAttributes flash,
            Model model) {

        String viewName = "cuotas/formulario";
        var condominio = condominioService.findById(cid);
        if (binding.hasErrors()) {
            model.addAttribute("condominio", condominio);
            model.addAttribute("breadcrum", getBreadcrum(condominio));
            return viewName;
        }
        cuotaService.save(cuota);
        viewName = "redirect:/condominios/" + condominio.getId() + "/cuotas/" + cuota.getId();
        flash.addFlashAttribute("alert_success", "Se agrego una nueva cuota para todos los condominos");
        return viewName;
    }
}
