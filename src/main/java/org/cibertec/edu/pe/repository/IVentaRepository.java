package org.cibertec.edu.pe.repository;

import org.cibertec.edu.pe.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVentaRepository extends JpaRepository<Venta, Integer> {
    Venta findByIdVenta(int id);
    Venta findByNombreComprador(String nombreComprador);
}
