package org.cibertec.edu.pe.controller;

import org.cibertec.edu.pe.model.Ciudad;
import org.cibertec.edu.pe.repository.ICiudadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ciudades")
public class CiudadController {

    @Autowired
    private ICiudadRepository ciudadRepository;

    // Obtener todas las ciudades
    @GetMapping("/")
    public List<Ciudad> getAllCiudades() {
        return ciudadRepository.findAll();
    }

    // Obtener una ciudad por su código postal
    @GetMapping("/{codigoPostal}")
    public Ciudad getCiudadByCodigoPostal(@PathVariable String codigoPostal) {
        return ciudadRepository.findByCodigoPostal(codigoPostal);
    }

    // Crear una nueva ciudad
    @PostMapping("/")
    public Ciudad createCiudad(@RequestBody Ciudad ciudad) {
        return ciudadRepository.save(ciudad);
    }

    // Actualizar una ciudad existente
    @PutMapping("/{codigoPostal}")
    public Ciudad updateCiudad(@PathVariable String codigoPostal, @RequestBody Ciudad ciudadDetails) {
        Ciudad ciudad = ciudadRepository.findByCodigoPostal(codigoPostal);

        ciudad.setNombre(ciudadDetails.getNombre()); // Actualiza el nombre u otros atributos según sea necesario

        return ciudadRepository.save(ciudad);
    }

    // Eliminar una ciudad existente
    @DeleteMapping("/{codigoPostal}")
    public void deleteCiudad(@PathVariable String codigoPostal) {
        Ciudad ciudad = ciudadRepository.findByCodigoPostal(codigoPostal);
        ciudadRepository.delete(ciudad);
    }
}
