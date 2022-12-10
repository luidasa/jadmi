package mx.admino.controllers.expedientes;

import mx.admino.models.Archivo;
import mx.admino.models.Breadcrum;
import mx.admino.models.entities.Casa;
import mx.admino.models.entities.Condominio;
import mx.admino.services.CasasService;
import mx.admino.services.CondominioService;
import mx.admino.services.ExpedientesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ExpedientesController {

    @Autowired
    private CondominioService condominioService;

    @Autowired
    private CasasService casasService;

    @Autowired
    private ExpedientesService expedientesService;

    private static List<Breadcrum> getBreadcrum(Condominio condominio, Casa casa) {

        List<Breadcrum> x = new ArrayList<Breadcrum>();
        x.add(new Breadcrum("Inicio", "/panel", false));
        x.add(new Breadcrum("Condominios", "/condominios", false));
        x.add(new Breadcrum(condominio.getNombre(), "/condominios/" + condominio.getId(), false));
        if (casa == null) {
            x.add(new Breadcrum("Expediente", "/condominios/" + condominio.getId() + "/archivos", true));
        } else {
            x.add(new Breadcrum("Casas", "/condominios/" + condominio.getId() + "/casas", false));
            x.add(new Breadcrum(casa.getInterior(), "/condominios/" + condominio.getId() + "/casas/" + casa.getId(), false));
            x.add(new Breadcrum("Expediente", "/condominios/" + condominio.getId() + "/casas/" + casa.getId() + "/archivos", true));
        }
        return x;
    }

    @GetMapping("/condominios/{cid}/casas/{uid}/archivos")
    public String getExpedientes (
            @PathVariable String cid,
            @PathVariable String uid,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            Model model) {

        Pageable pageable = PageRequest.of(page - 1, size);

        var condominio = condominioService.findById(cid);
        var casa = casasService.findById(uid);

        var archivos = expedientesService.findByCasa(casa, pageable);
        var nuevoArchivo = new Archivo();

        model.addAttribute("condominio", condominio);
        model.addAttribute("breadcrum", getBreadcrum(condominio, casa));
        model.addAttribute("casa", casa);
        model.addAttribute("archivos", archivos);
        model.addAttribute("narchivo", nuevoArchivo);

        return "expedientes/index";
    }
}
