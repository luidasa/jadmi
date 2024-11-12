package mx.admino.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity
@Table(name = "condominios")
public class Condominio implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(min = 5)
	private String nombre;

	@ManyToOne(cascade = CascadeType.ALL)
	private Usuario administrador;

	@NotBlank
	private String telefono;

	@NotBlank
	private String correo;

	@NotBlank
	private String domicilio;

	private Float saldo;
	
	@Min(value = 2)
	private Integer unidades;

	public Condominio() {
		this.unidades = 0;
	}

	public Condominio(String nombre) {

		this.nombre = nombre;
	}

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

	public Integer getUnidades() {

		return unidades;
	}

	public void setUnidades(Integer unidades) {

		this.unidades = unidades;
	}

	public Usuario getAdministrador() {

		return administrador;
	}

	public void setAdministrador(Usuario administrador) {

		this.administrador = administrador;
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

	@Override
	public String toString() {
		return "Condominio{" +
				"id='" + id + '\'' +
				", nombre='" + nombre + '\'' +
				", administrador=" + administrador.getName() +
				", telefono='" + telefono + '\'' +
				", correo='" + correo + '\'' +
				", domicilio='" + domicilio + '\'' +
				", saldo=" + saldo +
				", unidades=" + unidades +
				'}';
	}

	public Condominio merge(Condominio condominio) {

		this.nombre = condominio.getNombre();
		this.telefono = condominio.getTelefono();
		this.correo = condominio.getCorreo();
		this.domicilio = condominio.getDomicilio();

		return this;
	}

	public Condominio(String nombre, Usuario administrador, String telefono, String correo, String domicilio, Float saldo, Integer unidades) {
		this.nombre = nombre;
		this.administrador = administrador;
		this.telefono = telefono;
		this.correo = correo;
		this.domicilio = domicilio;
		this.saldo = saldo;
		this.unidades = unidades;
	}
}
