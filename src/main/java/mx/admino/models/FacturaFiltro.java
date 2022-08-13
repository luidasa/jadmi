package mx.admino.models;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import mx.admino.models.entities.Condomino;

public class FacturaFiltro {
	
	public FacturaFiltro() {
		LocalDate today = LocalDate.now().atStartOfDay().toLocalDate();
		LocalDate start = today.minusDays(today.getDayOfMonth());
		//LocalDate end = start.plusMonths(1);

		this.setFechaCorte(convertToDate(start));
		//this.setFechaMinimo(convertToDate(start));
		//this.setFechaMaxima(convertToDate(end));
	}

	private Condomino condomino;
	
	private Float saldoMinimo;
	
	private Float saldoMaximo;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaCorte;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaMinimo;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
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
	
	public Date getFechaCorte() {
		return fechaCorte;
	}

	public void setFechaCorte(Date fechaCorte) {
		this.fechaCorte = fechaCorte;
	}

	public Date convertToDate(LocalDate dateToConvert) {
	    return Date.from(dateToConvert.atStartOfDay()
	      .atZone(ZoneId.systemDefault())
	      .toInstant());
	}

	@Override
	public String toString() {
		return "FacturaFiltro [" + 
				"condomino=" + condomino + 
				", saldoMinimo=" + saldoMinimo + 
				", saldoMaximo=" + saldoMaximo + 
				", fechaMinimo=" + fechaMinimo + 
				", fechaMaxima=" + fechaMaxima + "]";
	}
	
	
}
