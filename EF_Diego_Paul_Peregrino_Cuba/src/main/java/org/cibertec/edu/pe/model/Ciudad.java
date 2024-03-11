package org.cibertec.edu.pe.model;

import javax.persistence.*;


@Entity
@Table(name = "tb_ciudad")
public class Ciudad {

    @Id
    private String codigoPostal;

    private String nombre;

    // Constructor, getters y setters

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


}