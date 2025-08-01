import com.olf.openjvs.*;
import com.olf.openjvs.enums.*;

public class MergeTables implements IScript {
    public void execute(IContainerContext context) throws OException {
        // Primero declaramos las tablas que vamos a utilizar mas adelante -> TABLA A Y TABLA B
        Table tableA = Util.NULL_TABLE;
        Table tableB = Util.NULL_TABLE;
        
        // Crearemos una tabla de resultados para las columnas seleccionadas
        Table result = Table.tableNew("Merged A and B");

        // Agreganmos las columnas que nosotros esperamos que seria la estructura de nuestro OUTPUT
        result.addCol("customer_id", COL_TYPE_ENUM.COL_INT);
        result.addCol("first_name", COL_TYPE_ENUM.COL_STRING);
        result.addCol("last_name", COL_TYPE_ENUM.COL_STRING);
        result.addCol("phone_number", COL_TYPE_ENUM.COL_STRING);
        result.addCol("registration_date", COL_TYPE_ENUM.COL_DATE);
        result.addCol("last_purchase_date", COL_TYPE_ENUM.COL_DATE);
        result.addCol("total_spent", COL_TYPE_ENUM.COL_DOUBLE);
        result.addCol("total_orders", COL_TYPE_ENUM.COL_INT);
        result.addCol("loyalty_status", COL_TYPE_ENUM.COL_STRING);
        result.addCol("account_status", COL_TYPE_ENUM.COL_STRING);
        
        // Intentamos realizar nuestros selects ya sea de nuestra tabla A como de nuestra tabla B con un "try"
        // Y si no fue posible realizar nuestro select pues ya tenemos nuestro catch que seria una excepcion que ocurre cuando no se logro
        // con un print del mensaje, throw e

        try {

            // Primero seleccionamos todas las rows que son validas dentro de nuestra tabla A
            // Al final utilizamos un where donde traemos todas las columnas 'where' customer_id > 0
            // Esto con la finalidad de excluir registros de invalidos si es que existen
            result.select(tableA, "customer_id, first_name, last_name, phone_number, registration_date", "customer_id GT 0"); 
            

            // Seleccionamos ahora los datos de las columnas de la tabla B, juntando la informacion previamente seleccionada
            // De la tabla A y uniendola a traves de la llave del customer_id, juntando asi efectivamente las columnas por la llave
            result.select(tableB, "last_purchase_date, total_spent, total_orders, loyalty_status, account_status", "customer_id EQ $customer_id");
            
            // Aqui lo que hacemos es manejar los casos en donde las tablas tienen diferentes cuentas de las rows
            int rowsA = tableA.getNumRows();
            int rowsB = tableB.getNumRows();
            int maxRows = Math.min(rowsA, rowsB); // Aqui mismo utilizamos la cuenta mas pequeña para evitar caer dentro de errores de index
            
            // Dentro de este for loop procesaremos solamente el menor numero de filas para evitar tener mismatches
            // Si el numero de rows del resultado es mayor al numero maximo de las rows entonces
            // Podemos cortar los resultados si es necesario
            if (result.getNumRows() > maxRows) {
                for (int i = result.getNumRows() - 1; i >= maxRows; i--) {
                    result.delRow(i);
                }
            }
            
            // Esto no fue requerido pero lo agregue como un pequeño extra, 
            // agregue validacion para los nombres de la columna 
            validateColumnNames(result);

            /*
            * Si es que deseamos ver como termino la tabla de resultados podemos realizar varias cosas:
            *
            * - viewTable() se muestra como dentro de una tipo ventana de GUI
            * - OConsole.oprint("Tabla creada con " + result.getNumRows() + " filas y " + result.getNumCols() + " columnas");
            * - result.printTableDumpToTtextFile("C:\\merged_results.txt");
            * 
            */
            result.viewTable(); 

            
        } catch (OException e) {
            OConsole.oprint("Error cuando intentaste juntar las tablas: " + e.getMessage());
            throw e;
        } finally {
            // Podemos realizar clean up si es necesario ( depende obviamente mucho del contexto y si es que OPENJVS maneja el garbage collector)
        }
    }
    
    /**
     * Valida que los nombres de la columna reflejen su nombre de origen de tabla
     */
    private void validateColumnNames(Table result) throws OException {
        // Me aseguro que los indices de las columnas esten dentro de rangos validos
        int numCols = result.getNumCols();
        if (numCols < 10) {
            OConsole.oprint(".....Menos columnas de lo esperado");
        }
        
        // Validar que tenemos las columnas esenciales
        String[] requiredColumns = {"customer_id", "first_name", "last_name", 
                                   "phone_number", "registration_date", "last_purchase_date", 
                                   "total_spent", "total_orders", "loyalty_status", "account_status"};
        
        for (String colName : requiredColumns) {
            if (result.getColNum(colName) < 0) {
                throw new OException("Falta columna requerida: " + colName);
            }
        }
    }
} 