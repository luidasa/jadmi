package mx.admino.models.entities;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "resumenes")
public class Resumen implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	
	private LocalDate fecha;
	
	private Integer numeroPagos;
	
	private Float importePagos;
	
	private Integer numeroCuotas;
	
	private Float importeCuotas;
	
	private Integer numeroPenalizaciones;
	
	private Float importePenalizaciones;
	
	private Integer numeroGastos;
	
	private Float importeGastos;

	private Float balanceInicial;
	
	private Float balanceFinal;
	
	private Float pagosVencidos;
	
	private Integer condominosAtrasados;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Integer getNumeroPagos() {
		return numeroPagos;
	}

	public void setNumeroPagos(Integer numeroPagos) {
		this.numeroPagos = numeroPagos;
	}

	public Float getImportePagos() {
		return importePagos;
	}

	public void setImportePagos(Float importePagos) {
		this.importePagos = importePagos;
	}

	public Integer getNumeroCuotas() {
		return numeroCuotas;
	}

	public void setNumeroCuotas(Integer numeroCuotas) {
		this.numeroCuotas = numeroCuotas;
	}

	public Float getImporteCuotas() {
		return importeCuotas;
	}

	public void setImporteCuotas(Float importeCuotas) {
		this.importeCuotas = importeCuotas;
	}

	public Integer getNumeroPenalizaciones() {
		return numeroPenalizaciones;
	}

	public void setNumeroPenalizaciones(Integer numeroPenalizaciones) {
		this.numeroPenalizaciones = numeroPenalizaciones;
	}

	public Float getImportePenalizaciones() {
		return importePenalizaciones;
	}

	public void setImportePenalizaciones(Float importePenalizaciones) {
		this.importePenalizaciones = importePenalizaciones;
	}

	public Integer getNumeroGastos() {
		return numeroGastos;
	}

	public void setNumeroGastos(Integer numeroGastos) {
		this.numeroGastos = numeroGastos;
	}

	public Float getImporteGastos() {
		return importeGastos;
	}

	public void setImporteGastos(Float importeGastos) {
		this.importeGastos = importeGastos;
	}

	public Float getBalanceInicial() {
		return balanceInicial;
	}

	public void setBalanceInicial(Float balanceInicial) {
		this.balanceInicial = balanceInicial;
	}

	public Float getBalanceFinal() {
		return balanceFinal;
	}

	public void setBalanceFinal(Float balanceFinal) {
		this.balanceFinal = balanceFinal;
	}

	public Float getPagosVencidos() {
		return pagosVencidos;
	}

	public void setPagosVencidos(Float pagosVencidos) {
		this.pagosVencidos = pagosVencidos;
	}

	public Integer getCondominosAtrasados() {
		return condominosAtrasados;
	}

	public void setCondominosAtrasados(Integer condominosAtrasados) {
		this.condominosAtrasados = condominosAtrasados;
	}
}
