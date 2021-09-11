package mx.admino.models;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection ="defCargos")
public class DefCargo implements Serializable {

	private static final long serialVersionUID = 1L;

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
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date fechaInicio;
	
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date fechaFin;

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
}
