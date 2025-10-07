package org.cibertec.edu.pe.repository;

import org.cibertec.edu.pe.model.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDetalleVentaRepository extends JpaRepository<DetalleVenta, Integer> {
    DetalleVenta findByIdDetalleVenta(int id);
    List<DetalleVenta> findByCiudadOrigen(String ciudadOrigen);
    List<DetalleVenta> findByCiudadDestino(String ciudadDestino);
    long count();

}
