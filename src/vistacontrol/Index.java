package vistacontrol;

import utils.Errores;
import utils.Lector;

/**
 * @author sanchedev
 * @author plasencia
 */
public class Index {

    private static final int MAX_INTENTOS = 3;

    private static void crearAdminPorDefecto() {
        if (IndexUsuarios.cantidad == 0) {
            int codigo = 0;
            IndexUsuarios.agregarDirecto("Admin", "admin@iasd.com", "admin123", codigo);
            System.out.println("--------------------------------------------------");
            System.out.println("Se creo un usuario por defecto:");
            System.out.println("  Correo:     admin@iasd.com");
            System.out.println("  Contrasenia: admin123");
            System.out.println("Le recomendamos crear otro usuario y eliminar este.");
            System.out.println("--------------------------------------------------");
            System.out.println("");
        }
    }

    private static boolean login() {
        System.out.println("*** INICIAR SESION ***");
        for (int intento = 1; intento <= MAX_INTENTOS; intento++) {
            String correo = Lector.preguntar("Correo");
            String contrasenia = Lector.preguntar("Contrasenia");
            if (IndexUsuarios.iniciarSesion(correo, contrasenia)) {
                System.out.println("Bienvenido " + IndexUsuarios.verNombre(IndexUsuarios.usuarioActual) + "!");
                System.out.println("");
                return true;
            }
            Errores.deAuthUsuario();
            if (intento < MAX_INTENTOS) {
                System.out.println("Intento " + intento + " de " + MAX_INTENTOS + ". Intente de nuevo.");
            }
        }
        System.out.println("Agoto el maximo de intentos.");
        return false;
    }

    private static void mostrarMenuInicial() {
        System.out.println("*** SISTEMA DE TESORERIA IASD ***");
        System.out.println("1. Iniciar sesion");
        System.out.println("2. Salir");
    }

    public static void inicio() {
        IndexIglesias.cargar();
        IndexMiembros.cargar();
        IndexDepositos.cargar();
        IndexUsuarios.cargar();
        crearAdminPorDefecto();

        int opcion;
        do {
            mostrarMenuInicial();
            opcion = Lector.preguntarEntero("Elige una opcion [1-2]");
            System.out.println("");

            switch (opcion) {
                case 1 -> {
                    if (login()) {
                        IndexSistema.inicio();
                    }
                }
                case 2 ->
                    System.out.println("Buena suerte!");
                default ->
                    Errores.deRango();
            }
        } while (opcion != 2);
        Lector.cerrar();
    }

    public static void main(String[] args) {
        inicio();
    }
}
