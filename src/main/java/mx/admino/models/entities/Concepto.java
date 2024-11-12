package mx.admino.models.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "conceptos_presupuesto")
public class Concepto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
