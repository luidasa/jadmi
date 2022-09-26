package mx.admino.factories;

import mx.admino.models.Breadcrum;
import mx.admino.models.entities.Cargo;
import mx.admino.models.entities.Casa;
import mx.admino.models.entities.Condominio;

import java.util.ArrayList;
import java.util.List;

public class BreadcrumFactory {

    public static List<Breadcrum> getBreadcrum(Condominio condominio) {

        List<Breadcrum> x = new ArrayList<Breadcrum>();
        x.add(new Breadcrum("Inicio", "/panel", false));
        x.add(new Breadcrum("Condominios", "/condominios", condominio == null));
        if (condominio != null)  {
            if (condominio.getId() != null) {
                x.add(new Breadcrum(condominio.getNombre(), "/condominios/" + condominio.getId(), true));
            } else {
                x.add(new Breadcrum("Nuevo", "/condominios/nuevo", true));
            }
        }
        return x ;
    }

    public static List<Breadcrum> getBreadcrum(Condominio condominio, Casa casa) {

        List<Breadcrum> x = new ArrayList<Breadcrum>();
        x.add(new Breadcrum("Inicio", "/panel", false));
        x.add(new Breadcrum("Condominios", "/condominios", condominio == null));
        x.add(new Breadcrum(condominio.getNombre(), "/condominios/" + condominio.getId(), false));
        x.add(new Breadcrum("Casas", "/condominios/" + condominio.getId() +"/casas", casa == null));
        if (casa != null) {
            if (casa.getId() != null) {
                x.add(new Breadcrum(casa.getInterior(), "/condominios/" + condominio.getId() + "/casas/" + casa.getId(), true));
            } else {
                x.add(new Breadcrum("Nueva", "/condominios/" + condominio.getId() + "/casas/nuevo", true));
            }
        }
        return x ;
    }

}
