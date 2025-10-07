# ✈️ VivaAir - Sistema de Reservas de Vuelos

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Bootstrap](https://img.shields.io/badge/Bootstrap-5.3.0-blue.svg)](https://getbootstrap.com/)
[![Maven](https://img.shields.io/badge/Maven-3.9.0-red.svg)](https://maven.apache.org/)

## 📖 Descripción

Sistema web desarrollado como proyecto académico para la gestión de reservas de vuelos. Implementa funcionalidades básicas de e-commerce usando Spring Boot y siguiendo el patrón MVC.

La version actual es una mejora del proyecto presentado para el curso.

### 🎯 Objetivos del Proyecto

- Aplicar conceptos de Spring Boot en un proyecto real
- Implementar arquitectura MVC
- Crear APIs REST funcionales
- Desarrollar una interfaz web responsiva
- Practicar testing con JUnit

## 🛠️ Tecnologías Utilizadas

**Backend:**

- Java 17
- Spring Boot 3.2
- Spring Data JPA
- Maven
- H2 Database (desarrollo)

**Frontend:**

- Thymeleaf
- Bootstrap 5
- JavaScript
- CSS3

**Testing:**

- JUnit 5
- Spring Boot Test

## 🚀 Instalación y Ejecución

### Requisitos

- Java 17+
- Maven 3.6+

### Pasos para ejecutar

```bash
# Clonar el repositorio
git clone [url-del-repositorio]
cd EF_Diegol_Peregrino

# Compilar y ejecutar
mvn clean install
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8100`

## 📋 Funcionalidades

### Gestión de Ciudades

- Listar ciudades disponibles
- Agregar nuevas ciudades
- API REST para operaciones CRUD

### Sistema de Reservas

- Formulario de reserva de vuelos
- Selección de origen y destino
- Fechas de ida y vuelta
- Cantidad de pasajeros

### Carrito de Compras

- Agregar múltiples reservas
- Ver resumen de compras
- Proceso de finalización

## 🌐 API REST

### Endpoints disponibles

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/ciudades` | Obtener todas las ciudades |
| POST | `/api/ciudades` | Crear nueva ciudad |
| GET | `/api/ciudades/{codigo}` | Obtener ciudad por código |
| PUT | `/api/ciudades/{codigo}` | Actualizar ciudad |
| DELETE | `/api/ciudades/{codigo}` | Eliminar ciudad |

### Ejemplo de uso

```json
POST /api/ciudades
{
    "codigoPostal": "LIM",
    "nombre": "Lima"
}
```

## 🧪 Testing

```bash
# Ejecutar tests
mvn test

# Ver reporte de cobertura
mvn jacoco:report
```

El proyecto incluye tests unitarios para los principales componentes.

## 📁 Estructura del Proyecto

src/
├── main/
│   ├── java/
│   │   └── org/cibertec/edu/pe/
│   │       ├── controller/     # Controladores web y REST
│   │       ├── model/          # Entidades JPA
│   │       ├── repository/     # Repositorios de datos
│   │       └── service/        # Lógica de negocio
│   └── resources/
│       ├── static/             # CSS, JS, imágenes
│       ├── templates/          # Plantillas Thymeleaf
│       └── application.properties
└── test/                       # Tests unitarios

## 📊 Base de Datos

El proyecto utiliza H2 como base de datos en memoria para facilitar el desarrollo y testing. Las tablas principales son:

- `tb_ciudad` - Información de ciudades
- `tb_ventas` - Registro de ventas
- `detalle_venta` - Detalles de cada venta

## 🔧 Configuración

La aplicación se puede configurar mediante `application.properties`:

```properties
server.port=8100
spring.datasource.url=jdbc:h2:mem:vivaairdb
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=create-drop
```

## 📱 Capturas de Pantalla

*[Aquí se pueden agregar algunas capturas de pantalla del sistema]*

## 🚀 Mejoras Futuras

Algunas funcionalidades que se podrían implementar:

- [ ] Sistema de autenticación
- [ ] Integración con base de datos MySQL
- [ ] Notificaciones por email
- [ ] Sistema de pagos
- [ ] Búsqueda avanzada de vuelos
- [ ] Panel administrativo

## 🤝 Contribuciones

Este es un proyecto académico, pero las sugerencias son bienvenidas. Si encuentras algún error o tienes ideas de mejora, puedes:

1. Crear un issue
2. Hacer un fork del proyecto
3. Crear un pull request

## 👨‍💻 Autor

### Diego Peregrino

- Estudiante de Computacion e informatica - CIBERTEC

## 📝 Notas Académicas

La version base de este proyecto fue desarrollado como parte del curso de Spring Boot en CIBERTEC, posteriormente adaptado para portafolio. Su propósito es demostrar la comprensión de:

- Arquitectura MVC con Spring Boot
- Desarrollo de APIs REST
- Integración frontend-backend
- Testing de aplicaciones Spring
- Buenas prácticas de desarrollo

## 📄 Licencia

Este proyecto es de uso académico y está disponible bajo licencia MIT para fines educativos.

---

### Desarrollado como proyecto final del curso Spring Boot - CIBERTEC 2024
