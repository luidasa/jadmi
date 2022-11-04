package mx.admino.controllers.egresos;

import mx.admino.models.Breadcrum;
import mx.admino.models.entities.Casa;
import mx.admino.models.entities.Condominio;
import mx.admino.models.entities.Egreso;
import mx.admino.services.CondominioService;
import mx.admino.services.EgresosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EgresosController {

    @Autowired
    CondominioService condominioService;

    @Autowired
    EgresosService egresosService;

    private List<Breadcrum> getBreadcrum(Condominio condominio) {

        DateFormatter format = new DateFormatter("dd/MM/yyyy");

        List<Breadcrum> x = new ArrayList<Breadcrum>();
        x.add(new Breadcrum("Inicio", "/panel", false));
        x.add(new Breadcrum("Condominios", "/condominios", false));
        x.add(new Breadcrum(condominio.getNombre(), "/condominios/" + condominio.getId(), false));
        x.add(new Breadcrum("Pagos", "/condominios/" + condominio.getId() + "/egresos", true));
        return x ;
    }


    @GetMapping("/condominios/{cid}/egresos")
    public String index(
        @PathVariable String cid,
        @RequestParam(required = false, defaultValue = "1") Integer page,
        @RequestParam(required = false, defaultValue = "10") Integer size,
        Model model) {

        Sort ordenado = Sort.by("fechaVencimiento").descending();
        Pageable pageable = PageRequest.of(page - 1, size, ordenado);

        var condominio = condominioService.findById(cid);
        var gasto = new Egreso(condominio);
        Page<Egreso> egresos = egresosService.findByCondominio(condominio, pageable);

        String tomorrow  = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        model.addAttribute("tomorrow", tomorrow);
        model.addAttribute("condominio", condominio);
        model.addAttribute("egresos", egresos);
        model.addAttribute("gasto", gasto);
        model.addAttribute("breadcrum", getBreadcrum(condominio));

        return "egresos/index";
    }
}
