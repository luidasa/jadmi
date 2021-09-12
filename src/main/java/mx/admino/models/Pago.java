package mx.admino.models;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection ="pagos")
public class Pago implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Pago() {
		this.estatus = PagoEstatus.PENDIENTE;
	}
	
	@Id
	private String id;
	
	private Condomino condomino;
	
	@NotNull
	@DecimalMin("0.01") 
	private Float importe;
	
	@NotNull
	private PagoEstatus estatus;

	@NotNull
	@Past
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaPagado;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Condomino getCondomino() {
		return condomino;
	}

	public void setCondomino(Condomino condomino) {
		this.condomino = condomino;
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
}
