package org.cibertec.edu.pe.repository;

import org.cibertec.edu.pe.model.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICiudadRepository extends JpaRepository<Ciudad, Integer> {
    
    // Buscar por código postal
    Optional<Ciudad> findByCodigoPostal(String codigoPostal);
    
    // Buscar por nombre (ignorando mayúsculas/minúsculas)
    Optional<Ciudad> findByNombreIgnoreCase(String nombre);
    
    // Buscar ciudades que contengan cierto texto en el nombre
    List<Ciudad> findByNombreContainingIgnoreCase(String nombre);
    
    // Verificar si existe por código postal
    boolean existsByCodigoPostal(String codigoPostal);
    
    // Consulta personalizada para buscar por nombre o código postal
    @Query("SELECT c FROM Ciudad c WHERE c.nombre LIKE %:texto% OR c.codigoPostal LIKE %:texto%")
    List<Ciudad> buscarPorTexto(@Param("texto") String texto);
    
    // Obtener todas las ciudades ordenadas por nombre
    List<Ciudad> findAllByOrderByNombreAsc();
}
