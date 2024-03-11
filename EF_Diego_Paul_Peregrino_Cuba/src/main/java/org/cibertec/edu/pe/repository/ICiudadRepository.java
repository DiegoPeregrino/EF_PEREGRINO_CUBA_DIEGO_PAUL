package org.cibertec.edu.pe.repository;

import org.cibertec.edu.pe.model.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICiudadRepository extends JpaRepository<Ciudad, String> {
	    Ciudad findByCodigoPostal(String codigoPostal);
	}
