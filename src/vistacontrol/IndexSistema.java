package vistacontrol;

import utils.Errores;
import utils.Lector;

/**
 * @author sanchedev
 */
public class IndexSistema {

    public static void mostrarMenuPrincipal() {
        System.out.println("*** MENU PRINCIPAL ***");
        System.out.println("Sesion: " + IndexUsuarios.verCorreoActual());
        System.out.println("1. Miembros");
        System.out.println("2. Iglesia");
        System.out.println("3. Depositos");
        System.out.println("4. Informes (Reportes)");
        System.out.println("5. Usuarios");
        System.out.println("6. Cerrar sesion");
    }

    public static void inicio() {
        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = Lector.preguntarEntero("Elige una opcion [1-6]");
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
                    IndexUsuarios.inicio();
                case 6 -> {
                    IndexUsuarios.cerrarSesion();
                    System.out.println("Sesion cerrada.\n");
                }
                default ->
                    Errores.deRango();
            }
        } while (opcion != 6);
    }
}
