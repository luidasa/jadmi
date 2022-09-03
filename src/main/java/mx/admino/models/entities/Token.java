package mx.admino.models.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public class Token {

    public Token(Usuario usuario) {

        this.usuario = usuario;
        this.code = UUID.randomUUID().toString();
    }

    @Id
    private String id;

    @NotNull
    @DBRef
    private Usuario usuario;

    private String code;

    private LocalDate fechaVencimiento;

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

    public String getCode() {
        return code;
    }

}
