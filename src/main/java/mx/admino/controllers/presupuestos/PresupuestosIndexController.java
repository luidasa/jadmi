package mx.admino.controllers.presupuestos;

import mx.admino.models.Breadcrum;
import mx.admino.models.entities.Condominio;
import mx.admino.models.entities.Presupuesto;
import mx.admino.services.CondominioService;
import mx.admino.services.PresupuestosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PresupuestosIndexController {

    @Autowired
    private CondominioService condominioService;

    @Autowired
    private PresupuestosService presupuestosService;

    private List<Breadcrum> getBreadcrum(Condominio condominio) {

        List<Breadcrum> x = new ArrayList<Breadcrum>();
        x.add(new Breadcrum("Inicio", "/panel", false));
        x.add(new Breadcrum("Condominios", "/condominios", false));
        x.add(new Breadcrum(condominio.getNombre(), "/condominios/" +  condominio.getId() , false));
        x.add(new Breadcrum("Presupuestos", "/condominios/" +  condominio.getId() + "/presupuestos" , true));
        return x;
    }

    @GetMapping("/condominios/{cid}/presupuestos")
    public String index(
            @PathVariable Long cid,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer rows,
            Model model) {

        var condominio = condominioService.findById(cid);

        Pageable pageable = PageRequest.of(page - 1, rows, Sort.by("fechaInicio", "desc"));
        model.addAttribute("breadcrum", getBreadcrum(condominio));
        Page<Presupuesto> presupuestos = presupuestosService.findByCondominio(condominio, pageable);
        model.addAttribute("pagina", presupuestos );
        model.addAttribute("condominio", condominio );

        return "presupuestos/index";
    }

}
