package org.cibertec.edu.pe.service;

import org.cibertec.edu.pe.model.Ciudad;
import org.cibertec.edu.pe.repository.ICiudadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CiudadService {

    private final ICiudadRepository ciudadRepository;

    @Autowired
    public CiudadService(ICiudadRepository ciudadRepository) {
        this.ciudadRepository = ciudadRepository;
    }

    @Transactional(readOnly = true)
    public List<Ciudad> getAllCiudades() {
        return ciudadRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Ciudad> getCiudadById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID debe ser un número positivo");
        }
        return ciudadRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Ciudad getCiudadByIdOrThrow(Integer id) {
        return getCiudadById(id)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada con ID: " + id));
    }

    public Ciudad saveCiudad(Ciudad ciudad) {
        if (ciudad == null) {
            throw new IllegalArgumentException("La ciudad no puede ser nula");
        }
        validateCiudad(ciudad);
        return ciudadRepository.save(ciudad);
    }

    public void deleteCiudad(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID debe ser un número positivo");
        }
        if (!ciudadRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Ciudad no encontrada con ID: " + id);
        }
        ciudadRepository.deleteById(id);
    }

    public Ciudad updateCiudad(Integer id, Ciudad ciudadActualizada) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID debe ser un número positivo");
        }
        if (ciudadActualizada == null) {
            throw new IllegalArgumentException("Los datos de la ciudad no pueden ser nulos");
        }

        validateCiudad(ciudadActualizada);

        return ciudadRepository.findById(id)
                .map(ciudad -> {
                    ciudad.setNombre(ciudadActualizada.getNombre());
                    ciudad.setCodigoPostal(ciudadActualizada.getCodigoPostal());
                    return ciudadRepository.save(ciudad);
                })
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada con ID: " + id));
    }

    @Transactional(readOnly = true)
    public boolean existsById(Integer id) {
        if (id == null || id <= 0) {
            return false;
        }
        return ciudadRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public long countCiudades() {
        return ciudadRepository.count();
    }

    private void validateCiudad(Ciudad ciudad) {
        if (ciudad.getNombre() == null || ciudad.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la ciudad es obligatorio");
        }
        if (ciudad.getCodigoPostal() == null || ciudad.getCodigoPostal().trim().isEmpty()) {
            throw new IllegalArgumentException("El código postal es obligatorio");
        }
        if (ciudad.getNombre().length() > 100) {
            throw new IllegalArgumentException("El nombre no puede exceder 100 caracteres");
        }
        if (ciudad.getCodigoPostal().length() > 10) {
            throw new IllegalArgumentException("El código postal no puede exceder 10 caracteres");
        }
    }
}