package mx.admino.models;

import java.io.Serializable;
import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection ="cuotas")
public class Cuota implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Cuota() {
		this.status = CuotaStatus.REGISTRADO;
	}

	@Id
	private String id;
	
	@NotNull
	@NotEmpty
	@NotBlank
	private String nombre;
	
	@NotNull
	private Float importe;
	
	@NotNull
	@NotEmpty
	@NotBlank
	private String descripcion;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaInicio;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaFin;

	@NotNull
	private CuotaStatus status;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Float getImporte() {
		return importe;
	}

	public void setImporte(Float importe) {
		this.importe = importe;
	}

	public CuotaStatus getStatus() {
		return status;
	}

	public void setStatus(CuotaStatus status) {
		this.status = status;
	}

	public Cuota merge(@Valid Cuota cuota) {
		
		this.nombre = cuota.getNombre();
		this.descripcion = cuota.getDescripcion();
		this.fechaInicio = cuota.getFechaInicio();
		this.fechaFin = cuota.getFechaFin();
		this.importe = cuota.getImporte();
		this.status = cuota.getStatus();
		
		return this;
	}	
}
