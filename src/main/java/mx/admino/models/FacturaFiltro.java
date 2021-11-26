package mx.admino.models;

import java.util.Date;

public class FacturaFiltro {

	private Condomino condomino;
	
	private Float saldoMinimo;
	
	private Float saldoMaximo;
	
	private Date fechaMinimo;
	
	private Date fechaMaxima;

	public Boolean hasCondomino() {
		return getCondomino() != null;
	}

	public Condomino getCondomino() {
		return condomino;
	}

	public void setCondomino(Condomino condomino) {
		this.condomino = condomino;
	}

	public Boolean hasSaldo() {
		return ((this.saldoMaximo != null) || (this.saldoMinimo != null));
	}

	public Float getSaldoMinimo() {
		return saldoMinimo;
	}

	public void setSaldoMinimo(Float saldoMinimo) {
		this.saldoMinimo = saldoMinimo;
	}

	public Float getSaldoMaximo() {
		return saldoMaximo;
	}

	public void setSaldoMaximo(Float saldoMaximo) {
		this.saldoMaximo = saldoMaximo;
	}

	public Boolean hasFecha() {
		return ((this.fechaMinimo != null) || (this.fechaMaxima != null));
	}
	
	public Date getFechaMinimo() {
		return fechaMinimo;
	}

	public void setFechaMinimo(Date fechaMinimo) {
		this.fechaMinimo = fechaMinimo;
	}

	public Date getFechaMaxima() {
		return fechaMaxima;
	}

	public void setFechaMaxima(Date fechaMaxima) {
		this.fechaMaxima = fechaMaxima;
	}
}
