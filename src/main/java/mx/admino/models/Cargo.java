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
		this.estatus = CargoEstatus.PENDIENTE;
	}
	
	public Cargo(@NotNull Condomino condomino, @NotNull Float importe, Float descuento, @NotEmpty @NotBlank String concepto,
			@NotNull Date fechaVencimiento) {
		this();
		this.condomino = condomino;
		this.importe = importe;
		this.descuento = descuento;
		this.concepto = concepto;
		this.fechaVencimiento = fechaVencimiento;
	}

	public Cargo(Condomino condomino, Cuota cuota, Date fechaVencimiento) {
		this(condomino,
			cuota.getImporte(),
			condomino.getEstaDesocupada() && !cuota.getEsCompletaSiVacia() ? cuota.getImporteDesocupado(): 0,
			cuota.getDescripcion(),
			fechaVencimiento);
	}

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	/**
	 * Condomino que debe de aplicar el cargo
	 */
	@NotNull
	private Condomino condomino;
	
	/**
	 * Importe del cargo cuando la casa esta ocupada
	 */
	@NotNull
	private Float importe;
	
	/**
	 * Indica el descuento que debe de aplicarse si esta desocupada
	 */
	private Float descuento;
	
	/**
	 * Concepto por el cual se esta aplicando el cargo.
	 */
	@NotEmpty
	@NotBlank
	private String concepto;
	
	/**
	 * Estatus que tiene el cargo
	 */
	@NotNull
	private CargoEstatus estatus;
	
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

	public CargoEstatus getEstatus() {
		return estatus;
	}

	public void setEstatus(CargoEstatus status) {
		this.estatus = status;
	}
	
	public Float getDescuento() {
		return descuento;
	}

	public void setDescuento(Float descuento) {
		this.descuento = descuento;
	}

	@Override
	public String toString() {
		return "Cargo [condomino=" + condomino.getInterior() + 
				", importe=" + importe + 
				", concepto=" + concepto + 
				", status=" + estatus + 
				", fechaVencimiento=" + fechaVencimiento + "]";
	}
	
}
