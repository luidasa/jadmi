package mx.admino.controllers.facturas;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.admino.models.entities.CorteFactura;
import mx.admino.services.CondominioService;
import mx.admino.services.CasasService;
import mx.admino.services.FacturaService;

@Controller
@RequestMapping("/admin/facturas")
public class CreateFacturasController {

	@Autowired
	FacturaService facturaService;
	
	@Autowired
    CasasService casasService;
	
	@Autowired
	CondominioService condominioService;
	
	
}
