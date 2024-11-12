package mx.admino.models.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import mx.admino.models.PagoMetodo;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import mx.admino.models.PagoEstatus;

@Entity
@Table(name ="pagos")
public class Pago implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Pago() {
		this.estatus = PagoEstatus.PENDIENTE;
	}
	
	@Id
	private String id;

	private Condominio condominio;

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
	
	private Boolean conciliado;

	public Pago(Casa casa) {
		this();
		this.setConciliado(false);
		this.casa = casa;
		this.estatus = PagoEstatus.PENDIENTE;
	}

	public Pago(Condominio condominio) {
		this();
		this.estatus = PagoEstatus.PENDIENTE;
		this.condominio = condominio;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Casa getCasa() {
		return casa;
	}

	public void setCasa(Casa casa) {
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

	public PagoMetodo getMetodo() {
		return metodo;
	}

	public void setMetodo(PagoMetodo metodo) {
		this.metodo = metodo;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public Boolean getConciliado() {
		return conciliado;
	}

	public void setConciliado(Boolean conciliado) {
		this.conciliado = conciliado;
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

		this.setCasa(pago.getCasa());
		this.setFechaPagado(pago.getFechaPagado());
		this.setImporte(pago.getImporte());
		this.setEstatus(pago.getEstatus());
		this.setConciliado(pago.getConciliado());
		
		return this;
	}
}
