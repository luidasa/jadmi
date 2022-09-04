package mx.admino.events;

import mx.admino.models.entities.Usuario;
import org.springframework.context.ApplicationEvent;

public class OnUserRecoverEvent extends ApplicationEvent {

    private Usuario usuario;

    private String urlBase;

    public OnUserRecoverEvent(Usuario usuario, String url) {
        super(usuario);

        this.usuario = usuario;
        this.urlBase = url;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getUrlBase() {
        return urlBase;
    }

    public void setUrlBase(String urlBase) {
        this.urlBase = urlBase;
    }
}
