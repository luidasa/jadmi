package mx.admino.controllers.usuarios;

import mx.admino.models.entities.Figura;
import mx.admino.services.CondominioService;
import mx.admino.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class UsuariosController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    CondominioService condominioService;

    @GetMapping("/condominios/{cid}/usuarios")
    public String getIndex(
            @PathVariable String cid,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer rows,
            Principal auth,
            Model model) {

        Pageable pageable = PageRequest.of(page - 1, rows);
        var condominio = condominioService.findById(cid);
        Page<Figura> usuario = usuarioService.findByCondominio(condominio, pageable);
        return "usuarios/index";
    }


}
