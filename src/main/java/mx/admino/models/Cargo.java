package mx.admino.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="cargos")
public class Cargo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	private Float importe;
	
	private String concepto;
	
	private LocalDateTime fechaVencimiento;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Float getImporte() {
		return importe;
	}

	public void setImporte(Float importe) {
		this.importe = importe;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public LocalDateTime getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(LocalDateTime fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
}
