# codigoTrabajadoEnEntrevista - Script de Juntar Tablas. En nuestro caso TABLA A y TABLA B

## ¿Qué hace este código?

Bueno, este archivo `codigoTrabajadoEnEntrevista.java` es básicamente un script que hice para juntar (merge) dos tablas de datos de clientes usando OpenJVS. La idea es tomar información de clientes que está separada en dos tablas diferentes y combinarla en una sola tabla más completa.

## La idea principal

### ¿Para qué sirve?
El script toma datos de dos tablas que tengo:
- **Tabla A**: Con la información básica del cliente (nombre, teléfono, fecha de registro, etc.)
- **Tabla B**: Con información de compras y estado de cuenta (cuánto ha gastado, cuántas órdenes, etc.)

Y las junta en una sola tabla que tiene toda la información del cliente en un solo lugar.

### ¿Qué columnas tiene la tabla final?

La tabla que sale al final tiene estas columnas:

| Columna | Tipo | ¿Qué es? |
|---------|------|----------|
| `customer_id` | INT | El ID único del cliente |
| `first_name` | STRING | El nombre del cliente |
| `last_name` | STRING | El apellido del cliente |
| `phone_number` | STRING | El número de teléfono |
| `registration_date` | DATE | Cuándo se registró |
| `last_purchase_date` | DATE | Cuándo fue su última compra |
| `total_spent` | DOUBLE | Cuánto ha gastado en total |
| `total_orders` | INT | Cuántas órdenes ha hecho |
| `loyalty_status` | STRING | Su estado en el programa de lealtad |
| `account_status` | STRING | Si su cuenta está activa o no |

## ¿Cómo funciona paso a paso?

### 1. Primero inicializo todo
- Declaro las tablas que voy a usar (`tableA` y `tableB`)
- Creo una tabla nueva donde voy a poner los resultados

### 2. Luego selecciono los datos
- **Primera selección**: Tomo los datos básicos de `tableA` (solo los que tienen `customer_id > 0` para excluir registros inválidos)
- **Segunda selección**: Junto los datos de `tableB` usando el `customer_id` como llave para unir todo

### 3. Valido y limpio los datos
- Manejo el caso donde las tablas tienen diferentes números de filas
- Me aseguro de que todas las columnas que necesito estén ahí
- Si hay filas de más, las elimino para evitar problemas

### 4. Muestro los resultados
- La tabla se muestra en una ventana GUI
- También podría imprimir estadísticas o guardar en archivo si quisiera

## Cosas de seguridad que agregué

### Manejo de errores
- **Try-Catch**: Por si algo sale mal durante la ejecución
- **Validación de columnas**: Me aseguro de que todas las columnas que necesito existan
- **Manejo de filas**: Para evitar errores cuando las tablas tienen diferentes tamaños

### Validaciones que implementé
- Verifico que tenga el número mínimo de columnas
- Valido que los nombres de las columnas requeridas estén ahí
- Controlo que los índices estén dentro de rangos válidos

## ¿Cómo lo uso?

### Lo que necesito
- Tener OpenJVS instalado
- Acceso a las tablas de datos de entrada
- Permisos para ejecutar en el entorno OpenJVS

### ¿Cómo se ejecuta?
El script se ejecuta a través de OpenJVS y automáticamente:
1. Procesa las tablas que le doy
2. Junta los datos
3. Me muestra los resultados
4. Si algo sale mal, me dice qué pasó

## Notas técnicas

- Usa el patrón de script de OpenJVS
- Implementa la interfaz `IScript` que necesita el framework
- Maneja tipos de datos específicos de OpenJVS (`COL_TYPE_ENUM`)
- Incluye cleanup automático de recursos en el bloque `finally`

## ¿Por qué lo hice así?

Bueno, la verdad es que lo hice así porque:
- Quería que fuera fácil de entender y mantener
- Necesitaba que fuera robusto para manejar diferentes tamaños de datos
- Me gusta agregar validaciones extra para evitar problemas