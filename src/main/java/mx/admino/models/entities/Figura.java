package mx.admino.models.entities;

import org.springframework.data.annotation.Id;


import javax.validation.constraints.NotNull;

public class Figura {

    @Id
    private String id;

    private Usuario usuario;

    private Condominio condominio;

    @NotNull
    private Roles role;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Condominio getCondominio() {
        return condominio;
    }

    public void setCondominio(Condominio condominio) {
        this.condominio = condominio;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
}
