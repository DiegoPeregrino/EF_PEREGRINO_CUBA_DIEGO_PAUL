package org.cibertec.edu.pe.controller;

import org.cibertec.edu.pe.model.Ciudad;
import org.cibertec.edu.pe.repository.ICiudadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ciudades")
public class CiudadRestController {

    @Autowired
    private ICiudadRepository ciudadRepository;

    @GetMapping
    public List<Ciudad> getAllCiudades() {
        return ciudadRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ciudad> getCiudadById(@PathVariable Integer id) {
        Optional<Ciudad> ciudad = ciudadRepository.findById(id);
        return ciudad.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Ciudad createCiudad(@RequestBody Ciudad ciudad) {
        return ciudadRepository.save(ciudad);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ciudad> updateCiudad(@PathVariable Integer id, @RequestBody Ciudad ciudadActualizada) {
        Optional<Ciudad> ciudad = ciudadRepository.findById(id);
        if (ciudad.isPresent()) {
            Ciudad c = ciudad.get();
            c.setNombre(ciudadActualizada.getNombre());
            c.setCodigoPostal(ciudadActualizada.getCodigoPostal());
            return ResponseEntity.ok(ciudadRepository.save(c));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCiudad(@PathVariable Integer id) {
        Optional<Ciudad> ciudad = ciudadRepository.findById(id);
        if (ciudad.isPresent()) {
            ciudadRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}