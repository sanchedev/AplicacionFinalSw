package vistacontrol;

import repository.Gastos;
import repository.Iglesias;
import utils.Errores;
import utils.Exportador;
import utils.Lector;

/**
 * Gestion de gastos: registro y consulta de retiros de dinero.
 */
public class IndexGastos {

    private static void registrar() {
        System.out.println("*** REGISTRAR GASTO ***");

        int codigoIglesia;
        do {
            Iglesias.mostrarGuiaIglesias();
            codigoIglesia = Lector.preguntarEntero("Codigo de Iglesia (de donde se retira) [0-" + (Iglesias.verCantidad() - 1) + "]");
            if (Iglesias.buscarIglesia(codigoIglesia) != -1) {
                break;
            }
            Errores.personalizado("Codigo de iglesia invalido. Intente de nuevo.");
        } while (true);

        String ministerio = Lector.preguntar("Ministerio destino (nombre del ministerio/departamento)");
        double monto = Lector.preguntarDoublePositivo("Monto a retirar");
        String descripcion = Lector.preguntar("Descripcion / Concepto del gasto");

        int indiceGasto = Gastos.crearGasto(codigoIglesia, ministerio, monto, descripcion);

        if (indiceGasto == -1) {
            System.out.println("!> No se pudo registrar el gasto (almacenamiento lleno)");
            return;
        }

        System.out.println("Gasto registrado exitosamente!");
        System.out.println("Codigo: " + Gastos.verCodigo(indiceGasto));
        System.out.println("Monto: S/. " + String.format("%.2f", monto));
    }

    private static void listar() {
        System.out.println("*** LISTAR GASTOS ***");

        int cantidad = Gastos.verCantidad();

        if (cantidad == 0) {
            System.out.println("!> La lista de gastos esta vacia");
            return;
        }

        for (int i = cantidad - 1; i >= 0; i--) {
            Gastos.mostrarGasto(i);
            System.out.println(" - - - - - - - - - - ");
        }
        System.out.printf("Se mostraron %d gasto(s)\n", cantidad);

        String[] encabezados = {"Codigo", "Iglesia", "Ministerio", "Monto", "Descripcion", "Fecha"};
        String[][] datos = new String[cantidad][6];
        for (int i = 0; i < cantidad; i++) {
            int idx = cantidad - 1 - i;
            datos[i][0] = Gastos.verCodigo(idx) + "";
            datos[i][1] = Gastos.verCodigoIglesia(idx) + "";
            datos[i][2] = Gastos.verMinisterio(idx);
            datos[i][3] = "S/. " + String.format("%.2f", Gastos.verMonto(idx));
            datos[i][4] = Gastos.verDescripcion(idx);
            datos[i][5] = Gastos.verFecha(idx);
        }
        Exportador.exportar("Lista de Gastos", encabezados, datos);
    }

    private static void listarPorIglesia() {
        System.out.println("*** LISTAR GASTOS POR IGLESIA ***");

        int codigoIglesia;
        do {
            Iglesias.mostrarGuiaIglesias();
            codigoIglesia = Lector.preguntarEntero("Codigo de Iglesia [0-" + (Iglesias.verCantidad() - 1) + "]");
            if (Iglesias.buscarIglesia(codigoIglesia) != -1) {
                break;
            }
            Errores.personalizado("Codigo de iglesia invalido. Intente de nuevo.");
        } while (true);

        int[] indices = Gastos.buscarGastosPorIglesia(codigoIglesia);

        if (indices.length == 0) {
            System.out.println("!> No hay gastos para esta iglesia");
            return;
        }

        System.out.println();
        Iglesias.mostrarDetalleIglesia(Iglesias.buscarIglesia(codigoIglesia));
        System.out.println();

        for (int i = indices.length - 1; i >= 0; i--) {
            Gastos.mostrarGasto(indices[i]);
            System.out.println(" - - - - - - - - - - ");
        }
        System.out.printf("Se mostraron %d gasto(s)\n", indices.length);

        String nombreIgls = Iglesias.verNombre(Iglesias.buscarIglesia(codigoIglesia));
        String[] encabezados = {"Codigo", "Ministerio", "Monto", "Descripcion", "Fecha"};
        String[][] datos = new String[indices.length][5];
        for (int i = 0; i < indices.length; i++) {
            int idx = indices[indices.length - 1 - i];
            datos[i][0] = Gastos.verCodigo(idx) + "";
            datos[i][1] = Gastos.verMinisterio(idx);
            datos[i][2] = "S/. " + String.format("%.2f", Gastos.verMonto(idx));
            datos[i][3] = Gastos.verDescripcion(idx);
            datos[i][4] = Gastos.verFecha(idx);
        }
        Exportador.exportar("Gastos - " + nombreIgls, encabezados, datos);
    }

    private static void ver() {
        System.out.println("*** VER GASTO ***");

        int codigo = Lector.preguntarEntero("Codigo del gasto");
        int indice = Gastos.buscarGasto(codigo);

        if (indice == -1) {
            System.out.println("!> El gasto con codigo `" + codigo + "` no existe");
        } else {
            Gastos.mostrarGasto(indice);
        }
    }

    private static void volver() {
        System.out.println("Buena suerte!\n");
    }

    public static void mostrarMenu() {
        int cantidad = Gastos.verCantidad();
        System.out.println("");
        System.out.println("*** GESTION DE GASTOS *** (" + cantidad + " gasto(s))");
        System.out.println("1. Registrar Gasto");
        System.out.println("2. Ver Todos los Gastos");
        System.out.println("3. Ver Por Iglesia");
        System.out.println("4. Ver Un Gasto");
        System.out.println("5. Volver");
    }

    public static void inicio() {
        int opcion;
        do {
            mostrarMenu();
            opcion = Lector.preguntarEntero("Elige una opcion [1-5]");
            System.out.println("");

            switch (opcion) {
                case 1 ->
                    registrar();
                case 2 ->
                    listar();
                case 3 ->
                    listarPorIglesia();
                case 4 ->
                    ver();
                case 5 ->
                    volver();
                default ->
                    Errores.deRango();
            }
        } while (opcion != 5);
    }
}
