package vistacontrol;

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
public class IndexIglesias {
    private static void listar() {
        System.out.println("*** LISTAR IGLESIAS ***");
    }
    
    private static void registrar() {
        System.out.println("*** REGISTRAR IGLESIA ***");
    }
    
    private static void ver() {
        System.out.println("*** VER IGLESIA ***");
    }
    
    private static void buscar() {
        System.out.println("*** BUSCAR IGLESIA ***");
    }
    
    private static void editar() {
        System.out.println("*** EDITAR IGLESIA ***");
    }
    
    private static void borrar() {
        System.out.println("*** BORRAR IGLESIA ***");
    }
    
    
    private static void volver() {
        System.out.println("Buena suerte!\n");
    }
    
    public static void mostrarMenu() {
        System.out.println("");
        System.out.println("*** PANEL DE USUARIO ***");
        System.out.println("1. Ver Todas Los Iglesias");
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
                case 1 -> listar();
                case 2 -> registrar();
                case 3 -> ver();
                case 4 -> buscar();
                case 5 -> editar();
                case 6 -> borrar();
                case 7 -> volver();
                default -> Errores.deRango();
            }
        } while (opcion != 7);
    }

}
