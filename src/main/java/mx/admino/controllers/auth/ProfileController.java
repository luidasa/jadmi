package mx.admino.controllers.auth;

import mx.admino.models.entities.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    public String getProfile() {
        return "auth/profile";
    }

    @PostMapping("/profile")
    public String postProfile(
            @Valid Usuario usuario,
            BindingResult binding,
            RedirectAttributes flash,
            Model model) {

        return "redirect:/profile";
    }
}
