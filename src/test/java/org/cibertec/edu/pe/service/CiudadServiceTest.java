package org.cibertec.edu.pe.service;

import org.cibertec.edu.pe.model.Ciudad;
import org.cibertec.edu.pe.repository.ICiudadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CiudadServiceTest {

    @Mock
    private ICiudadRepository ciudadRepository;

    @InjectMocks
    private CiudadService ciudadService;

    private Ciudad ciudad;

    @BeforeEach
    void setUp() {
        ciudad = new Ciudad();
        ciudad.setId(1);
        ciudad.setNombre("Lima");
        ciudad.setCodigoPostal("LIM");
    }

    @Test
    public void testGetCiudadById_Success() {
        // Arrange
        when(ciudadRepository.findById(1)).thenReturn(Optional.of(ciudad));

        // Act
        Optional<Ciudad> result = ciudadService.getCiudadById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Lima", result.get().getNombre());
        verify(ciudadRepository, times(1)).findById(1);
    }

    @Test
    public void testGetCiudadByIdOrThrow_Success() {
        // Arrange
        when(ciudadRepository.findById(1)).thenReturn(Optional.of(ciudad));

        // Act
        Ciudad result = ciudadService.getCiudadByIdOrThrow(1);

        // Assert
        assertNotNull(result);
        assertEquals("Lima", result.getNombre());
    }

    @Test
    public void testGetCiudadByIdOrThrow_NotFound() {
        // Arrange
        when(ciudadRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            ciudadService.getCiudadByIdOrThrow(999);
        });
    }

    @Test
    public void testGetCiudadById_InvalidId() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            ciudadService.getCiudadById(-1);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            ciudadService.getCiudadById(null);
        });
    }

    @Test
    public void testSaveCiudad_Success() {
        // Arrange
        when(ciudadRepository.save(ciudad)).thenReturn(ciudad);

        // Act
        Ciudad result = ciudadService.saveCiudad(ciudad);

        // Assert
        assertNotNull(result);
        assertEquals("Lima", result.getNombre());
        verify(ciudadRepository, times(1)).save(ciudad);
    }

    @Test
    public void testSaveCiudad_NullCiudad() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            ciudadService.saveCiudad(null);
        });
    }

    @Test
    public void testDeleteCiudad_Success() {
        // Arrange
        when(ciudadRepository.existsById(1)).thenReturn(true);

        // Act
        ciudadService.deleteCiudad(1);

        // Assert
        verify(ciudadRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteCiudad_NotFound() {
        // Arrange
        when(ciudadRepository.existsById(999)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            ciudadService.deleteCiudad(999);
        });
    }
}