package mx.admino.models.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "corte-facturas")
public class CorteFactura implements Serializable {

	public CorteFactura() {

		this.fechaCorte = Date.from(
				LocalDate.now().minusDays(LocalDate.now().getDayOfMonth()).atStartOfDay()
			      .atZone(ZoneId.systemDefault())
			      .toInstant());
		this.fechaVencimiento = Date.from(
				LocalDate.now().minusDays(LocalDate.now().getDayOfMonth()).atStartOfDay().plusDays(10)
			      .atZone(ZoneId.systemDefault())
			      .toInstant());
	}
		
	@Id
	private Long id;

	private Condominio condominio;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaCorte;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaVencimiento;

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	private static final long serialVersionUID = 1L;
}
