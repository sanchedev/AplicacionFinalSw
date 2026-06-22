package vistacontrol;

import repository.Iglesias;
import utils.Errores;
import utils.Lector;

/**
 * Gestion de iglesias: CRUD completo.
 */
public class IndexIglesia {

    private static void listar() {
        System.out.println("*** LISTAR IGLESIAS ***");

        int cantidad = Iglesias.verCantidad();

        if (cantidad == 0) {
            System.out.println("!> La lista de iglesias esta vacia");
            return;
        }

        for (int i = 0; i < cantidad; i++) {
            Iglesias.mostrarIglesia(i);
            System.out.println(" - - - - - - - - - - ");
        }
        System.out.printf("Se mostraron %d iglesia(s)\n", cantidad);
    }

    private static void registrar() {
        System.out.println("*** REGISTRAR IGLESIA ***");

        String nombre = Lector.preguntar("Nombre");
        String direccion = Lector.preguntar("Direccion");
        int aforo = Lector.preguntarEntero("Aforo (capacidad maxima)");

        Iglesias.agregarIglesia(nombre, direccion, aforo);

        System.out.println("Iglesia registrada exitosamente!");
    }

    private static void ver() {
        System.out.println("*** VER IGLESIA ***");

        int codigo;
        do {
            Iglesias.mostrarGuiaIglesias();
            codigo = Lector.preguntarEntero("Codigo de la Iglesia [0-" + (Iglesias.verCantidad() - 1) + "]");
            if (Iglesias.buscarIglesia(codigo) != -1) {
                break;
            }
            Errores.personalizado("Codigo de iglesia invalido. Intente de nuevo.");
        } while (true);

        Iglesias.mostrarDetalleIglesia(Iglesias.buscarIglesia(codigo));
    }

    private static void buscar() {
        System.out.println("*** BUSCAR IGLESIA ***");

        String nombre = Lector.preguntar("Nombre (o parte del nombre)");
        int[] indices = Iglesias.buscarIglesias(nombre);

        if (indices.length == 0) {
            System.out.println("!> No se encontraron iglesias con \"" + nombre + "\"");
            return;
        }

        for (int i = 0; i < indices.length; i++) {
            Iglesias.mostrarIglesia(indices[i]);
            System.out.println(" - - - - - - - - - - ");
        }
        System.out.printf("Se mostraron %d iglesia(s)\n", indices.length);
    }

    private static void editar() {
        System.out.println("*** EDITAR IGLESIA ***");

        int codigo;
        do {
            Iglesias.mostrarGuiaIglesias();
            codigo = Lector.preguntarEntero("Codigo de la Iglesia [0-" + (Iglesias.verCantidad() - 1) + "]");
            if (Iglesias.buscarIglesia(codigo) != -1) {
                break;
            }
            Errores.personalizado("Codigo de iglesia invalido. Intente de nuevo.");
        } while (true);

        int indice = Iglesias.buscarIglesia(codigo);
        String nombre = Lector.preguntar("Nombre", Iglesias.verNombre(indice));
        String direccion = Lector.preguntar("Direccion", Iglesias.verDireccion(indice));
        int aforo = Lector.preguntarEntero("Aforo", Iglesias.verAforo(indice));

        Iglesias.editarIglesia(codigo, nombre, direccion, aforo);
        System.out.println("Iglesia editada exitosamente!");
    }

    private static void borrar() {
        System.out.println("*** BORRAR IGLESIA ***");

        int codigo;
        do {
            Iglesias.mostrarGuiaIglesias();
            codigo = Lector.preguntarEntero("Codigo de la Iglesia [0-" + (Iglesias.verCantidad() - 1) + "]");
            if (Iglesias.buscarIglesia(codigo) != -1) {
                break;
            }
            Errores.personalizado("Codigo de iglesia invalido. Intente de nuevo.");
        } while (true);

        int indice = Iglesias.buscarIglesia(codigo);
        Iglesias.mostrarDetalleIglesia(indice);

        if (Lector.confirmar("Desea eliminar iglesia? (sus miembros seran desasignados)")) {
            Iglesias.borrarIglesia(codigo);
            System.out.println("Iglesia eliminada exitosamente!");
        }
    }

    private static void volver() {
        System.out.println("Buena suerte!\n");
    }

    public static void mostrarMenu() {
        int cantidad = Iglesias.verCantidad();
        System.out.println("");
        System.out.println("*** GESTION DE IGLESIAS *** (" + cantidad + " iglesia(s))");
        System.out.println("1. Ver Todas las Iglesias");
        System.out.println("2. Registrar Iglesia");
        System.out.println("3. Ver Iglesia");
        System.out.println("4. Buscar Iglesias");
        System.out.println("5. Editar Iglesia");
        System.out.println("6. Borrar Iglesia");
        System.out.println("7. Volver");
    }

    public static void inicio() {
        int opcion;
        do {
            mostrarMenu();
            opcion = Lector.preguntarEntero("Elige una opcion [1-7]");
            System.out.println("");

            switch (opcion) {
                case 1 ->
                    listar();
                case 2 ->
                    registrar();
                case 3 ->
                    ver();
                case 4 ->
                    buscar();
                case 5 ->
                    editar();
                case 6 ->
                    borrar();
                case 7 ->
                    volver();
                default ->
                    Errores.deRango();
            }
        } while (opcion != 7);
    }
}
