package org.cibertec.edu.pe.controller;

import org.cibertec.edu.pe.model.Ciudad;
import org.cibertec.edu.pe.repository.ICiudadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/ciudades")
public class CiudadController {

    @Autowired
    private ICiudadRepository ciudadRepository;

    @GetMapping
    public String listarCiudades(Model model) {
        model.addAttribute("ciudades", ciudadRepository.findAll());
        return "ciudad_list";
    }

    @GetMapping("/nueva")
    public String mostrarFormularioNueva(Model model) {
        model.addAttribute("ciudad", new Ciudad());
        return "ciudad_form";
    }

    @PostMapping("/guardar")
    public String guardarCiudad(@Valid @ModelAttribute Ciudad ciudad, BindingResult result) {
        if (result.hasErrors()) {
            return "ciudad_form";
        }
        ciudadRepository.save(ciudad);
        return "redirect:/ciudades/lista";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
        Optional<Ciudad> ciudad = ciudadRepository.findById(id);
        if (ciudad.isPresent()) {
            model.addAttribute("ciudad", ciudad.get());
            return "ciudad_form";
        }
        return "redirect:/ciudades";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCiudad(@PathVariable Integer id) {
        ciudadRepository.deleteById(id);
        return "redirect:/ciudades/lista";
    }
}
