package mx.admino.models.entities;

import java.io.Serializable;

import javax.validation.constraints.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="condominos")
public class Condomino implements Serializable {

	private static final long serialVersionUID = 1L;

	public Condomino() {
	}
	
	public Condomino(Condominio condominio, String interior) {
		this();

		this.condominio = condominio;
		this.interior = interior;
		this.estaDesocupada = false;
		this.estaRentada = false;
	}

	@Id
	private String id;

	@NotEmpty
	@NotBlank
	private String interior;

	@NotEmpty
	@NotBlank
	@Digits(fraction = 0, integer = 10)
	@DecimalMin(value = "10")
	private String telefono;

	@NotEmpty
	@Email
	private String correo;
		
	private Float saldo;
	
	private Boolean estaDesocupada;
	
	private Boolean estaRentada;

	@DBRef
	@NotNull
	private Condominio condominio;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInterior() {
		return interior;
	}

	public void setInterior(String interior) {
		this.interior = interior;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Float getSaldo() {
		return saldo;
	}

	public void setSaldo(Float saldo) {
		this.saldo = saldo;
	}

	public Boolean getEstaDesocupada() {
		return estaDesocupada;
	}

	public void setEstaDesocupada(Boolean estaDesocupada) {
		this.estaDesocupada = estaDesocupada;
	}

	public Boolean getEstaRentada() {
		return estaRentada;
	}

	public void setEstaRentada(Boolean estaRentada) {
		this.estaRentada = estaRentada;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public void merge(Condomino condomino) {
		
		this.setInterior(condomino.getInterior());
		this.setTelefono(condomino.getTelefono());
		this.setCorreo(condomino.getCorreo());
		this.setSaldo(condomino.getSaldo());
		this.setEstaDesocupada(condomino.getEstaDesocupada());
	}

	@Override
	public String toString() {
		return "Condomino [interior=" + interior +
				", telefono=" + telefono +
				", correo=" + correo + 
				", saldo=" + saldo + "]";
	}

}
