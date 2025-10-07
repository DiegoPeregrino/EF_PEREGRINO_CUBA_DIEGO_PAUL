package org.cibertec.edu.pe.controller;

import org.cibertec.edu.pe.model.Ciudad;
import org.cibertec.edu.pe.model.DetalleVenta;
import org.cibertec.edu.pe.model.Venta;
import org.cibertec.edu.pe.repository.ICiudadRepository;
import org.cibertec.edu.pe.repository.IDetalleVentaRepository;
import org.cibertec.edu.pe.repository.IVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class CarritoController {

    @Autowired
    private IVentaRepository ventaRepository;

    @Autowired
    private IDetalleVentaRepository detalleVentaRepository;

    @Autowired
    private ICiudadRepository ciudadRepository;

    @GetMapping("/formularioVenta")
    public String mostrarFormularioVenta(Model model) {
        model.addAttribute("ciudades", ciudadRepository.findAll());
        return "venta_form";
    }

    @PostMapping("/agregarVenta")
    public String agregarVenta(@RequestParam("ciudadOrigen") Integer ciudadOrigenId,
            @RequestParam("ciudadDestino") Integer ciudadDestinoId,
            @RequestParam("fechaSalida") String fechaSalidaStr, 
            @RequestParam("fechaRetorno") String fechaRetornoStr,
            @RequestParam("nombreComprador") String nombreComprador, 
            @RequestParam("cantidad") int cantidad,
            HttpSession session) {

        Ciudad ciudadOrigen = ciudadRepository.findById(ciudadOrigenId).orElse(null);
        Ciudad ciudadDestino = ciudadRepository.findById(ciudadDestinoId).orElse(null);

        if (ciudadOrigen == null || ciudadDestino == null) {
            return "redirect:/formularioVenta?error=ciudades";
        }

        Date fechaSalida = null;
        Date fechaRetorno = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            fechaSalida = dateFormat.parse(fechaSalidaStr);
            fechaRetorno = dateFormat.parse(fechaRetornoStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return "redirect:/formularioVenta?error=fechas";
        }

        double subTotal = cantidad * 50.0;

        DetalleVenta detalleVenta = new DetalleVenta();
        detalleVenta.setCiudadOrigen(ciudadOrigen);
        detalleVenta.setCiudadDestino(ciudadDestino);
        detalleVenta.setFechaViaje(fechaSalida);
        detalleVenta.setFechaRetorno(fechaRetorno);
        detalleVenta.setCantidad(cantidad);
        detalleVenta.setSubTotal(subTotal);
        detalleVenta.setNombreComprador(nombreComprador);

        @SuppressWarnings("unchecked")
        List<DetalleVenta> carrito = (List<DetalleVenta>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
        }
        carrito.add(detalleVenta);
        session.setAttribute("carrito", carrito);

        double total = calcularTotal(carrito);
        session.setAttribute("total", total);

        return "redirect:/carrito";
    }

    @GetMapping("/carrito")
    public String mostrarCarrito(HttpSession session, Model model) {
        @SuppressWarnings("unchecked")
        List<DetalleVenta> carrito = (List<DetalleVenta>) session.getAttribute("carrito");
        Double total = (Double) session.getAttribute("total");
        model.addAttribute("carrito", carrito != null ? carrito : new ArrayList<>());
        model.addAttribute("total", total != null ? total : 0.0);
        return "carrito";
    }

    @PostMapping("/comprar")
    public String comprar(HttpSession session, Model model) {
        @SuppressWarnings("unchecked")
        List<DetalleVenta> carrito = (List<DetalleVenta>) session.getAttribute("carrito");
        Double total = (Double) session.getAttribute("total");
        
        if (carrito != null && !carrito.isEmpty()) {
            // Crear la venta
            Venta venta = new Venta();
            venta.setFechaVenta(new Date());
            venta.setTotal(total);
            venta.setNombreComprador(carrito.get(0).getNombreComprador());
            venta = ventaRepository.save(venta);

            // Asociar detalles a la venta y guardar
            for (DetalleVenta detalle : carrito) {
                detalle.setVenta(venta);
                detalleVentaRepository.save(detalle);
            }

            session.removeAttribute("carrito");
            session.removeAttribute("total");
            
            model.addAttribute("mensaje", "Compra realizada exitosamente");
            model.addAttribute("ventaId", venta.getId());
            return "confirmacion_pago";
        }
        return "redirect:/carrito";
    }

    @PostMapping("/limpiar")
    public String limpiarCarrito(HttpSession session) {
        session.removeAttribute("carrito");
        session.removeAttribute("total");
        return "redirect:/formularioVenta";
    }

    @GetMapping("/agregar")
    public String agregarAlCarrito(@RequestParam Integer ciudadOrigenId, 
                                   @RequestParam Integer ciudadDestinoId,
                                   HttpSession session) {
        Optional<Ciudad> ciudadOrigen = ciudadRepository.findById(ciudadOrigenId);
        Optional<Ciudad> ciudadDestino = ciudadRepository.findById(ciudadDestinoId);
        
        if (!ciudadOrigen.isPresent() || !ciudadDestino.isPresent()) {
            return "redirect:/formularioVenta?error=ciudades";
        }

        Ciudad origen = ciudadOrigen.get();
        Ciudad destino = ciudadDestino.get();

        DetalleVenta detalleVenta = new DetalleVenta();
        detalleVenta.setCiudadOrigen(origen);
        detalleVenta.setCiudadDestino(destino);
        detalleVenta.setFechaViaje(new Date());
        detalleVenta.setFechaRetorno(new Date());
        detalleVenta.setCantidad(1);
        detalleVenta.setSubTotal(50.0);
        detalleVenta.setNombreComprador("Cliente Visitante");

        @SuppressWarnings("unchecked")
        List<DetalleVenta> carrito = (List<DetalleVenta>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
        }
        carrito.add(detalleVenta);
        session.setAttribute("carrito", carrito);

        double total = calcularTotal(carrito);
        session.setAttribute("total", total);

        return "redirect:/carrito";
    }

    @GetMapping("/ver")
    public String verCarrito(HttpSession session, Model model) {
        @SuppressWarnings("unchecked")
        List<DetalleVenta> carrito = (List<DetalleVenta>) session.getAttribute("carrito");
        Double total = (Double) session.getAttribute("total");
        model.addAttribute("carrito", carrito != null ? carrito : new ArrayList<>());
        model.addAttribute("total", total != null ? total : 0.0);
        return "carrito";
    }

    @PostMapping("/actualizar")
    public String actualizarCarrito(@RequestParam Map<String, String> params, HttpSession session) {
        @SuppressWarnings("unchecked")
        List<DetalleVenta> carrito = (List<DetalleVenta>) session.getAttribute("carrito");
        
        if (carrito != null) {
            for (DetalleVenta detalle : carrito) {
                String cantidadStr = params.get("cantidad_" + detalle.getId());
                if (cantidadStr != null) {
                    int cantidad = Integer.parseInt(cantidadStr);
                    detalle.setCantidad(cantidad);
                    detalle.setSubTotal(cantidad * 50.0); // Suponiendo un precio fijo de 50.0
                }
            }
            session.setAttribute("carrito", carrito);
            double total = calcularTotal(carrito);
            session.setAttribute("total", total);
        }
        return "redirect:/carrito";
    }

    @GetMapping("/eliminar/{index}")
    public String eliminarDelCarrito(@PathVariable int index, HttpSession session) {
        @SuppressWarnings("unchecked")
        List<DetalleVenta> carrito = (List<DetalleVenta>) session.getAttribute("carrito");
        if (carrito != null && index >= 0 && index < carrito.size()) {
            carrito.remove(index);
            session.setAttribute("carrito", carrito);
            double total = calcularTotal(carrito);
            session.setAttribute("total", total);
        }
        return "redirect:/carrito";
    }

    private double calcularTotal(List<DetalleVenta> carrito) {
        double total = 0.0;
        for (DetalleVenta detalle : carrito) {
            total += detalle.getSubTotal();
        }
        return total;
    }
}

