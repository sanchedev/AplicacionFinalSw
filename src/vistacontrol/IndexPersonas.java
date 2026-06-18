package vistacontrol;

import repository.Iglesias;
import repository.Personas;
import utils.Errores;
import utils.Lector;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author sanchedev
 */
public class IndexPersonas {

    private static void listar() {
        System.out.println("*** LISTAR FELIGRESES ***");

        int cantidad = Personas.verCantidad();

        if (cantidad == 0) {
            System.out.println("!> La lista de feligreses esta vacia");
            return;
        }

        for (int i = 0; i < cantidad; i++) {
            Personas.mostrarPersona(i);
            System.out.println(" - - - - - - - - - - ");
        }
        System.out.printf("Se mostraron %d feligres(es)\n", cantidad);
    }

    private static void registrar() {
        System.out.println("*** REGISTRAR FELIGRES ***");

        String dni = Lector.preguntar("DNI");
        String nombre = Lector.preguntar("Nombre Completo");

        Iglesias.mostrarGuiaIglesias();
        int codigoIglesia = Lector.preguntarEntero("Codigo de Iglesia", -1);

        if (Iglesias.buscarIglesia(codigoIglesia) == -1) {
            System.out.println("No se encontro la iglesia, se agregara como invitado en su lugar");
            codigoIglesia = -1;
        }

        Personas.crearPersona(dni, nombre, codigoIglesia);

        System.out.println("Iglesia registrada exitosamente!");
    }

    private static void ver() {
        System.out.println("*** VER FELIGRES ***");

        String dni = Lector.preguntar("DNI");

        int indice = Personas.buscarPersona(dni);

        if (indice == -1) {
            System.out.println("!> El feligres con DNI `" + dni + "` no existe");
        } else {
            Personas.mostrarDetallePersona(indice);
        }
    }

    private static void editar() {
        System.out.println("*** EDITAR FELIGRES ***");

        String dni = Lector.preguntar("DNI");

        int indice = Personas.buscarPersona(dni);

        if (indice == -1) {
            System.out.println("!> El feligres con DNI `" + dni + "` no existe");
        } else {
            String nombre = Lector.preguntar("Nombre Completo", Personas.verNombre(indice));

            Iglesias.mostrarGuiaIglesias();
            int codigoIglesia = Lector.preguntarEntero("Codigo de Iglesia", Personas.verCodigoIglesia(indice));

            if (Iglesias.buscarIglesia(codigoIglesia) == -1) {
                System.out.println("No se encontro la iglesia, se agregara como invitado en su lugar");
                codigoIglesia = -1;
            }

            Personas.editarPersona(dni, nombre, codigoIglesia);
        }
    }

    private static void volver() {
        System.out.println("Buena suerte!\n");
    }

    public static void mostrarMenu() {
        System.out.println("");
        System.out.println("*** GESTION DE FELIGRESES ***");
        System.out.println("1. Ver Todas Los Feligreses");
        System.out.println("2. Registrar Feligres");
        System.out.println("3. Ver Feligres");
        System.out.println("4. Editar Feligres");
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
                    listar();
                case 2 ->
                    registrar();
                case 3 ->
                    ver();
                case 4 ->
                    editar();
                case 5 ->
                    volver();
                default ->
                    Errores.deRango();
            }
        } while (opcion != 5);
    }

}
