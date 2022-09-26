package mx.admino.controllers.cargos;

import mx.admino.factories.BreadcrumFactory;
import mx.admino.models.Breadcrum;
import mx.admino.models.entities.Cargo;
import mx.admino.models.entities.Condominio;
import mx.admino.services.CargoService;
import mx.admino.services.CasasService;
import mx.admino.services.CondominioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CargoCrearController {

    @Autowired
    CargoService cargoService;

    @Autowired
    CondominioService condominioService;

    @Autowired
    CasasService casasService;

    private List<Breadcrum> getBreadcrum(Condominio condominio, Cargo cargo) {

        List<Breadcrum> x = new ArrayList<Breadcrum>();
        x.add(new Breadcrum("Inicio", "/panel", false));
        x.add(new Breadcrum("Condominios", "/condominios", condominio == null));
        x.add(new Breadcrum(condominio.getNombre(), "/condominios/" + condominio.getId(), false));
        x.add(new Breadcrum("Cargos", "/condominios/" + condominio.getId() +"/cargos", cargo == null));
        if (cargo != null) {
            if (cargo.getId() != null) {
                x.add(new Breadcrum(cargo.getConcepto(), "/condominios/" + condominio.getId() + "/cargos/" + cargo.getId(), true));
            } else {
                x.add(new Breadcrum("Nueva", "/condominios/" + condominio.getId() + "/cargos/nuevo", true));
            }
        }
        return x ;
    }

    @GetMapping("/condominios/{cid}/cargos/{id}/nuevo")
    public String getNuevo(
            @PathVariable String cid,
            @PathVariable String id,
            Model model) {

        var condominio = condominioService.findById(cid);

        var casa = casasService.findById(id);

        if (!condominio.getUnidades().equals(casa.getCondominio().getId())) {

        }

        Cargo cargo = new Cargo();
        if ((cid!=null) && (!cid.isEmpty()) && (!cid.isBlank())) {
            cargo.setCondomino(casa);
        }
        model.addAttribute("breadcrum", getBreadcrum(condominio, cargo));
        model.addAttribute("cargo", cargo);
        return "cargos/formulario";
    }

    @PostMapping("/condominios/{cid}/cargos/{id}/nuevo")
    public String postNuevo(
            @PathVariable String cid,
            @PathVariable String id,
            @ModelAttribute @Valid Cargo cargo,
            BindingResult binding,
            RedirectAttributes flash,
            Model model) {
        String viewName = "cargos/formulario";

        var condominio = condominioService.findById(cid);
        var casa = casasService.findById(id);

        model.addAttribute("breadcrum", getBreadcrum(condominio, cargo));
        if (binding.hasErrors()) {
            return viewName;
        }
        cargoService.save(cargo);
        viewName = "redirect:/cargos";
        flash.addFlashAttribute("alert_success", "Cargo registrado en el condomino.");
        return viewName;
    }
}
