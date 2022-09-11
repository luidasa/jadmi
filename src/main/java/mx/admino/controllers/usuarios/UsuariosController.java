package mx.admino.controllers.usuarios;

import mx.admino.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    UsuarioService usuarioSrv;

    @GetMapping
    public String getIndex(
            Model model) {

        return "usuarios/index";
    }


}
