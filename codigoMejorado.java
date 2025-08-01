import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class codigoMejorado {
    
    /**
     * Metodo principal que combina dos JTable objects en una nueva tabla unificada
     * Cumple exactamente con los requisitos de la entrevista:
     * - Acepta dos JTable objects como parametros
     * - Merge por indice de fila (primera fila de A + primera fila de B)
     * - Retorna un DefaultTableModel con los datos combinados
     */
    public DefaultTableModel mergeTablesRowByRow(JTable tableA, JTable tableB) {
        
        // Validamos que las tablas no sean null para evitar errores
        if (tableA == null || tableB == null) {
            throw new IllegalArgumentException("Las tablas no pueden ser null");
        }
        
        // Definimos las columnas que queremos seleccionar de cada tabla
        // 5 columnas de la Tabla A (informacion basica del cliente)
        String[] columnasTablaA = {"Customer ID", "First Name", "Last Name", "Phone Number", "Registration Date"};
        
        // 5 columnas de la Tabla B (informacion de compras y cuenta)
        String[] columnasTablaB = {"Last Purchase Date", "Total Spent", "Total Orders", "Loyalty Status", "Account Status"};
        
        // Creamos el array de nombres de columnas para la tabla combinada (Tabla C)
        // Aqui los nombres reflejan su origen como pide el requisito
        String[] columnasFinales = new String[10];
        
        // Copiamos las columnas de la tabla A al resultado final
        for (int i = 0; i < columnasTablaA.length; i++) {
            columnasFinales[i] = "A_" + columnasTablaA[i]; // Prefijo para mostrar origen
        }
        
        // Copiamos las columnas de la tabla B al resultado final
        for (int i = 0; i < columnasTablaB.length; i++) {
            columnasFinales[columnasTablaA.length + i] = "B_" + columnasTablaB[i]; // Prefijo para mostrar origen
        }
        
        // Validamos los rangos de indices de las columnas antes de proceder
        // Esto es para cumplir con el requisito de "Validate column index ranges"
        if (tableA.getColumnCount() < 5) {
            throw new IndexOutOfBoundsException("La tabla A debe tener al menos 5 columnas");
        }
        
        if (tableB.getColumnCount() < 5) {
            throw new IndexOutOfBoundsException("La tabla B debe tener al menos 5 columnas");
        }
        
        // Manejamos el caso donde las tablas tienen diferente numero de filas
        // Como dice el requisito: "stop at the shortest"
        int rowsTablaA = tableA.getRowCount();
        int rowsTablaB = tableB.getRowCount();
        int maxRows = Math.min(rowsTablaA, rowsTablaB); // Nos quedamos con la tabla mas corta
        
        // Mostramos informacion sobre las dimensiones para debugging
        System.out.println("Tabla A tiene " + rowsTablaA + " filas");
        System.out.println("Tabla B tiene " + rowsTablaB + " filas");
        System.out.println("Procesaremos " + maxRows + " filas (la menor cantidad)");
        
        // Creamos el DefaultTableModel que vamos a retornar
        DefaultTableModel modeloCombinado = new DefaultTableModel(columnasFinales, 0);
        
        // Ahora realizamos el merge fila por fila usando indices
        // Primera fila de A + Primera fila de B, Segunda fila de A + Segunda fila de B, etc.
        for (int fila = 0; fila < maxRows; fila++) {
            
            // Creamos un array para almacenar los datos de esta fila combinada
            Object[] filaCombinada = new Object[10];
            
            try {
                // Extraemos los datos de las primeras 5 columnas de la tabla A para esta fila
                for (int col = 0; col < 5; col++) {
                    filaCombinada[col] = tableA.getValueAt(fila, col);
                }
                
                // Extraemos los datos de las primeras 5 columnas de la tabla B para esta fila
                for (int col = 0; col < 5; col++) {
                    filaCombinada[5 + col] = tableB.getValueAt(fila, col);
                }
                
                // Agregamos la fila combinada al modelo de resultado
                modeloCombinado.addRow(filaCombinada);
                
            } catch (ArrayIndexOutOfBoundsException e) {
                // Si ocurre un error de indice, mostramos informacion y saltamos esta fila
                System.err.println("Error procesando fila " + fila + ": " + e.getMessage());
                continue; // Continuamos con la siguiente fila
            }
        }
        
        // Mostramos estadisticas del resultado final
        System.out.println("Tabla combinada creada exitosamente:");
        System.out.println("- Filas procesadas: " + modeloCombinado.getRowCount());
        System.out.println("- Columnas totales: " + modeloCombinado.getColumnCount());
        System.out.println("- Estructura: 5 columnas de Tabla A + 5 columnas de Tabla B");
        
        // Retornamos el DefaultTableModel como pide el requisito
        return modeloCombinado;
    }
    
    /**
     * Metodo helper para validar que los indices de las columnas esten en rango valido
     * Esto cumple con el requisito "Validate column index ranges"
     */
    private boolean validarIndicesColumnas(JTable tabla, int numColumnasRequeridas) {
        
        if (tabla == null) {
            return false;
        }
        
        // Verificamos que la tabla tenga al menos el numero de columnas que necesitamos
        if (tabla.getColumnCount() < numColumnasRequeridas) {
            System.err.println("Error: La tabla solo tiene " + tabla.getColumnCount() + 
                             " columnas, pero necesitamos " + numColumnasRequeridas);
            return false;
        }
        
        return true; // Todo esta bien
    }
    
    /**
     * Metodo de ejemplo para mostrar como usar la funcionalidad
     * Esto seria equivalente a un main method para testing
     */
    public void ejemploDeUso() {
        
        // Creamos datos de ejemplo para la Tabla A (informacion basica de clientes)
        String[] columnasA = {"Customer ID", "First Name", "Last Name", "Phone Number", "Registration Date"};
        Object[][] datosA = {
            {1, "Juan", "Perez", "555-1234", "2023-01-15"},
            {2, "Maria", "Garcia", "555-5678", "2023-02-20"},
            {3, "Carlos", "Rodriguez", "555-9012", "2023-03-10"}
        };
        
        // Creamos datos de ejemplo para la Tabla B (informacion de compras)
        String[] columnasB = {"Last Purchase Date", "Total Spent", "Total Orders", "Loyalty Status", "Account Status"};
        Object[][] datosB = {
            {"2024-01-15", 1250.50, 15, "Gold", "Active"},
            {"2024-02-01", 850.75, 8, "Silver", "Active"},
            {"2024-01-30", 2100.25, 25, "Platinum", "Active"}
        };
        
        // Creamos las JTables con los datos de ejemplo
        JTable tablaA = new JTable(new DefaultTableModel(datosA, columnasA));
        JTable tablaB = new JTable(new DefaultTableModel(datosB, columnasB));
        
        // Llamamos a nuestro metodo principal para hacer el merge
        DefaultTableModel resultado = mergeTablesRowByRow(tablaA, tablaB);
        
        // Mostramos el resultado
        System.out.println("\n=== RESULTADO DEL MERGE ===");
        for (int col = 0; col < resultado.getColumnCount(); col++) {
            System.out.print(resultado.getColumnName(col) + "\t");
        }
        System.out.println();
        
        for (int fila = 0; fila < resultado.getRowCount(); fila++) {
            for (int col = 0; col < resultado.getColumnCount(); col++) {
                System.out.print(resultado.getValueAt(fila, col) + "\t\t");
            }
            System.out.println();
        }
    }
} 