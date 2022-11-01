package mx.admino.models.entities;

import java.io.Serializable;
import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import mx.admino.models.PagoMetodo;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import mx.admino.models.PagoEstatus;

@Document(collection ="pagos")
public class Pago implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Pago() {
		this.estatus = PagoEstatus.PENDIENTE;
	}
	
	@Id
	private String id;
	
	private Casa casa;

	private PagoMetodo metodo;
	
	@NotNull
	@DecimalMin("0.01") 
	private Float importe;
	
	@NotNull
	private PagoEstatus estatus;

	@NotNull
	@Past
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaPagado;

	public Pago(Casa casa) {
		this.casa = casa;
		this.estatus = PagoEstatus.PENDIENTE;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Casa getCondomino() {
		return casa;
	}

	public void setCondomino(Casa casa) {
		this.casa = casa;
	}

	public Float getImporte() {
		return importe;
	}

	public void setImporte(Float importe) {
		this.importe = importe;
	}

	public Date getFechaPagado() {
		return fechaPagado;
	}

	public void setFechaPagado(Date fechaPagado) {
		this.fechaPagado = fechaPagado;
	}

	public PagoEstatus getEstatus() {
		return estatus;
	}

	public void setEstatus(PagoEstatus estatus) {
		this.estatus = estatus;
	}

	public Casa getCasa() {
		return casa;
	}

	public void setCasa(Casa casa) {
		this.casa = casa;
	}

	public PagoMetodo getMetodo() {
		return metodo;
	}

	public void setMetodo(PagoMetodo metodo) {
		this.metodo = metodo;
	}

	@Override
	public String toString() {
		return "Pago [" +
				"id=" + id +
				", condomino=" + casa +
				", importe=" + importe + 
				", estatus=" + estatus + 
				", fechaPagado=" + fechaPagado + "]";
	}

	public Pago merge(@Valid Pago pago) {

		this.setCondomino(pago.getCondomino());
		this.setFechaPagado(pago.getFechaPagado());
		this.setImporte(pago.getImporte());
		this.setEstatus(pago.getEstatus());
		
		return this;
	}
}
