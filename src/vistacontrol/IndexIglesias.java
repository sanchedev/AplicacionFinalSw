package vistacontrol;

import utils.Errores;
import utils.Lector;

/**
 * @author sanchedev
 * @author plasencia
 */
public class IndexIglesias {

    private static final int[] codigos = new int[1000];
    private static final String[] nombres = new String[1000];
    private static final String[] direcciones = new String[1000];
    private static final int[] aforos = new int[1000];
    public static int cantidad = 0;

    public static String verNombre(int codigo) {
        int indice = buscarPorCodigo(codigo);
        if (indice == -1) {
            return "";
        }
        return nombres[indice];
    }

    public static String verDireccion(int codigo) {
        int indice = buscarPorCodigo(codigo);
        if (indice == -1) {
            return "";
        }
        return direcciones[indice];
    }

    public static void verIglesias() {
        if (cantidad == 0) {
            System.out.println("!> No hay iglesias");
        } else {
            for (int i = 0; i < cantidad; i++) {
                System.out.println("- [" + i + "]: " + nombres[i]);
            }
        }
    }

    public static int buscarPorCodigo(int codigo) {
        int indice = -1;
        for (int i = 0; i < cantidad; i++) {
            if (codigos[i] == codigo) {
                indice = i;
                break;
            }
        }
        return indice;
    }

    private static void agregar() {
        System.out.println("*** Agregar Iglesia ***");
        int codigo = 0;
        if (cantidad > 0) {
            codigo = codigos[cantidad - 1] + 1;
        }
        codigos[cantidad] = codigo;
        nombres[cantidad] = Lector.preguntar("Nombre de la Iglesia");
        direcciones[cantidad] = Lector.preguntar("Direccion");
        aforos[cantidad] = Lector.preguntarEntero("Aforo");
        cantidad++;
        System.out.println("Iglesia registrado exitosamente!");
    }

    private static void eliminar() {
        System.out.println("*** Eliminar Iglesia ***");
        int codigo = Lector.preguntarEntero("Codigo de Iglesia a eliminar");
        int indice = buscarPorCodigo(codigo);
        if (indice == -1) {
            Errores.personalizado("El codigo no esta registrado");
            return;
        }
        System.out.println("Iglesia eliminada exitosamente!");
        cantidad--;
        for (int i = indice; i < cantidad; i++) {
            codigos[i] = codigos[i + 1];
            nombres[i] = nombres[i + 1];
            direcciones[i] = direcciones[i + 1];
            aforos[i] = aforos[i + 1];
        }
        System.out.println("Iglesia eliminada correctamente");
    }

    private static void editar() {
        System.out.println("*** Editar Iglesia ***");
        int codigo = Lector.preguntarEntero("Codigo de Iglesia a eliminar");
        int indice = buscarPorCodigo(codigo);
        if (indice == -1) {
            Errores.personalizado("El codigo no esta registrado");
            return;
        }
        nombres[indice] = Lector.preguntar("Nombre de la Iglesia", nombres[indice]);
        direcciones[indice] = Lector.preguntar("Direccion", direcciones[indice]);
        aforos[indice] = Lector.preguntarEntero("Aforo", aforos[indice]);
        System.out.println("Iglesia editada exitosamente!");
    }

    private static void listar() {
        System.out.println("*** Listar Iglesias ***");
        if (cantidad == 0) {
            System.out.println("!> No hay iglesias");
            return;
        }
        for (int i = 0; i < cantidad; i++) {
            System.out.println("Codigo:     " + codigos[i]);
            System.out.println("Nombre:     " + nombres[i]);
            System.out.println("Direccion:  " + direcciones[i]);
            System.out.println("Aforo:      " + aforos[i]);
            System.out.println("- - - - - - - - - - -");
        }
        System.out.println("Se mostraron " + cantidad + " iglesias(s)");
    }

    private static void buscar() {
        System.out.println("*** Buscar Iglesia ***");
        String nombre = Lector.preguntar("Nombre");
        int indice = -1;
        for (int i = 0; i < cantidad; i++) {
            if (nombres[i].contains(nombre)) {
                indice = i;
                break;
            }
        }
        if (indice == -1) {
            System.out.println("!> No se encontro ninguna iglesia con el nombre \"" + nombre + "\"");
            return;
        }
        System.out.println("- - -");
        System.out.println("Codigo:     " + codigos[indice]);
        System.out.println("Nombre:     " + nombres[indice]);
        System.out.println("Direccion:  " + direcciones[indice]);
        System.out.println("Aforo:      " + aforos[indice]);
    }

    private static void volver() {
        System.out.println("Buena suerte!\n");
    }

    public static void mostrarMenu() {
        System.out.println("");
        System.out.println("*** MENU IGLESIAS ***");
        System.out.println("1. Agregar");
        System.out.println("2. Eliminar");
        System.out.println("3. Editar");
        System.out.println("4. Listar");
        System.out.println("5. Buscar");
        System.out.println("6. Volver");
    }

    public static void inicio() {
        int opcion;
        do {
            mostrarMenu();
            opcion = Lector.preguntarEntero("Elige una opcion [1-6]");
            System.out.println("");

            switch (opcion) {
                case 1 ->
                    agregar();
                case 2 ->
                    eliminar();
                case 3 ->
                    editar();
                case 4 ->
                    listar();
                case 5 ->
                    buscar();
                case 6 ->
                    volver();
                default ->
                    Errores.deRango();
            }
        } while (opcion != 6);
    }
}
