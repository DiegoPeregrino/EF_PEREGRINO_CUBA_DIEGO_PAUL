package org.cibertec.edu.pe.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "detalle_venta")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "venta_id")
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "ciudad_origen")
    private Venta ciudadOrigen;

    @ManyToOne
    @JoinColumn(name = "ciudad_destino")
    private Venta ciudadDestino;

    private int cantidad;

    @Column(name = "sub_total")
    private double subTotal;

    @Column(name = "fecha_viaje")
    private Date fechaViaje;

    @Column(name = "fecha_retorno")
    private Date fechaRetorno;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Venta getVenta() {
		return venta;
	}

	public void setVenta(Venta venta) {
		this.venta = venta;
	}

	public Venta getCiudadOrigen() {
		return ciudadOrigen;
	}

	public void setCiudadOrigen(Venta ciudadOrigen) {
		this.ciudadOrigen = ciudadOrigen;
	}

	public Venta getCiudadDestino() {
		return ciudadDestino;
	}

	public void setCiudadDestino(Venta ciudadDestino) {
		this.ciudadDestino = ciudadDestino;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

	public Date getFechaViaje() {
		return fechaViaje;
	}

	public void setFechaViaje(Date fechaViaje) {
		this.fechaViaje = fechaViaje;
	}

	public Date getFechaRetorno() {
		return fechaRetorno;
	}

	public void setFechaRetorno(Date fechaRetorno) {
		this.fechaRetorno = fechaRetorno;
	}

}
