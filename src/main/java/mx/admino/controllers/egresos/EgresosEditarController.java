package mx.admino.controllers.egresos;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class EgresosEditarController {


    @GetMapping("/condominios/{cid}/egresos/{eid}")
    public String getDetalleEgreso(
            @PathVariable String cid,
            @PathVariable String eid,
            Model model
    ) {

        return "egresos/show";
    }

}
