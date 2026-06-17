package vistacontrol;

import repository.Depositos;
import repository.Iglesias;
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
public class IndexDepositos {
    private static void listar() {
        System.out.println("*** LISTAR DEPOSITOS ***");
        
        int cantidad = Depositos.verCantidad();
        
        if (cantidad == 0) {
            System.out.println("!> La lista de depositos esta vacia");
            return;
        }
        
        for (int i = cantidad - 1; i >= 0; i--) {
            Depositos.mostrarDeposito(i);
            System.out.println(" - - - - - - - - - - ");
        }
        System.out.printf("Se mostraron %d deposito(s)\n", cantidad);
    }
    
    private static void ver() {
        System.out.println("*** VER DEPOSITO ***");
        
        int codigo = Lector.preguntarEntero("Codigo del deposito");
        
        int indice = Depositos.buscarDeposito(codigo);
        
        if (indice == -1) {
            System.out.println("!> El deposito con codigo `"+codigo+"` no existe");
        } else {
            Depositos.mostrarDetalleDeposito(indice);
            if (Lector.confirmar("¿Desea imprimir este recibo?")) {
                Depositos.imprimirDeposito(indice);
            }
        }
    }
    
    private static void volver() {
        System.out.println("Buena suerte!\n");
    }
    
    public static void mostrarMenu() {
        System.out.println("");
        System.out.println("*** GESTION DE DEPOSITOS ***");
        System.out.println("1. Ver Todos Los DEPOSITOS");
        System.out.println("2. Ver Un Deposito");
        System.out.println("3. Volver");
    }
    
    public static void inicio() {
        int opcion;
        do {
            mostrarMenu();
            opcion = Lector.preguntarEntero("Elige una opcion [1-3]");
            System.out.println("");

            switch (opcion) {
                case 1 -> listar();
                case 2 -> ver();
                case 3 -> volver();
                default -> Errores.deRango();
            }
        } while (opcion != 3);
    }

}
