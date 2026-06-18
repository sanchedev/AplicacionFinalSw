package vistacontrol;

import utils.Errores;
import utils.Lector;
import utils.Sesion;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author sanchedev
 */
public class IndexTesoreria {
    private static void usuarios() {
        IndexUsuarios.inicio();
    }
    
    private static void feligreses() {
        IndexPersonas.inicio();
    }
    
    private static void iglesias() {
        IndexIglesias.inicio();
    }
    
    private static void depositos() {
        IndexDepositos.inicio();
    }
    
    private static void cerrarSesion() {
        System.out.println("*** CERRAR SESION ***");
        if (Lector.confirmar("¿Desea cerrar sesion?")) {
            Sesion.salirUsuario();
            System.out.println("Sesion Cerrada correctamente!");
        }
    }
    
    private static void volver() {
        System.out.println("Buena suerte!\n");
    }
    
    public static void mostrarMenu() {
        System.out.println("");
        System.out.println("*** TESORERIA ***");
        System.out.println("1. Manejar Usuarios");
        System.out.println("2. Manejar Feligreses");
        System.out.println("3. Manejar Iglesias");
        System.out.println("4. Manejar Depositos");
        System.out.println("5. Cerrar Sesion");
        System.out.println("6. Volver");
    }
    
    public static void inicio() {
        int opcion;
        do {
            mostrarMenu();
            opcion = Lector.preguntarEntero("Elige una opcion [1-6]");
            System.out.println("");

            switch (opcion) {
                case 1 -> usuarios();
                case 2 -> feligreses();
                case 3 -> iglesias();
                case 4 -> depositos();
                case 5 -> cerrarSesion();
                case 6 -> volver();
                default -> Errores.deRango();
            }
        } while (opcion != 6 && Sesion.haAuthUsuario());
    }

}
