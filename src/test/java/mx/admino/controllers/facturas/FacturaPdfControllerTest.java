package mx.admino.controllers.facturas;

import mx.admino.services.FacturaService;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FacturaPdfControllerTest {


    @Test
    void getFactura() {

        Map<String, String> getEnv = System.getenv();
        getEnv.forEach((k,v)->System.out.println(k+ " " + v));
    }
}