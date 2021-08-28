package mx.admino.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="condominos")
public class Condomino implements Serializable {

	private static final long serialVersionUID = 1L;

	public Condomino() {
		this.pagos = new ArrayList<>();
		this.cargos = new ArrayList<>();
		this.facturas = new ArrayList<>();
	}
	
	public Condomino(String interior, String nombre) {
		this();

		this.interior = interior;
		this.nombre = nombre;
	}

	@Id
	private String id;
	
	private String username;
	
	private String interior;
	
	private String nombre;
	
	private String telefono;
	
	private String correo;
	
	private List<Pago> pagos;
	
	private List<Cargo> cargos;
	
	private List<Factura> facturas;	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getInterior() {
		return interior;
	}

	public void setInterior(String interior) {
		this.interior = interior;
	}

	public List<Pago> getPagos() {
		return pagos;
	}

	public void setPagos(List<Pago> pagos) {
		this.pagos = pagos;
	}

	public List<Cargo> getCargos() {
		return cargos;
	}

	public void setCargos(List<Cargo> cargos) {
		this.cargos = cargos;
	}

	public List<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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
}
