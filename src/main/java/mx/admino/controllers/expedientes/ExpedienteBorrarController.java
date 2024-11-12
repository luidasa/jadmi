package mx.admino.controllers.expedientes;

import mx.admino.services.ExpedientesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
public class ExpedienteBorrarController {

    private static final Logger logger = LoggerFactory.getLogger(ExpedienteBorrarController.class);

    @Autowired
    private ExpedientesService expedienteService;

    @Value("${app.path.upload}")
    private String path;

    @GetMapping("/condominios/{cid}/casas/{uid}/archivos/{aid}/borrar")
    public String borrarArchivo(
            @PathVariable Long cid,
            @PathVariable Long uid,
            @PathVariable Long aid,
            RedirectAttributes redirectAttributes,
            Model model) {

        var archivo = expedienteService.findById(aid);
        try {
            Files.delete(Path.of(path + archivo.getRuta()));
            expedienteService.deleteById(aid);
            redirectAttributes.addFlashAttribute("alert_success", "El archivo se borro del sistema y no se puede recuperar.");
        } catch (IOException e) {
            logger.error("Ocurrio un error al borrar el archivo.", e);
            redirectAttributes.addFlashAttribute("alert_danger", "El archivo no se borro del sistema. Intente mas tarde");
        }

        return "redirect:/condominios/" + cid + "/casas/" + uid + "/archivos";
    }
}
