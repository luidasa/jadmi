package mx.admino.models.entities;

import java.math.BigDecimal;

public class Concepto {

    private Tipos tipo;

    private BigDecimal gastosFijos;

    private BigDecimal gastosVariables;

    private BigDecimal gastosPeriodicos;

    public Concepto(Tipos item) {

        this.tipo = item;
        this.gastosFijos = new BigDecimal("0.0");
        this.gastosPeriodicos = new BigDecimal("0.0");
        this.gastosVariables = new BigDecimal("0.0");
    }

    public Tipos getTipo() {
        return tipo;
    }

    public void setTipo(Tipos tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getGastosFijos() {
        return gastosFijos;
    }

    public void setGastosFijos(BigDecimal gastosFijos) {
        this.gastosFijos = gastosFijos;
    }

    public BigDecimal getGastosVariables() {
        return gastosVariables;
    }

    public void setGastosVariables(BigDecimal gastosVariables) {
        this.gastosVariables = gastosVariables;
    }

    public BigDecimal getGastosPeriodicos() {
        return gastosPeriodicos;
    }

    public void setGastosPeriodicos(BigDecimal gastosPeriodicos) {
        this.gastosPeriodicos = gastosPeriodicos;
    }

    public enum Tipos {

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
