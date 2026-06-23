package vistacontrol;

import utils.Errores;
import utils.Lector;

/**
 * @author sanchedev
 * @author plasencia
 */
public class Index {

    private static void salir() {
        System.out.println("Buena suerte!");
    }

    public static void mostrarMenu() {
        System.out.println("*** MENU PRINCIPAL ***");
        System.out.println("1. Miembros");
        System.out.println("2. Iglesia");
        System.out.println("3. Depositos");
        System.out.println("4. Informes (Reportes)");
        System.out.println("5. Salir");
    }

    public static void inicio() {
        // IndexIglesias.cargar();
        // IndexMiembros.cargar();
        // IndexDepositos.cargar();

        int opcion;
        do {
            mostrarMenu();
            opcion = Lector.preguntarEntero("Elige una opcion [1-5]");
            System.out.println("");

            switch (opcion) {
                case 1 ->
                    IndexMiembros.inicio();
                case 2 ->
                    IndexIglesias.inicio();
                case 3 ->
                    IndexDepositos.inicio();
                case 4 ->
                    IndexInformes.inicio();
                case 5 ->
                    salir();
                default ->
                    Errores.deRango();
            }
        } while (opcion != 5);
        Lector.cerrar();
    }

    public static void main(String[] args) {
        inicio();
    }
}
