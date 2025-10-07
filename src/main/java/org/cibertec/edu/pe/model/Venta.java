package org.cibertec.edu.pe.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fecha_venta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVenta;

    @Column(name = "total")
    private Double total;

    @Column(name = "nombre_comprador")
    private String nombreComprador;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetalleVenta> detalles;

    // Constructores
    public Venta() {}

    public Venta(Date fechaVenta, Double total, String nombreComprador) {
        this.fechaVenta = fechaVenta;
        this.total = total;
        this.nombreComprador = nombreComprador;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getNombreComprador() {
        return nombreComprador;
    }

    public void setNombreComprador(String nombreComprador) {
        this.nombreComprador = nombreComprador;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }
}


