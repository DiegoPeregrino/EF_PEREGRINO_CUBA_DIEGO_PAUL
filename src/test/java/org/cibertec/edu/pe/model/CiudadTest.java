package org.cibertec.edu.pe.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CiudadTest {

    private Validator validator;
    private Ciudad ciudad;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        
        ciudad = new Ciudad();
    }

    @Test
    @DisplayName("Ciudad válida - todos los campos correctos")
    void testCiudadValida() {
        ciudad.setCodigoPostal("LIM");
        ciudad.setNombre("Lima");

        Set<ConstraintViolation<Ciudad>> violations = validator.validate(ciudad);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Validación de código postal - no puede ser nulo")
    void testCodigoPostalNoNulo() {
        ciudad.setCodigoPostal(null);
        ciudad.setNombre("Lima");

        Set<ConstraintViolation<Ciudad>> violations = validator.validate(ciudad);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Validación de nombre - no puede ser vacío")
    void testNombreNoVacio() {
        ciudad.setCodigoPostal("LIM");
        ciudad.setNombre("");

        Set<ConstraintViolation<Ciudad>> violations = validator.validate(ciudad);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Métodos equals y hashCode funcionan correctamente")
    void testEqualsYHashCode() {
        Ciudad ciudad1 = new Ciudad();
        ciudad1.setCodigoPostal("LIM");
        ciudad1.setNombre("Lima");

        Ciudad ciudad2 = new Ciudad();
        ciudad2.setCodigoPostal("LIM");
        ciudad2.setNombre("Lima");

        assertEquals(ciudad1, ciudad2);
        assertEquals(ciudad1.hashCode(), ciudad2.hashCode());
    }

    @Test
    @DisplayName("ToString devuelve formato esperado")
    void testToString() {
        ciudad.setCodigoPostal("LIM");
        ciudad.setNombre("Lima");

        String resultado = ciudad.toString();
        assertTrue(resultado.contains("LIM"));
        assertTrue(resultado.contains("Lima"));
    }
}