package mx.admino.controllers.expedientes;

import mx.admino.models.Archivo;
import mx.admino.services.CasasService;
import mx.admino.services.CondominioService;
import mx.admino.services.ExpedientesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.UUID;

@Controller
public class ExpedienteNuevoController {

    @Autowired
    private CondominioService condominioService;

    @Autowired
    private CasasService casaService;

    @Autowired
    private ExpedientesService expedientesService;

    @Value("${app.path.upload}")
    private String path;

    @PostMapping("/condominios/{cid}/casas/{uid}/archivos/nuevo")
    public String postNuevo(
            @PathVariable String cid,
            @PathVariable String uid,
            @Valid Archivo narchivo,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            @RequestParam MultipartFile documento,
            Model model
            ) {

        var condominio = condominioService.findById(cid);
        var casa = casaService.findById(uid);
        var newFilename = UUID.randomUUID().toString();

        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().stream().forEach(e -> System.out.println(e.getDefaultMessage()));
            redirectAttributes.addFlashAttribute("alert_danger", "Error la información que proporcionaste es incorrecta. Verificala y vuelve a intentar");
            return String.format("redirect:/condominios/%s/casas/%s/archivos", cid, uid);
        }

        try {
            byte barr[]=documento.getBytes();

            BufferedOutputStream bout=new BufferedOutputStream(
                    new FileOutputStream(path + newFilename));
            bout.write(barr);
            bout.flush();
            bout.close();

        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("alert_danger", "Ocurrio un error al guardar el archivo. Por favor intenta mas tarde");
            return String.format("redirect:/condominios/%s/casas/%s/archivos", cid, uid);
        }
        narchivo.setNombreOriginal(documento.getOriginalFilename());
        narchivo.setContentType(documento.getContentType());
        narchivo.setCasa(casa);
        narchivo.setCondominio(condominio);
        narchivo.setRuta(newFilename);
        Archivo nuevoArchivo = expedientesService.insert(narchivo);
        redirectAttributes.addFlashAttribute("alert_success", "Archivo guardado en el expediente de la casa");
        return String.format("redirect:/condominios/%s/casas/%s/archivos", cid, uid);
    }
}
