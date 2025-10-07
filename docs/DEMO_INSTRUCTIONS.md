# 📋 Instrucciones de Demostración

## 🗃️ **Configuración de Base de Datos**

1. **Importar datos de demostración:**
```bash
mysql -u root -p < database/vivaair_demo.sql
```

2. **Verificar datos:**
```bash
mysql -u root -p vuelos -e "SELECT COUNT(*) as ciudades FROM tb_ciudad; SELECT COUNT(*) as ventas FROM tb_ventas;"
```

## 🔌 **Testing con Postman**

1. **Importar colección:**
   - Abrir Postman
   - File → Import
   - Seleccionar `postman/VivaAir_API_Collection.postman_collection.json`

2. **Configurar variables:**
   - URL base: `http://localhost:8100`
   - Asegurarse que la aplicación esté corriendo

3. **Ejecutar tests:**
   - Carpeta "🎫 Ejemplos de Datos" → Run Collection
   - Carpeta "🏙️ Gestión de Ciudades" → Probar CRUD
   - Carpeta "🧪 Tests de Validación" → Verificar errores

## 🎯 **Secuencia de Demostración**

1. ✅ Mostrar aplicación web funcionando
2. ✅ Demostrar API REST con Postman
3. ✅ Mostrar base de datos con datos reales
4. ✅ Explicar arquitectura del código