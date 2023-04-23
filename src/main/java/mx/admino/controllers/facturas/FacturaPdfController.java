package mx.admino.controllers.facturas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.html.HtmlWriter;

@Controller
public class FacturaPdfController {
	
	@GetMapping("/condominios/{uid}/casas/{cid}/facturas/{fid}/pdf")
	public ResponseEntity<?> getFactura(
			@PathVariable String uid,
			@PathVariable String cid,
			@PathVariable String fid,
			Model model) throws IOException {
		
		 Document document = new Document(PageSize.A4, 50, 50, 50, 50);
		 String filename = "./output.pdf";
        try {
            // step 2:
            // we create a writer that listens to the document
            // and directs a HTML-stream to a file
            HtmlWriter.getInstance(document,
                    new FileOutputStream(filename));

            // step 3: we open the document
            document.open();
            // step 4: we add a paragraph to the document
            document.add(new Paragraph("Hello World"));
        } catch (DocumentException | IOException de) {
            System.err.println(de.getMessage());
        }
        
        
        // step 5: we close the document
        document.close();
        
        FileInputStream fis= new FileInputStream(new File(filename));
        byte[] content = new byte[fis.available()];
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        // Here you have to set the actual filename of your pdf
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(content, headers, HttpStatus.OK);
        return response;	
        }

}
