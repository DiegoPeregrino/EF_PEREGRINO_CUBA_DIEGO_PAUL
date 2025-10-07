package org.cibertec.edu.pe.controller;

import org.cibertec.edu.pe.model.Ciudad;
import org.cibertec.edu.pe.repository.ICiudadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CiudadController.class)
class CiudadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICiudadRepository ciudadRepository;

    private Ciudad ciudadLima;
    private Ciudad ciudadCusco;

    @BeforeEach
    void setUp() {
        ciudadLima = new Ciudad();
        ciudadLima.setId(1);
        ciudadLima.setCodigoPostal("LIM");
        ciudadLima.setNombre("Lima");

        ciudadCusco = new Ciudad();
        ciudadCusco.setId(2);
        ciudadCusco.setCodigoPostal("CUZ");
        ciudadCusco.setNombre("Cusco");
    }

    @Test
    void testListarCiudades() throws Exception {
        // Arrange
        List<Ciudad> ciudades = Arrays.asList(ciudadLima);
        when(ciudadRepository.findAll()).thenReturn(ciudades);

        // Act & Assert
        mockMvc.perform(get("/ciudades"))
                .andExpect(status().isOk())
                .andExpect(view().name("ciudad_list"))
                .andExpect(model().attributeExists("ciudades"));
    }

    @Test
    void testMostrarFormularioNuevaCiudad() throws Exception {
        mockMvc.perform(get("/ciudades/nueva"))
                .andExpect(status().isOk())  // Cambié andExpected por andExpect
                .andExpect(view().name("ciudad_form"))
                .andExpect(model().attributeExists("ciudad"));
    }

    @Test
    void testGuardarCiudadValida() throws Exception {
        when(ciudadRepository.save(any(Ciudad.class))).thenReturn(ciudadLima);

        mockMvc.perform(post("/ciudades/guardar")
                        .param("codigoPostal", "LIM")
                        .param("nombre", "Lima")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ciudades/lista"));

        verify(ciudadRepository, times(1)).save(any(Ciudad.class));
    }

    @Test
    void testEditarCiudadExistente() throws Exception {
        // Usar Integer en lugar de String
        when(ciudadRepository.findById(1)).thenReturn(Optional.of(ciudadLima));

        mockMvc.perform(get("/ciudades/editar/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("ciudad_form"))
                .andExpect(model().attribute("ciudad", ciudadLima));

        verify(ciudadRepository, times(1)).findById(1);
    }

    @Test
    void testEliminarCiudad() throws Exception {
        // Usar Integer en lugar de String
        when(ciudadRepository.existsById(1)).thenReturn(true);
        doNothing().when(ciudadRepository).deleteById(1);

        mockMvc.perform(get("/ciudades/eliminar/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ciudades/lista"));

        verify(ciudadRepository, times(1)).deleteById(1);
    }
}