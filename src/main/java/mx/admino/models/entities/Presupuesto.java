package mx.admino.models.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "presupuestos")
public class Presupuesto {

    public Presupuesto() {
        this.conceptos = new ArrayList<>();
        this.pagos = new ArrayList<>();
        this.gastos = new ArrayList<>();

        Arrays.stream(Concepto.Tipos.values()).sequential().forEach(item -> {
            this.conceptos.add(new Concepto(item));
        });
    }

    public Presupuesto(Condominio condominio) {
        this();
        this.condominio = condominio;
    }

    private String id;

    private String mes;


    private BigDecimal ingresos;

    private BigDecimal egresos;

    private BigDecimal ingresosReales;

    private BigDecimal egresosReales;

    private List<Concepto> conceptos;

    @DBRef
    private List<Pago> pagos;

    @DBRef
    private List<Egreso> gastos;

    @DBRef
    private Condominio condominio;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
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

    public List<Egreso> getGastos() {
        return gastos;
    }

    public void setGastos(List<Egreso> gastos) {
        this.gastos = gastos;
    }

    public List<Concepto> getConceptos() {
        return conceptos;
    }

    public void setConceptos(List<Concepto> conceptos) {
        this.conceptos = conceptos;
    }

    public Condominio getCondominio() {
        return condominio;
    }

    public void setCondominio(Condominio condominio) {
        this.condominio = condominio;
    }
}
