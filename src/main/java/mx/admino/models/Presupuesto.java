package mx.admino.models;

import mx.admino.models.entities.Condominio;
import mx.admino.models.entities.Pago;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "presupuestos")
public class Presupuesto {

    public Presupuesto() {
        this.pagos = new ArrayList<>();
        this.gastos = new ArrayList<>();
    }

    @Id
    private String id;

    @NotNull
    private Date fechaInicio;

    @NotNull
    private Date fechaFin;

    @NotNull
    private BigDecimal ingresos;

    @NotNull
    private BigDecimal egresos;

    private BigDecimal ingresosReales;

    private BigDecimal egresosReales;

    private List<Pago> pagos;

    private List<Gasto> gastos;

    @DBRef
    private Condominio condominio;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public BigDecimal getIngresos() {
        return ingresos;
    }

    public void setIngresos(BigDecimal ingresos) {
        this.ingresos = ingresos;
    }

    public BigDecimal getEgresos() {
        return egresos;
    }

    public void setEgresos(BigDecimal egresos) {
        this.egresos = egresos;
    }

    public BigDecimal getIngresosReales() {
        return ingresosReales;
    }

    public void setIngresosReales(BigDecimal ingresosReales) {
        this.ingresosReales = ingresosReales;
    }

    public BigDecimal getEgresosReales() {
        return egresosReales;
    }

    public void setEgresosReales(BigDecimal egresosReales) {
        this.egresosReales = egresosReales;
    }

    public List<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }

    public List<Gasto> getGastos() {
        return gastos;
    }

    public void setGastos(List<Gasto> gastos) {
        this.gastos = gastos;
    }

    public Condominio getCondominio() {
        return condominio;
    }

    public void setCondominio(Condominio condominio) {
        this.condominio = condominio;
    }
}
