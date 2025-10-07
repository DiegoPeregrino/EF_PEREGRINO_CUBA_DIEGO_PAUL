/**
 * VivaAir - Scripts principales
 * Autor: Diego Paul Peregrino
 * CIBERTEC 2025
 */

document.addEventListener('DOMContentLoaded', function() {
    // Inicializar componentes
    initializeApp();
    
    // Validaciones de formularios
    initializeFormValidations();
    
    // Efectos visuales
    initializeAnimations();
    
    console.log('🛩️ VivaAir - Sistema iniciado correctamente');
});

/**
 * Inicializa la aplicación
 */
function initializeApp() {
    // Configurar tooltips de Bootstrap
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
    
    // Configurar navbar activo
    setActiveNavItem();
    
    // Auto-close alerts
    autoCloseAlerts();
}

/**
 * Marca el item activo en la navegación
 */
function setActiveNavItem() {
    const currentPath = window.location.pathname;
    const navLinks = document.querySelectorAll('.navbar-nav .nav-link');
    
    navLinks.forEach(link => {
        link.classList.remove('active');
        if (link.getAttribute('href') === currentPath) {
            link.classList.add('active');
        }
    });
}

/**
 * Inicializa validaciones de formularios
 */
function initializeFormValidations() {
    // Validación de formulario de venta
    const ventaForm = document.querySelector('form[action*="agregarVenta"]');
    if (ventaForm) {
        setupVentaFormValidation(ventaForm);
    }
    
    // Validación de formulario de ciudad
    const ciudadForm = document.querySelector('form[action*="ciudades"]');
    if (ciudadForm) {
        setupCiudadFormValidation(ciudadForm);
    }
}

/**
 * Configurar validación del formulario de venta
 */
function setupVentaFormValidation(form) {
    const origenSelect = form.querySelector('select[name="ciudadOrigen"]');
    const destinoSelect = form.querySelector('select[name="ciudadDestino"]');
    const fechaSalida = form.querySelector('input[name="fechaSalida"]');
    const fechaRetorno = form.querySelector('input[name="fechaRetorno"]');
    const nombreComprador = form.querySelector('input[name="nombreComprador"]');
    const cantidad = form.querySelector('input[name="cantidad"]');
    
    // Validar que origen y destino sean diferentes
    function validarRuta() {
        if (origenSelect.value && destinoSelect.value && origenSelect.value === destinoSelect.value) {
            showAlert('El destino debe ser diferente al origen', 'warning');
            destinoSelect.value = '';
            return false;
        }
        return true;
    }
    
    // Validar fechas
    function validarFechas() {
        const hoy = new Date().toISOString().split('T')[0];
        
        if (fechaSalida.value < hoy) {
            showAlert('La fecha de salida no puede ser anterior a hoy', 'warning');
            fechaSalida.value = '';
            return false;
        }
        
        if (fechaRetorno.value && fechaRetorno.value <= fechaSalida.value) {
            showAlert('La fecha de retorno debe ser posterior a la fecha de salida', 'warning');
            fechaRetorno.value = '';
            return false;
        }
        
        return true;
    }
    
    // Event listeners
    origenSelect.addEventListener('change', validarRuta);
    destinoSelect.addEventListener('change', validarRuta);
    fechaSalida.addEventListener('change', validarFechas);
    fechaRetorno.addEventListener('change', validarFechas);
    
    // Validación de nombre (solo letras y espacios)
    nombreComprador.addEventListener('input', function() {
        const regex = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/;
        if (this.value && !regex.test(this.value)) {
            this.setCustomValidity('Solo se permiten letras y espacios');
        } else {
            this.setCustomValidity('');
        }
    });
    
    // Validación del formulario completo
    form.addEventListener('submit', function(e) {
        if (!validarRuta() || !validarFechas()) {
            e.preventDefault();
            return false;
        }
        
        // Mostrar loading
        const submitBtn = form.querySelector('button[type="submit"]');
        submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin me-2"></i>Procesando...';
        submitBtn.disabled = true;
    });
}

/**
 * Configurar validación del formulario de ciudad
 */
