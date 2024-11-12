package mx.admino.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import mx.admino.models.EgresoEstatus;
import mx.admino.models.PagoMetodo;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "egresos")
public class Egreso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Condominio condominio;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaPagado;

    @NotNull
    private String receptor;

    @NotNull
    @Min(1)
    private Float importe;

    @NotNull
    private Concepto.Tipos concepto;

    @NotNull
    private PagoMetodo metodo;

    private EgresoEstatus estatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Condominio getCondominio() {
        return condominio;
    }

    public void setCondominio(Condominio condominio) {
        this.condominio = condominio;
    }

    public Date getFechaPagado() {
        return fechaPagado;
    }

    public void setFechaPagado(Date fechaPagado) {
        this.fechaPagado = fechaPagado;
    }

    public Float getImporte() {
        return importe;
    }

    public void setImporte(Float importe) {
        this.importe = importe;
    }

    public Concepto.Tipos getConcepto() {
        return concepto;
    }

    public void setConcepto(Concepto.Tipos concepto) {
        this.concepto = concepto;
    }

    public PagoMetodo getMetodo() {
        return metodo;
    }

    public void setMetodo(PagoMetodo metodo) {
        this.metodo = metodo;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public EgresoEstatus getEstatus() {
        return estatus;
    }

    public void setEstatus(EgresoEstatus estatus) {
        this.estatus = estatus;
    }

    public Egreso() {
        this.estatus = EgresoEstatus.CONCILIADO;
    }

    public Egreso(Condominio condominio) {
        this();
        this.condominio = condominio;
    }
}
