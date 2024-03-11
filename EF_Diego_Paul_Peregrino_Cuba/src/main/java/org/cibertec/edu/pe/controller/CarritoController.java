    package org.cibertec.edu.pe.controller;

    import org.cibertec.edu.pe.model.DetalleVenta;
    import org.cibertec.edu.pe.model.Venta;
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

    @Controller
    @SessionAttributes({ "carrito", "total" })
    public class CarritoController {

        @Autowired
        private IVentaRepository ventaRepository;

        private final IDetalleVentaRepository detalleVentaRepository;

        public CarritoController(IDetalleVentaRepository detalleVentaRepository) {
            this.detalleVentaRepository = detalleVentaRepository;
        }

        @GetMapping("/formularioVenta")
        public String mostrarFormularioVenta(Model model) {
            return "formularioVenta";
        }

        @PostMapping("/agregarVenta")
        public String agregarVenta(@RequestParam("ciudadOrigen") Venta ciudadOrigen,
                @RequestParam("ciudadDestino") Venta ciudadDestino,
                @RequestParam("fechaSalida") String fechaSalidaStr, @RequestParam("fechaRetorno") String fechaRetornoStr,
                @RequestParam("nombreComprador") String nombreComprador, @RequestParam("cantidad") int cantidad,
                HttpSession session) {

            Date fechaSalida = null;
            Date fechaRetorno = null;
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                fechaSalida = dateFormat.parse(fechaSalidaStr);
                fechaRetorno = dateFormat.parse(fechaRetornoStr);
            } catch (ParseException e) {
                e.printStackTrace();
                return "redirect:/formularioVenta";
            }

            double subTotal = cantidad * 50.0;

            DetalleVenta detalleVenta = new DetalleVenta();
            detalleVenta.setCiudadOrigen(ciudadOrigen);
            detalleVenta.setCiudadDestino(ciudadDestino);
            detalleVenta.setFechaViaje(fechaSalida);
            detalleVenta.setFechaRetorno(fechaRetorno);
            detalleVenta.setCantidad(cantidad);
            detalleVenta.setSubTotal(subTotal);

            detalleVentaRepository.save(detalleVenta);

            List<DetalleVenta> carrito = (List<DetalleVenta>) session.getAttribute("carrito");
            if (carrito == null) {
                carrito = new ArrayList<>();
            }
            carrito.add(detalleVenta);
            session.setAttribute("carrito", carrito);

            return "redirect:/formularioVenta";
        }

        @PostMapping("/comprar")
        public String comprar(HttpSession session) {
            List<DetalleVenta> carrito = (List<DetalleVenta>) session.getAttribute("carrito");
            if (carrito != null && !carrito.isEmpty()) {
                for (DetalleVenta detalle : carrito) {
                    detalleVentaRepository.save(detalle);
                }
                session.removeAttribute("carrito");
                session.removeAttribute("total");
            }
            return "redirect:/formularioVenta";
        }

        private double calcularTotal(List<DetalleVenta> carrito) {
            double total = 0.0;
            for (DetalleVenta detalle : carrito) {
                total += detalle.getSubTotal();
            }
            return total;
        }
    }

