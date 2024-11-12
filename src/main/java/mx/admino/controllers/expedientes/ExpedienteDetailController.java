package mx.admino.controllers.expedientes;

import mx.admino.services.ExpedientesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ExpedienteDetailController {

    private static final Logger logger = LoggerFactory.getLogger(ExpedienteDetailController.class);

    @Autowired
    private ExpedientesService archivoService;

    @Value("${app.path.upload}")
    private String path;

    @GetMapping("/condominios/{cid}/casas/{uid}/archivos/{aid}")
    public void getArchivo(
        @PathVariable Long cid,
        @PathVariable Long uid,
        @PathVariable Long aid,
        HttpServletResponse response
    ) {
        var archivo = archivoService.findById(aid);

        try {
            // get your file as InputStream
            InputStream is = new FileInputStream(path + archivo.getRuta());
            System.out.println(path + archivo.getRuta() + " Nombre original: " + archivo.getNombreOriginal());
            // copy it to response's OutputStream
            response.setContentType(archivo.getContentType());
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + archivo.getNombreOriginal() + "\"");
            response.flushBuffer();
        } catch (IOException ex) {
            logger.info("Error writing file to output stream. Filename was '{}'", archivo.getNombreOriginal(), ex);
            throw new RuntimeException("IOError writing file to output stream");
        }

    }
}
