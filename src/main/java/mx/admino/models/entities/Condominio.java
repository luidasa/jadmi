package mx.admino.models.entities;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "condominio")
public class Condominio implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	@NotNull
	@NotEmpty
	@NotBlank
	@Size(min = 5)
	@Indexed(unique = true)
	private String nombre;
	
	@Transient
	@NotNull
	@NotEmpty
	@NotBlank
	private String administrador;

	@Transient
	@NotNull
	@NotEmpty
	@NotBlank
	@Size(min = 10, max = 10)
	private String telefono;	
	
	@Transient
	@NotNull
	@Email
	private String correo;
	
	@NotNull
	@NotEmpty
	@NotBlank
	private String domicilio;
	
	private Float saldo;
	
	@Min(value = 2)
	private Integer unidades;
	
	@Transient
	private String password;
	
	@Transient
	private String confirmacion;

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

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public Float getSaldo() {
		return saldo;
	}

	public void setSaldo(Float saldo) {
		this.saldo = saldo;
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

	public Integer getUnidades() {
		return unidades;
	}

	public void setUnidades(Integer unidades) {
		this.unidades = unidades;
	}
	
}
