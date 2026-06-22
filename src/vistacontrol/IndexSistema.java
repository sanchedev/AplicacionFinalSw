package vistacontrol;

import repository.Depositos;
import repository.Gastos;
import repository.Iglesias;
import repository.Miembros;
import utils.Errores;
import utils.Lector;
import utils.Sesion;

/**
 * Menu principal del sistema despues de iniciar sesion.
 * Redirige a los modulos segun el rol del usuario.
 */
public class IndexSistema {

    private static void miembros() {
        if (Iglesias.verCantidad() < 1) {
            System.out.println("!> Agregue iglesias antes de gestionar miembros");
        } else {
            IndexMiembros.inicio();
        }
    }

    private static void iglesia() {
        IndexIglesia.inicio();
    }

    private static void informes() {
        if (Iglesias.verCantidad() < 1) {
            System.out.println("!> Agregue iglesias antes de ver informes");
        } else {
            IndexInformes.inicio();
        }
    }

    private static void gastos() {
        if (Iglesias.verCantidad() < 1) {
            System.out.println("!> Agregue iglesias antes de gestionar gastos");
        } else {
            IndexGastos.inicio();
        }
    }

    private static void cerrarSesion() {
        System.out.println("*** CERRAR SESION ***");
        if (Lector.confirmar("Desea cerrar sesion?")) {
            Sesion.salir();
            System.out.println("Sesion cerrada correctamente!");
        }
    }

    private static void volver() {
        System.out.println("Buena suerte!\n");
    }

    public static void mostrarMenu() {
        System.out.println("");
        System.out.println("*** SISTEMA DE TESORERIA IASD ***");
        System.out.println("1. Miembros (" + Miembros.verCantidad() + ")");
        System.out.println("2. Iglesia (" + Iglesias.verCantidad() + ")");
        System.out.println("3. Informes (" + Depositos.verCantidad() + ")");
        System.out.println("4. Gastos (" + Gastos.verCantidad() + ")");
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
                case 1 ->
                    miembros();
                case 2 ->
                    iglesia();
                case 3 ->
                    informes();
                case 4 ->
                    gastos();
                case 5 ->
                    cerrarSesion();
                case 6 ->
                    volver();
                default ->
                    Errores.deRango();
            }
        } while (opcion != 6 && Sesion.haAuth());
    }
}
