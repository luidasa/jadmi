package mx.admino.controllers.facturas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import mx.admino.services.CasasService;
import mx.admino.services.CondominioService;
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
