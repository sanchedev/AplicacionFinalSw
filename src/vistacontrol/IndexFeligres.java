package vistacontrol;

import repository.Depositos;
import repository.Iglesias;
import repository.Personas;
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
public class IndexFeligres {
    private static void verInformacion() {
        System.out.println("*** MI INFORMACION ***");
        int indice = Personas.buscarPersona(Sesion.verDNI());
        
        if (indice == -1) {
            System.out.println("!> Estas en modo anonimo");
        } else {
            Personas.mostrarDetallePersona(indice);
        }
    }
    
    private static void verDepositos() {
        System.out.println("*** MIS DEPOSITOS ***");
        
        String dni = Sesion.verDNI();
        int indice = Personas.buscarPersona(dni);
        
        if (indice == -1) {
            System.out.println("!> Estas en modo anonimo");
            return;
        }
        
        int[] resultados = Depositos.buscarDepositosPorDni(dni);

        if (resultados.length == 0) {
            System.out.println("!> Aun no has hecho un pago");
            return;
        }

        for (int i = 0; i < resultados.length; i++) {
            Depositos.mostrarDeposito(resultados[i]);
            System.out.println(" - - - - - - - - - - ");
        }
        System.out.printf("Se mostraron %d deposito(s)\n", resultados.length);
    }
    
    private static void depositar() {
        String dni = Sesion.verDNI();
        
        
        int codigoIglesia;
        do {
            if (Sesion.haAuthUsuario()) {
                codigoIglesia = Lector.preguntarEntero("Codigo de Iglesia", Personas.buscarIglesia(dni));
            } else {
                codigoIglesia = Lector.preguntarEntero("Codigo de Iglesia");
            }
        } while (Iglesias.buscarIglesia(codigoIglesia) == -1);
        
        double diezmo = Lector.preguntarDecimal("Diezmo");
        double ofrenda = Lector.preguntarDecimal("Ofrenda");
        
        Depositos.crearDeposito(dni, codigoIglesia, diezmo, ofrenda);
    }
    
    private static void volver() {
        System.out.println("Buena suerte!\n");
    }
    
    public static void mostrarMenu() {
        System.out.println("");
        System.out.println("*** FELIGRESIA ***");
        System.out.println("1. Ver Mi Informacion");
        System.out.println("2. Ver Mis Depositos");
        System.out.println("3. Realizar un Deposito");
        System.out.println("4. Volver");
    }
    
    public static void inicio() {
        int opcion;
        do {
            mostrarMenu();
            opcion = Lector.preguntarEntero("Elige una opcion [1-4]");
            System.out.println("");

            switch (opcion) {
                case 1 -> verInformacion();
                case 2 -> verDepositos();
                case 3 -> depositar();
                case 4 -> volver();
                default -> Errores.deRango();
            }
        } while (opcion != 4);
    }

}
