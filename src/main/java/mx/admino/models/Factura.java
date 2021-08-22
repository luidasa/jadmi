package mx.admino.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="facturas")
public class Factura implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Factura( ) {
		this.cargos = new ArrayList<>();
		this.pagos = new ArrayList<>();
	}

	@Id
	private String id;
	
	private List<Cargo> cargos;
	
	private List<Pago> pagos;
	
	private LocalDateTime fechaCorte;
	
	private LocalDateTime fechaVencimento;
	
	public LocalDateTime getFechaCorte() {
		return fechaCorte;
	}

	public void setFechaCorte(LocalDateTime fechaCorte) {
		this.fechaCorte = fechaCorte;
	}

	public LocalDateTime getFechaVencimento() {
		return fechaVencimento;
	}

	public void setFechaVencimento(LocalDateTime fechaVencimento) {
		this.fechaVencimento = fechaVencimento;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Cargo> getCargos() {
		return cargos;
	}

	public void setCargos(List<Cargo> cargos) {
		this.cargos = cargos;
	}

	public List<Pago> getPagos() {
		return pagos;
	}

	public void setPagos(List<Pago> pagos) {
		this.pagos = pagos;
	}
}
