package mx.admino.models.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public class Token {

    public Token(Usuario usuario) {

        this.usuario = usuario;
        this.code = UUID.randomUUID().toString();
        this.fechaVencimiento = LocalDate.now().plusDays(3);
        this.vigente = true;
    }

    @Id
    private String id;

    @NotNull
    @DBRef
    private Usuario usuario;

    private String code;

    private LocalDate fechaVencimiento;

    private boolean vigente;

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

    public boolean isVigente() {
        return vigente;
    }

    public void setVigente(boolean vigente) {
        this.vigente = vigente;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
}
