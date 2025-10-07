package org.cibertec.edu.pe.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "detalle_venta")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ciudad_origen_id")
    private Ciudad ciudadOrigen; // Cambiado de Venta a Ciudad

    @ManyToOne
    @JoinColumn(name = "ciudad_destino_id")
    private Ciudad ciudadDestino; // Cambiado de Venta a Ciudad

    @Column(name = "fecha_viaje")
    @Temporal(TemporalType.DATE)
    private Date fechaViaje;

    @Column(name = "fecha_retorno")
    @Temporal(TemporalType.DATE)
    private Date fechaRetorno;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "sub_total")
    private Double subTotal;

    @Column(name = "nombre_comprador")
    private String nombreComprador;

    @ManyToOne
    @JoinColumn(name = "venta_id")
    private Venta venta;

    // Constructores
    public DetalleVenta() {}

    public DetalleVenta(Ciudad ciudadOrigen, Ciudad ciudadDestino, Date fechaViaje,
                       Date fechaRetorno, Integer cantidad, Double subTotal, String nombreComprador) {
        this.ciudadOrigen = ciudadOrigen;
        this.ciudadDestino = ciudadDestino;
        this.fechaViaje = fechaViaje;
        this.fechaRetorno = fechaRetorno;
        this.cantidad = cantidad;
        this.subTotal = subTotal;
        this.nombreComprador = nombreComprador;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Ciudad getCiudadOrigen() {
        return ciudadOrigen;
    }

    public void setCiudadOrigen(Ciudad ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;
    }

    public Ciudad getCiudadDestino() {
        return ciudadDestino;
    }

    public void setCiudadDestino(Ciudad ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
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

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public String getNombreComprador() {
        return nombreComprador;
    }

    public void setNombreComprador(String nombreComprador) {
        this.nombreComprador = nombreComprador;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }
}