function setupCiudadFormValidation(form) {
    const codigoPostal = form.querySelector('input[name="codigoPostal"]');
    const nombre = form.querySelector('input[name="nombre"]');
    
    // Validar código postal (solo letras mayúsculas)
    codigoPostal.addEventListener('input', function() {
        this.value = this.value.toUpperCase();
        const regex = /^[A-Z]{3,5}$/;
        if (this.value && !regex.test(this.value)) {
            this.setCustomValidity('Código debe tener 3-5 letras mayúsculas');
        } else {
            this.setCustomValidity('');
        }
    });
    
    // Validar nombre de ciudad
    nombre.addEventListener('input', function() {
        this.value = this.value.charAt(0).toUpperCase() + this.value.slice(1).toLowerCase();
        const regex = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/;
        if (this.value && !regex.test(this.value)) {
            this.setCustomValidity('Solo se permiten letras y espacios');
        } else {
            this.setCustomValidity('');
        }
    });
}

/**
 * Mostrar alertas personalizadas
 */
function showAlert(message, type = 'info', duration = 5000) {
    // Crear elemento de alerta
    const alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${type} alert-dismissible fade show position-fixed`;
    alertDiv.style.cssText = 'top: 20px; right: 20px; z-index: 9999; min-width: 300px;';
    alertDiv.innerHTML = `
        <i class="fas fa-${getIconForAlertType(type)} me-2"></i>
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;
    
    // Agregar al DOM
    document.body.appendChild(alertDiv);
    
    // Auto-remove después del tiempo especificado
    setTimeout(() => {
        if (alertDiv.parentNode) {
            alertDiv.remove();
        }
    }, duration);
}

/**
 * Obtener icono para tipo de alerta
 */
function getIconForAlertType(type) {
    const icons = {
        'success': 'check-circle',
        'warning': 'exclamation-triangle',
        'danger': 'exclamation-circle',
        'info': 'info-circle'
    };
    return icons[type] || 'info-circle';
}

/**
 * Auto-cerrar alertas después de un tiempo
 */
function autoCloseAlerts() {
    const alerts = document.querySelectorAll('.alert:not(.alert-dismissible)');
    alerts.forEach(alert => {
        setTimeout(() => {
            if (alert.parentNode) {
                alert.style.opacity = '0';
                setTimeout(() => alert.remove(), 300);
            }
        }, 5000);
    });
}

/**
 * Inicializar animaciones
 */
function initializeAnimations() {
    // Intersection Observer para animaciones en scroll
    const observerOptions = {
        threshold: 0.1,
        rootMargin: '0px 0px -50px 0px'
    };
    
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('fade-in-up');
                observer.unobserve(entry.target);
            }
        });
    }, observerOptions);
    
    // Observar elementos con clase animate
    document.querySelectorAll('.animate-on-scroll').forEach(el => {
        observer.observe(el);
    });
}

/**
 * Formatear moneda
 */
function formatCurrency(amount) {
    return new Intl.NumberFormat('es-PE', {
        style: 'currency',
        currency: 'PEN'
    }).format(amount);
}

/**
 * Formatear fecha
 */
function formatDate(date) {
    return new Intl.DateTimeFormat('es-PE', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    }).format(new Date(date));
}

/**
 * Confirmar acción
 */
function confirmAction(message, callback) {
    if (confirm(message)) {
        callback();
    }
}

/**
 * Funciones para el carrito
 */
const Cart = {
    /**
     * Actualizar contador del carrito
     */
    updateCartCount: function() {
        // Esta función se conectaría con el backend para obtener el count actual
        const cartCount = document.querySelector('.cart-count');
        if (cartCount) {
            // Simular actualización
            fetch('/api/carrito/count')
                .then(response => response.json())
                .then(data => {
                    cartCount.textContent = data.count;
                })
                .catch(error => console.error('Error:', error));
        }
    },
    
    /**
     * Limpiar carrito con confirmación
     */
    clearCart: function() {
        confirmAction('¿Estás seguro de que quieres vaciar el carrito?', function() {
            window.location.href = '/carrito/limpiar';
        });
    }
};

// Hacer funciones globales disponibles
window.VivaAir = {
    showAlert,
    formatCurrency,
    formatDate,
    confirmAction,
    Cart
};