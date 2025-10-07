package org.cibertec.edu.pe.controller;

import org.cibertec.edu.pe.model.Ciudad;
import org.cibertec.edu.pe.repository.ICiudadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CiudadRestController.class)
class CiudadRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICiudadRepository ciudadRepository;

    private Ciudad ciudadLima;

    @BeforeEach
    void setUp() {
        ciudadLima = new Ciudad();
        ciudadLima.setId(1);
        ciudadLima.setCodigoPostal("LIM");
        ciudadLima.setNombre("Lima");
    }

    @Test
    void testGetAllCiudades() throws Exception {
        // Arrange
        List<Ciudad> ciudades = Arrays.asList(ciudadLima);
        when(ciudadRepository.findAll()).thenReturn(ciudades);

        // Act & Assert
        mockMvc.perform(get("/api/ciudades"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].nombre").value("Lima"));
    }

    @Test
    void testGetCiudadById_Found() throws Exception {
        // Arrange
        when(ciudadRepository.findById(1)).thenReturn(Optional.of(ciudadLima));

        // Act & Assert
        mockMvc.perform(get("/api/ciudades/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombre").value("Lima"));

        verify(ciudadRepository, times(1)).findById(1);
    }

    @Test
    void testGetCiudadById_NotFound() throws Exception {
        // Arrange
        when(ciudadRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/ciudades/999"))
                .andExpect(status().isNotFound());

        verify(ciudadRepository, times(1)).findById(999);
    }

    @Test
    void testCreateCiudad() throws Exception {
        // Arrange
        when(ciudadRepository.save(any(Ciudad.class))).thenReturn(ciudadLima);

        // Act & Assert
        mockMvc.perform(post("/api/ciudades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Lima\",\"codigoPostal\":\"LIM\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Lima"));

        verify(ciudadRepository, times(1)).save(any(Ciudad.class));
    }

    @Test
    void testUpdateCiudad_Found() throws Exception {
        // Arrange
        when(ciudadRepository.findById(1)).thenReturn(Optional.of(ciudadLima));
        when(ciudadRepository.save(any(Ciudad.class))).thenReturn(ciudadLima);

        // Act & Assert
        mockMvc.perform(put("/api/ciudades/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Lima Actualizada\",\"codigoPostal\":\"LIM\"}"))
                .andExpect(status().isOk());

        verify(ciudadRepository, times(1)).findById(1);
        verify(ciudadRepository, times(1)).save(any(Ciudad.class));
    }

    @Test
    void testUpdateCiudad_NotFound() throws Exception {
        // Arrange
        when(ciudadRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(put("/api/ciudades/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Lima\",\"codigoPostal\":\"LIM\"}"))
                .andExpect(status().isNotFound());

        verify(ciudadRepository, times(1)).findById(999);
    }

    @Test
    void testDeleteCiudad_Found() throws Exception {
        // Arrange
        when(ciudadRepository.findById(1)).thenReturn(Optional.of(ciudadLima));

        // Act & Assert
        mockMvc.perform(delete("/api/ciudades/1"))
                .andExpect(status().isOk());

        verify(ciudadRepository, times(1)).findById(1);
        verify(ciudadRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteCiudad_NotFound() throws Exception {
        // Arrange
        when(ciudadRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(delete("/api/ciudades/999"))
                .andExpect(status().isNotFound());

        verify(ciudadRepository, times(1)).findById(999);
    }
}