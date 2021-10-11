package mx.admino.models;

import java.util.Date;

/**
 * @author luisd
 *
 */
public class MovimientoFactura implements Comparable<MovimientoFactura> {
	
	private Date fecha;
	
	private String descripcion;
	
	private float importe;

	private float saldo;
	
	public MovimientoFactura(Date fecha, String descripcion, Float importe) {
		this.fecha = fecha;
		this.descripcion = descripcion;
		this.importe = importe;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public float getImporte() {
		return importe;
	}

	public void setImporte(float importe) {
		this.importe = importe;
	}

	public float getSaldo() {
		return saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}

	@Override
	public int compareTo(MovimientoFactura o) {
		return this.fecha.compareTo(o.getFecha());
	}
}
