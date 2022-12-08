package mx.admino.controllers.facturas;

import io.jsonwebtoken.impl.crypto.MacProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FacturaPdfControllerTest {

    @Test
//    @EnabledIfSystemProperty(named = "ENV", matches = "dev")
    void getFactura() {

        Map<String, String> getEnv = System.getenv();
        getEnv.forEach((k,v)->System.out.println(k+ " " + v));
    }
}