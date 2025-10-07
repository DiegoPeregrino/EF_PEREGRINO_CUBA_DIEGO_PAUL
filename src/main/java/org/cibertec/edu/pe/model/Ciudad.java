package org.cibertec.edu.pe.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "ciudades")
public class Ciudad {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank(message = "El código postal es obligatorio")
    @Size(max = 10, message = "El código postal no puede exceder 10 caracteres")
    @Column(name = "codigo_postal", unique = true)
    private String codigoPostal;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "ciudadOrigen", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetalleVenta> detallesVentaOrigen;

    @OneToMany(mappedBy = "ciudadDestino", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetalleVenta> detallesVentaDestino;

    // Constructores
    public Ciudad() {}

    public Ciudad(String codigoPostal, String nombre) {
        this.codigoPostal = codigoPostal;
        this.nombre = nombre;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<DetalleVenta> getDetallesVentaOrigen() {
        return detallesVentaOrigen;
    }

    public void setDetallesVentaOrigen(List<DetalleVenta> detallesVentaOrigen) {
        this.detallesVentaOrigen = detallesVentaOrigen;
    }

    public List<DetalleVenta> getDetallesVentaDestino() {
        return detallesVentaDestino;
    }

    public void setDetallesVentaDestino(List<DetalleVenta> detallesVentaDestino) {
        this.detallesVentaDestino = detallesVentaDestino;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Ciudad ciudad = (Ciudad) obj;
        return Objects.equals(id, ciudad.id) &&
               Objects.equals(codigoPostal, ciudad.codigoPostal) &&
               Objects.equals(nombre, ciudad.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigoPostal, nombre);
    }

    @Override
    public String toString() {
        return "Ciudad{" +
               "id=" + id +
               ", codigoPostal='" + codigoPostal + '\'' +
               ", nombre='" + nombre + '\'' +
               '}';
    }
}