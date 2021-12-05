package mx.admino.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection = "corte-facturas")
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
	private String id;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaCorte;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaVencimiento;

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
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

	private static final long serialVersionUID = 1L;
}
