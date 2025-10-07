-- =========================================
-- VIVAAIR - SISTEMA DE VENTAS DE BOLETOS
-- Script actualizado para coincidir con entidades JPA
-- Autor: Diego Peregrino - ACTUALIZADO
-- =========================================

-- Crear base de datos
DROP DATABASE IF EXISTS vuelos;
CREATE DATABASE vuelos CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE vuelos;

-- =========================================
-- ESTRUCTURA DE TABLAS ACTUALIZADA
-- =========================================

-- Tabla de ciudades (actualizada para coincidir con Ciudad.java)
CREATE TABLE ciudades (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo_postal VARCHAR(10) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    INDEX idx_codigo_postal (codigo_postal),
    INDEX idx_nombre (nombre)
) ENGINE=InnoDB;

-- Tabla de ventas (coincide con Venta.java)
CREATE TABLE tb_ventas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha_venta TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10,2) NOT NULL CHECK (total >= 0),
    nombre_comprador VARCHAR(100) NOT NULL,
    INDEX idx_fecha_venta (fecha_venta),
    INDEX idx_comprador (nombre_comprador)
) ENGINE=InnoDB;

-- Tabla de detalles de venta (actualizada para DetalleVenta.java)
CREATE TABLE detalle_venta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ciudad_origen_id INT NOT NULL,
    ciudad_destino_id INT NOT NULL,
    fecha_viaje DATE NOT NULL,
    fecha_retorno DATE,
    cantidad INT DEFAULT 1 CHECK (cantidad > 0),
    sub_total DECIMAL(10,2) NOT NULL CHECK (sub_total >= 0),
    nombre_comprador VARCHAR(100) NOT NULL,
    venta_id INT,
    
    FOREIGN KEY (ciudad_origen_id) REFERENCES ciudades(id) ON DELETE RESTRICT,
    FOREIGN KEY (ciudad_destino_id) REFERENCES ciudades(id) ON DELETE RESTRICT,
    FOREIGN KEY (venta_id) REFERENCES tb_ventas(id) ON DELETE CASCADE,
    
    INDEX idx_fecha_viaje (fecha_viaje),
    INDEX idx_ciudades (ciudad_origen_id, ciudad_destino_id),
    
    CONSTRAINT chk_fechas CHECK (fecha_retorno IS NULL OR fecha_retorno >= fecha_viaje),
    CONSTRAINT chk_ciudades_diferentes CHECK (ciudad_origen_id != ciudad_destino_id)
) ENGINE=InnoDB;

-- =========================================
-- DATOS DE DEMOSTRACIÓN ACTUALIZADOS
-- =========================================

-- Ciudades principales del Perú (usando ID autoincremental)
INSERT INTO ciudades (codigo_postal, nombre) VALUES
('LIM', 'Lima'),
('CUZ', 'Cusco'),
('AQP', 'Arequipa'),
('TRU', 'Trujillo'),
('CHI', 'Chiclayo'),
('PIU', 'Piura'),
('IQT', 'Iquitos'),
('HUA', 'Huánuco'),
('AYA', 'Ayacucho'),
('HUY', 'Huancayo'),
('TAC', 'Tacna'),
('PUC', 'Pucallpa'),
('TUM', 'Tumbes'),
('CAJ', 'Cajamarca'),
('PUN', 'Puno');

-- Ciudades internacionales
INSERT INTO ciudades (codigo_postal, nombre) VALUES
('BOG', 'Bogotá'),
('QUI', 'Quito'),
('SCL', 'Santiago'),
('LAP', 'La Paz'),
('CAR', 'Caracas'),
('MIA', 'Miami'),
('MAD', 'Madrid'),
('PAR', 'París'),
('LON', 'Londres'),
('NYC', 'Nueva York');

-- Ventas de ejemplo
INSERT INTO tb_ventas (fecha_venta, total, nombre_comprador) VALUES
('2025-01-15 09:30:00', 150.00, 'Ana García Rodríguez'),
('2025-01-16 14:45:00', 300.00, 'Carlos López Mendoza'),
('2025-01-17 11:20:00', 450.00, 'María Fernández Silva'),
('2025-01-18 16:10:00', 200.00, 'José Ramírez Torres'),
('2025-01-19 08:55:00', 350.00, 'Laura Martínez Vega');

