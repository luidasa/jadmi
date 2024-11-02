package mx.admino.controllers.auth;

import mx.admino.models.Breadcrum;
import mx.admino.models.entities.Usuario;
import mx.admino.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    PasswordEncoder encoder;

    @ModelAttribute
    private void getBreadcrum(Model model) {

        List<Breadcrum> x = new ArrayList<Breadcrum>();
        x.add(new Breadcrum("Inicio", "/panel", false));
        x.add(new Breadcrum("Perfil", "/perfil", true));

        model.addAttribute("breadcrum", x);
    }
    @GetMapping("/perfil")
    public String getProfile(Authentication auth, Model model) {

        Usuario usuarioActual = usuarioService.findByUsername(auth.getName());
        model.addAttribute("usuario", usuarioActual);

        return "auth/profile";
    }

    @PostMapping("/perfil")
    public String postProfile(
            @ModelAttribute @Valid Usuario usuario,
            BindingResult binding,
            RedirectAttributes flash,
            Model model,
            Authentication auth) {


        // Verificamos que el administrador no este registrado.
        var vemail = usuarioService.findByEmail(usuario.getEmail());
        if ((vemail != null) && (!vemail.getUsername().equals(usuario.getUsername()))) {
            System.err.println("Este correo ya esta registrado y no es del mismos usuario");
            binding.addError(new FieldError("usuario", "email", "El correo ya ha sido registrado."));
        }

        // Verificamos que la confirmación de la contraseña y la contraseña sean las mismas
        if (!usuario.getPassword().equals(usuario.getConfirmation())) {
            System.err.print("La contraseña y su confirmación no son iguales.");
            binding.addError(new FieldError("usuario", "password", "La contraseña y su confirmación no son iguales."));
        }

        // Verificamos que los objetos del dominio no tengan error.
        if (binding.hasErrors()) {
            binding.getAllErrors().stream().forEach(x -> System.out.println(x.getDefaultMessage()));
            model.addAttribute("alert_warning", "Ocurrio un error al registrar el condominio.");
            return "auth/profile";
        }

        Usuario usuarioDb = usuarioService.findByUsername(auth.getName());

        usuarioDb.merge(usuario);
        usuarioDb.setPassword(encoder.encode(usuarioDb.getPassword()));
        usuarioService.save(usuarioDb);

        flash.addFlashAttribute("alert_success", "Lo información de su cuenta se ha actualizado." );
        return "redirect:/perfil";
    }
}
