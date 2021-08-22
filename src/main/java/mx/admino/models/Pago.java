package mx.admino.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection ="pagos")
public class Pago implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	private Condomino condomino;
	
	private Float importe;
	
	private LocalDateTime fechaPagado;
	
	private LocalDateTime fechaRegistrado;

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

	public LocalDateTime getFechaPagado() {
		return fechaPagado;
	}

	public void setFechaPagado(LocalDateTime fechaPagado) {
		this.fechaPagado = fechaPagado;
	}

	public LocalDateTime getFechaRegistrado() {
		return fechaRegistrado;
	}

	public void setFechaRegistrado(LocalDateTime fechaRegistrado) {
		this.fechaRegistrado = fechaRegistrado;
	}
}
