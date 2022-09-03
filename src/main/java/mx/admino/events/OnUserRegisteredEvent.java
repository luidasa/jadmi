package mx.admino.events;

import mx.admino.models.entities.Usuario;
import org.springframework.context.ApplicationEvent;

public class OnUserRegisteredEvent extends ApplicationEvent {

    private Usuario usuario;

    private String urlBase;

    public OnUserRegisteredEvent(Usuario source, String url) {
        super(source);

        this.usuario = source;
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
