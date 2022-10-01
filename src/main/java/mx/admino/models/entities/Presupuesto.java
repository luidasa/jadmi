package mx.admino.models.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document(collection = "presupuestos")
public class Presupuesto {

    private String id;

    private String mes;

    private Conceptos concepto;

    private BigDecimal ingresos;

    private BigDecimal egresos;

    private BigDecimal ingresosReales;

    private BigDecimal egresosReales;

    private List<Pago> pagos;

    private List<Gasto> gastos;

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

    public Conceptos getConcepto() {
        return concepto;
    }

    public void setConcepto(Conceptos conceptos) {
        this.concepto = conceptos;
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

    public enum Conceptos {

        CUENTA,
        CONTADOR,
        IMPUESTOS,
        AGUA,
        MANTENIMIENTO,
        ALUMBRADO,
        SEGURIDAD,
        LIMPIEZA,
        FONDO,
        JARDINERIA;
    }
}