-- Detalles de venta de ejemplo (usando IDs de ciudades)
INSERT INTO detalle_venta (ciudad_origen_id, ciudad_destino_id, fecha_viaje, fecha_retorno, cantidad, sub_total, nombre_comprador, venta_id) VALUES
-- Venta 1: Lima (ID=1) -> Cusco (ID=2) (ida y vuelta)
(1, 2, '2025-02-01', '2025-02-05', 3, 150.00, 'Ana García Rodríguez', 1),

-- Venta 2: Lima -> Arequipa (solo ida) + Lima -> Miami (ida y vuelta)
(1, 3, '2025-02-10', NULL, 2, 100.00, 'Carlos López Mendoza', 2),
(1, 21, '2025-03-15', '2025-03-25', 4, 200.00, 'Carlos López Mendoza', 2),

-- Venta 3: Trujillo -> Lima -> Madrid (viaje internacional)
(4, 1, '2025-02-20', NULL, 1, 50.00, 'María Fernández Silva', 3),
(1, 22, '2025-02-21', '2025-03-01', 8, 400.00, 'María Fernández Silva', 3),

-- Venta 4: Cusco -> Lima (ida y vuelta)
(2, 1, '2025-02-25', '2025-02-28', 4, 200.00, 'José Ramírez Torres', 4),

-- Venta 5: Lima -> Nueva York (viaje de negocios)
(1, 25, '2025-03-10', '2025-03-20', 7, 350.00, 'Laura Martínez Vega', 5);

-- =========================================
-- CONSULTAS DE DEMOSTRACIÓN ACTUALIZADAS
-- =========================================

-- Mostrar todas las ciudades disponibles
SELECT 'CIUDADES DISPONIBLES' as CONSULTA;
SELECT id, codigo_postal as 'Código', nombre as 'Ciudad' 
FROM ciudades 
ORDER BY nombre;

-- Mostrar ventas con totales
SELECT 'RESUMEN DE VENTAS' as CONSULTA;
SELECT 
    v.id as 'ID Venta',
    v.nombre_comprador as 'Comprador',
    DATE_FORMAT(v.fecha_venta, '%d/%m/%Y %H:%i') as 'Fecha Venta',
    CONCAT('S/ ', FORMAT(v.total, 2)) as 'Total'
FROM tb_ventas v
ORDER BY v.fecha_venta DESC;

-- Mostrar detalles de viajes (actualizado con JOIN por ID)
SELECT 'DETALLES DE VIAJES' as CONSULTA;
SELECT 
    dv.id as 'ID',
    co.nombre as 'Origen',
    cd.nombre as 'Destino',
    DATE_FORMAT(dv.fecha_viaje, '%d/%m/%Y') as 'Fecha Ida',
    CASE 
        WHEN dv.fecha_retorno IS NOT NULL 
        THEN DATE_FORMAT(dv.fecha_retorno, '%d/%m/%Y')
        ELSE 'Solo ida'
    END as 'Fecha Vuelta',
    dv.cantidad as 'Pasajeros',
    CONCAT('S/ ', FORMAT(dv.sub_total, 2)) as 'Subtotal',
    dv.nombre_comprador as 'Comprador'
FROM detalle_venta dv
JOIN ciudades co ON dv.ciudad_origen_id = co.id
JOIN ciudades cd ON dv.ciudad_destino_id = cd.id
ORDER BY dv.fecha_viaje;

-- Rutas más populares (actualizado)
SELECT 'RUTAS MÁS POPULARES' as CONSULTA;
SELECT 
    CONCAT(co.nombre, ' → ', cd.nombre) as 'Ruta',
    COUNT(*) as 'Cantidad Viajes',
    SUM(dv.cantidad) as 'Total Pasajeros',
    CONCAT('S/ ', FORMAT(AVG(dv.sub_total), 2)) as 'Precio Promedio'
FROM detalle_venta dv
JOIN ciudades co ON dv.ciudad_origen_id = co.id
JOIN ciudades cd ON dv.ciudad_destino_id = cd.id
GROUP BY dv.ciudad_origen_id, dv.ciudad_destino_id
ORDER BY COUNT(*) DESC;

-- =========================================
-- VISTAS ÚTILES ACTUALIZADAS
-- =========================================

