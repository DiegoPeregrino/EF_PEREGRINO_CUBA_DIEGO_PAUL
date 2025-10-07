package org.cibertec.edu.pe.controller;

import org.cibertec.edu.pe.model.Ciudad;
import org.cibertec.edu.pe.model.DetalleVenta;
import org.cibertec.edu.pe.model.Venta;
import org.cibertec.edu.pe.repository.ICiudadRepository;
import org.cibertec.edu.pe.repository.IDetalleVentaRepository;
import org.cibertec.edu.pe.repository.IVentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarritoController.class)
class CarritoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IVentaRepository ventaRepository;

    @MockBean
    private IDetalleVentaRepository detalleVentaRepository;

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
    void testMostrarFormularioVenta() throws Exception {
        List<Ciudad> ciudades = List.of(ciudadLima, ciudadCusco);
        when(ciudadRepository.findAll()).thenReturn(ciudades);

        mockMvc.perform(get("/formularioVenta"))
                .andExpect(status().isOk())
                .andExpect(view().name("venta_form"))
                .andExpect(model().attributeExists("ciudades"));
    }

    @Test
    void testAgregarVenta() throws Exception {
        when(ciudadRepository.findById(1)).thenReturn(Optional.of(ciudadLima));
        when(ciudadRepository.findById(2)).thenReturn(Optional.of(ciudadCusco));

        mockMvc.perform(post("/agregarVenta")
                        .param("ciudadOrigen", "1")
                        .param("ciudadDestino", "2")
                        .param("fechaSalida", "2024-12-01")
                        .param("fechaRetorno", "2024-12-10")
                        .param("nombreComprador", "Juan Perez")
                        .param("cantidad", "2")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/carrito"));

        verify(ciudadRepository, times(1)).findById(1);
        verify(ciudadRepository, times(1)).findById(2);
    }

    @Test
    void testAgregarAlCarrito() throws Exception {
        when(ciudadRepository.findById(1)).thenReturn(Optional.of(ciudadLima));
        when(ciudadRepository.findById(2)).thenReturn(Optional.of(ciudadCusco));

        mockMvc.perform(get("/agregar")
                        .param("ciudadOrigenId", "1")
                        .param("ciudadDestinoId", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/carrito"));

        verify(ciudadRepository, times(1)).findById(1);
        verify(ciudadRepository, times(1)).findById(2);
    }

    @Test
    void testMostrarCarrito() throws Exception {
        MockHttpSession session = new MockHttpSession();
        List<DetalleVenta> carrito = new ArrayList<>();
        session.setAttribute("carrito", carrito);
        session.setAttribute("total", 100.0);

        mockMvc.perform(get("/carrito").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("carrito"))
                .andExpect(model().attributeExists("carrito"))
                .andExpect(model().attributeExists("total"));
    }

    @Test
    void testLimpiarCarrito() throws Exception {
        mockMvc.perform(post("/limpiar"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/formularioVenta"));
    }

    @Test
    void testComprar() throws Exception {
        MockHttpSession session = new MockHttpSession();
        List<DetalleVenta> carrito = new ArrayList<>();
        
        DetalleVenta detalle = new DetalleVenta();
        detalle.setNombreComprador("Juan Perez");
        detalle.setSubTotal(100.0);
        carrito.add(detalle);
        
        session.setAttribute("carrito", carrito);
        session.setAttribute("total", 100.0);

        Venta venta = new Venta();
        venta.setId(1); // ← Cambiar aquí: usar 1 en lugar de 1L
        when(ventaRepository.save(any(Venta.class))).thenReturn(venta);
        when(detalleVentaRepository.save(any(DetalleVenta.class))).thenReturn(detalle);

        mockMvc.perform(post("/comprar").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("confirmacion_pago"))
                .andExpect(model().attributeExists("mensaje"))
                .andExpect(model().attributeExists("ventaId"));

        verify(ventaRepository, times(1)).save(any(Venta.class));
        verify(detalleVentaRepository, times(1)).save(any(DetalleVenta.class));
    }
}