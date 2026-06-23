package vistacontrol;

import utils.Errores;
import utils.Lector;
import utils.Validaciones;

/**
 * @author sanchedev
 */
public class IndexMiembros {

    private static final String[] dnis = new String[1000];
    private static final String[] nombresCompletos = new String[1000];
    private static final int[] codigoIglesias = new int[1000];
    public static int cantidad = 0;

    public static int buscarPorDNI(String dni) {
        int indice = -1;
        for (int i = 0; i < cantidad; i++) {
            if (dnis[i].equals(dni)) {
                indice = i;
                break;
            }
        }
        return indice;
    }

    public static String verNombre(String dni) {
        int indice = buscarPorDNI(dni);
        if (indice == -1)
            return "";
        return nombresCompletos[indice];
    }

    private static void agregar() {
        System.out.println("*** Agregar Miembro ***");
        if (IndexIglesias.cantidad == 0) {
            Errores.personalizado("No hay Iglesias registradas");
            return;
        }
        String dni = Lector.preguntar("DNI");
        if (!Validaciones.esDNIValido(dni)) {
            Errores.personalizado("\"" + dni + "\" no es un DNI valido");
        }
        if (buscarPorDNI(dni) != -1) {
            Errores.personalizado("El DNI ya se encuentra registrado");
            return;
        }
        String nombre = Lector.preguntar("Nombre Completo");
        IndexIglesias.verIglesias();
        int codigoIglesia = Lector.preguntarEntero("Codigo de Iglesia");
        if (IndexIglesias.buscarPorCodigo(codigoIglesia) == -1) {
            Errores.personalizado("Iglesia con codigo \"" + codigoIglesia + "\" no existe");
            return;
        }
        dnis[cantidad] = dni;
        nombresCompletos[cantidad] = nombre;
        codigoIglesias[cantidad] = codigoIglesia;
        cantidad++;
        System.out.println("Miembro agregado con exito!");

    }

    private static void eliminar() {
        System.out.println("*** Eliminar Miembro ***");
        String dni = Lector.preguntar("DNI");
        int indice = buscarPorDNI(dni);
        if (indice == -1) {
            Errores.personalizado("El Miembro con ese DNI no existe");
            return;
        }
        cantidad--;
        for (int i = indice; i < cantidad; i++) {
            dnis[i] = dnis[i + 1];
            nombresCompletos[i] = nombresCompletos[i + 1];
            codigoIglesias[i] = codigoIglesias[i + 1];
        }
    }

    private static void editar() {
        System.out.println("*** Editar Miembro ***");
        String dni = Lector.preguntar("Igrese el DNI a editar");
        int indice = buscarPorDNI(dni);
        if (indice == -1) {
            Errores.personalizado("El DNI no esta registrado");
            return;
        }
        String nombre = Lector.preguntar("Nombre", nombresCompletos[indice]);
        IndexIglesias.verIglesias();
        int codigo = Lector.preguntarEntero("Codigo de Iglesia", codigoIglesias[indice]);
        if (IndexIglesias.buscarPorCodigo(codigo) == -1) {
            Errores.personalizado("Iglesia con codigo \"" + codigo + "\" no existe");
            return;
        }
        nombresCompletos[indice] = nombre;
        codigoIglesias[indice] = codigo;
        System.out.println("Miembro editado con Exito");

    }

    private static void listar() {
        System.out.println("*** Listar Miembros ***");
        if (cantidad == 0) {
            System.out.println("!> No hay miembros");
            return;
        }
        for (int i = 0; i < cantidad; i++) {
            System.out.println("DNI:     " + dnis[i]);
            System.out.println("Nombre:  " + nombresCompletos[i]);
            System.out.println("Iglesia: " + IndexIglesias.verNombre(codigoIglesias[i]));
            System.out.println("- - - - - - - - - - -");
        }
        System.out.println("Se mostraron " + cantidad + " miembro(s)");
    }

    private static void buscar() {
        System.out.println("*** Buscar Miembro ***");
        String nombre = Lector.preguntar("Nombre");
        int indice = -1;
        for (int i = 0; i < cantidad; i++) {
            if (nombresCompletos[i].contains(nombre)) {
                indice = i;
                break;
            }
        }
        if (indice == -1) {
            System.out.println("!> No se encontro ningun miembro con el nombre \"" + nombre + "\"");
            return;
        }
        System.out.println("- - -");
        System.out.println("DNI:     " + dnis[indice]);
        System.out.println("Nombre:  " + nombresCompletos[indice]);
        System.out.println("Iglesia: " + IndexIglesias.verNombre(codigoIglesias[indice]));
    }

    private static void volver() {
        System.out.println("Buena suerte!\n");
    }

    public static void mostrarMenu() {
        System.out.println("");
        System.out.println("*** MENU MIEMBROS ***");
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
