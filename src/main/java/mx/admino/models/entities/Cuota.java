package mx.admino.models.entities;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import mx.admino.models.CuotaEstatus;

@Entity
@Table(name = "cuotas")
public class Cuota implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Cuota() {
		this.estatus = CuotaEstatus.REGISTRADO;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@NotEmpty
	@NotBlank
	private String nombre;
	
	@NotNull
	private Float importe;

	private Float importeDesocupado;
	
	private Boolean esCompletaSiVacia;
	
	@NotNull
	@NotEmpty
	@NotBlank
	private String descripcion;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaInicio;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaFin;

	@NotNull
	private CuotaEstatus estatus;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public CuotaEstatus getEstatus() {
		return estatus;
	}

	public void setEstatus(CuotaEstatus status) {
		this.estatus = status;
	}

	public Cuota merge(@Valid Cuota cuota) {
		
		this.nombre = cuota.getNombre();
		this.descripcion = cuota.getDescripcion();
		this.fechaInicio = cuota.getFechaInicio();
		this.fechaFin = cuota.getFechaFin();
		this.importe = cuota.getImporte();
		this.estatus = cuota.getEstatus();
		this.esCompletaSiVacia = cuota.getEsCompletaSiVacia();
		this.importeDesocupado = cuota.getImporteDesocupado();
		
		return this;
	}

	public Boolean getEsCompletaSiVacia() {
		return esCompletaSiVacia;
	}

	public void setEsCompletaSiVacia(Boolean esCompletaSiVacia) {
		this.esCompletaSiVacia = esCompletaSiVacia;
	}

	public Float getImporteDesocupado() {
		return importeDesocupado;
	}

	public void setImporteDesocupado(Float importeDesocupado) {
		this.importeDesocupado = importeDesocupado;
	}

}
