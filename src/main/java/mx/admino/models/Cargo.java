package mx.admino.models;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection="cargos")
public class Cargo implements Serializable {

	public Cargo() {
		this.status = CargoStatus.PENDIENTE;
	}
	
	public Cargo(@NotNull Condomino condomino, @NotNull Float importe, @NotEmpty @NotBlank String concepto,
			@NotNull Date fechaVencimiento) {
		this();
		this.condomino = condomino;
		this.importe = importe;
		this.concepto = concepto;
		this.fechaVencimiento = fechaVencimiento;
	}

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	@NotNull
	private Condomino condomino;
	
	@NotNull
	private Float importe;
	
	@NotEmpty
	@NotBlank
	private String concepto;
	
	@NotNull
	private CargoStatus status;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaVencimiento;

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

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public Condomino getCondomino() {
		return condomino;
	}

	public void setCondomino(Condomino condomino) {
		this.condomino = condomino;
	}

	public CargoStatus getStatus() {
		return status;
	}

	public void setStatus(CargoStatus status) {
		this.status = status;
	}
}
