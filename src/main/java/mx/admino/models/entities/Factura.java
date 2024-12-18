package mx.admino.models.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="facturas")
public class Factura implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Factura( ) {
		this.cargos = new ArrayList<>();
		this.pagos = new ArrayList<>();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Casa casa;

	private Condominio condominio;
	
	@NotNull
	@Valid
	private CorteFactura corte;
	
	private List<Cargo> cargos;
	
	private List<Pago> pagos;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaCorte;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaVencimiento;
	
	private Factura facturaAnteriorFactura;
	
	private Float SaldoAnterior;
	
	private Float importeCargos;
	
	private Float importePagos;
	
	private Float saldo;
	
	public Date getFechaCorte() {
		return fechaCorte;
	}

	public void setFechaCorte(Date fechaCorte) {
		this.fechaCorte = fechaCorte;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Cargo> getCargos() {
		return cargos;
	}

	public void setCargos(List<Cargo> cargos) {
		this.cargos = cargos;
		this.importeCargos = 0.0f;
		cargos.stream().forEach(c-> {
			this.importeCargos += c.getImporte();
		});
	}

	public List<Pago> getPagos() {
		return pagos;
	}

	public void setPagos(List<Pago> pagos) {
		this.pagos = pagos;
		this.importePagos = 0.0f;
		this.pagos.stream().forEach(p -> {
			this.importePagos += p.getImporte();
		});
	}

	public Casa getCasa() {
		return casa;
	}

	public void setCasa(Casa casa) {
		this.casa = casa;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public Factura getFacturaAnteriorFactura() {
		return facturaAnteriorFactura;
	}

	public void setFacturaAnteriorFactura(Factura facturaAnteriorFactura) {
		this.facturaAnteriorFactura = facturaAnteriorFactura;
	}

	public Float getSaldoAnterior() {
		return SaldoAnterior;
	}

	public void setSaldoAnterior(Float saldoAnterior) {
		SaldoAnterior = saldoAnterior;
	}

	public Float getImporteCargos() {
		return importeCargos;
	}

	public void setImporteCargos(Float importeCargos) {
		this.importeCargos = importeCargos;
	}

	public Float getImportePagos() {
		return importePagos;
	}

	public void setImportePagos(Float importePagos) {
		this.importePagos = importePagos;
	}

	public Float getSaldo() {
		return this.saldo;
	}

	public void setSaldo(Float saldo) {
		this.saldo = saldo;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	
	public List<MovimientoFactura> getMovimientos() {
		List<MovimientoFactura> movimientos = new ArrayList<>();
		
		movimientos = this.pagos.stream().map(p -> {
			MovimientoFactura m = new MovimientoFactura(
					p.getFechaPagado(),
					"Pago", 
					-1 * p.getImporte());
			return m;
		}).collect(Collectors.toList());
		movimientos.addAll(this.cargos.stream().map(c -> {
			MovimientoFactura m = new MovimientoFactura(
					c.getFechaVencimiento(),
					c.getConcepto().toString(),
					c.getImporte());
			return m;
		}).collect(Collectors.toList()));
		return movimientos;
	}

	@Override
	public String toString() {
		return "Factura [condomino=" + casa +
				", cargos=" + cargos + 
				", pagos=" + pagos + 
				", fechaCorte=" + fechaCorte + 
				", fechaVencimiento=" + fechaVencimiento + 
				", facturaAnteriorFactura=" + facturaAnteriorFactura + 
				", SaldoAnterior=" + SaldoAnterior + 
				", importeCargos=" + importeCargos + 
				", importePagos=" + importePagos + 
				", Saldo=" + saldo + "]";
	}

	public CorteFactura getCorte() {
		return corte;
	}

	public void setCorte(CorteFactura corte) {
		this.corte = corte;
	}
}
