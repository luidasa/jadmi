package mx.admino.models;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class FacturaFormulario {

	private Condomino condomino;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaInicioCorte;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaCorte;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaVencimiento;

	public Condomino getCondomino() {
		return condomino;
	}

	public void setCondomino(Condomino condomino) {
		this.condomino = condomino;
	}

	public Date getFechaCorte() {
		return fechaCorte;
	}

	public void setFechaCorte(Date fechaCorte) {
		this.fechaCorte = fechaCorte;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public Date getFechaInicioCorte() {
		return fechaInicioCorte;
	}

	public void setFechaInicioCorte(Date fechaInicioCorte) {
		this.fechaInicioCorte = fechaInicioCorte;
	}
}
