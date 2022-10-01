package mx.admino.controllers.presupuestos;

import mx.admino.models.Breadcrum;
import mx.admino.models.Presupuesto;
import mx.admino.models.entities.Condominio;
import mx.admino.services.CondominioService;
import mx.admino.services.PresupuestosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PresupuestosNuevoController {

    @Autowired
    CondominioService condominioService;

    @Autowired
    PresupuestosService presupuestosService;

    private List<Breadcrum> getBreadcrum(Condominio condominio) {

        List<Breadcrum> x = new ArrayList<Breadcrum>();
        x.add(new Breadcrum("Inicio", "/panel", false));
        x.add(new Breadcrum("Condominios", "/condominios", false));
        x.add(new Breadcrum(condominio.getNombre(), "/condominios/" + condominio.getId() , false));
        x.add(new Breadcrum("Presupuestos", "/condominios/" + condominio.getId() + "/presupuestos", false));
        x.add(new Breadcrum( "Nuevo","/condominios/" + condominio.getId() + "/presupuestos/nuevo", true));

        return x ;
    }

    @GetMapping("/condominios/{cid}/presupuestos/nuevo")
    public String getNuevo(
            @PathVariable String cid,
            Model model
    ) {
        var condominio = condominioService.findById(cid);
        var presupuesto = new Presupuesto();

        model.addAttribute("condominio", condominio);
        model.addAttribute("presupuesto", presupuesto);
        model.addAttribute("breadcrum", getBreadcrum(condominio));
        return "presupuestos/formulario";
    }
}