-- Vista de resumen de ventas
CREATE VIEW vista_resumen_ventas AS
SELECT 
    v.id as venta_id,
    v.nombre_comprador,
    v.fecha_venta,
    v.total,
    COUNT(dv.id) as cantidad_viajes,
    SUM(dv.cantidad) as total_pasajeros
FROM tb_ventas v
LEFT JOIN detalle_venta dv ON v.id = dv.venta_id
GROUP BY v.id;

-- Vista de próximos viajes (actualizada)
CREATE VIEW vista_proximos_viajes AS
SELECT 
    dv.id,
    co.nombre as ciudad_origen,
    cd.nombre as ciudad_destino,
    dv.fecha_viaje,
    dv.fecha_retorno,
    dv.cantidad,
    dv.nombre_comprador,
    DATEDIFF(dv.fecha_viaje, CURDATE()) as dias_restantes
FROM detalle_venta dv
JOIN ciudades co ON dv.ciudad_origen_id = co.id
JOIN ciudades cd ON dv.ciudad_destino_id = cd.id
WHERE dv.fecha_viaje >= CURDATE()
ORDER BY dv.fecha_viaje;

-- =========================================
-- PROCEDIMIENTO PARA MIGRAR DATOS EXISTENTES
-- =========================================

DELIMITER //

CREATE PROCEDURE MigrarCiudadesExistentes()
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE old_codigo VARCHAR(10);
    DECLARE old_nombre VARCHAR(100);
    DECLARE new_id INT;
    
    -- Cursor para ciudades existentes
    DECLARE ciudad_cursor CURSOR FOR 
        SELECT codigo_postal, nombre FROM tb_ciudad;
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    -- Solo si existe la tabla antigua
    IF (SELECT COUNT(*) FROM information_schema.tables 
        WHERE table_schema = 'vuelos' AND table_name = 'tb_ciudad') > 0 THEN
        
        OPEN ciudad_cursor;
        
        read_loop: LOOP
            FETCH ciudad_cursor INTO old_codigo, old_nombre;
            IF done THEN
                LEAVE read_loop;
            END IF;
            
            -- Insertar en nueva estructura si no existe
            INSERT IGNORE INTO ciudades (codigo_postal, nombre) 
            VALUES (old_codigo, old_nombre);
            
        END LOOP;
        
        CLOSE ciudad_cursor;
        
        SELECT 'Migración de ciudades completada' as RESULTADO;
    END IF;
    
END //

DELIMITER ;

-- =========================================
-- ESTADÍSTICAS DEL SISTEMA
-- =========================================

SELECT 'ESTADÍSTICAS DEL SISTEMA ACTUALIZADO' as CONSULTA;
SELECT 
    (SELECT COUNT(*) FROM ciudades) as 'Total Ciudades',
    (SELECT COUNT(*) FROM tb_ventas) as 'Total Ventas',
    (SELECT COUNT(*) FROM detalle_venta) as 'Total Viajes',
    (SELECT CONCAT('S/ ', FORMAT(SUM(total), 2)) FROM tb_ventas) as 'Ingresos Totales',
    (SELECT COUNT(DISTINCT nombre_comprador) FROM tb_ventas) as 'Clientes Únicos';

-- =========================================
-- VERIFICACIÓN DE CONSISTENCIA
-- =========================================

SELECT 'VERIFICACIÓN DE INTEGRIDAD' as CONSULTA;
SELECT 
    'Ciudades sin detalles de venta' as Tipo,
    COUNT(*) as Cantidad
FROM ciudades c
LEFT JOIN detalle_venta dv1 ON c.id = dv1.ciudad_origen_id
LEFT JOIN detalle_venta dv2 ON c.id = dv2.ciudad_destino_id
WHERE dv1.id IS NULL AND dv2.id IS NULL

UNION ALL

SELECT 
    'Detalles sin venta asociada' as Tipo,
    COUNT(*) as Cantidad
FROM detalle_venta dv
LEFT JOIN tb_ventas v ON dv.venta_id = v.id
WHERE v.id IS NULL;

-- =========================================
-- FIN DEL SCRIPT ACTUALIZADO
-- =========================================

SELECT 'BASE DE DATOS VIVAAIR ACTUALIZADA EXITOSAMENTE' as RESULTADO;
SELECT 'Estructura compatible con entidades JPA - Spring Boot' as COMPATIBILIDAD;
SELECT 'Proyecto desarrollado por Diego Peregrino - CIBERTEC 2025' as AUTOR;