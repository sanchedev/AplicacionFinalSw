package vistacontrol;

import repository.Credenciales;
import repository.Depositos;
import repository.Gastos;
import repository.Iglesias;
import repository.Miembros;
import utils.Errores;
import utils.Lector;
import utils.Sesion;

/**
 * Punto de entrada principal de la aplicacion.
 * Menu de login simplificado: solo sesion de usuario (tesorero).
 */
public class Index {

    private static void iniciarSesion() {
        String email;
        String contrasenia;
        boolean haEntrado = Sesion.haAuth();
        boolean haSkipeado = false;

        while (!haEntrado && !haSkipeado) {
            System.out.println("*** INICIAR SESION ***");
            email = Lector.preguntar("Email");
            contrasenia = Lector.preguntar("Contrasenia");

            haEntrado = Sesion.authUsuario(email, contrasenia);
            if (!haEntrado) {
                Errores.deAuthUsuario();
                haSkipeado = !Lector.confirmar("Desea volver a intentar?");
            }
        }

        if (haEntrado) {
            String dni = Sesion.verDniMiembro();
            if (Miembros.verificarRolExpirado(dni)) {
                Miembros.retirarRol(dni);
                Credenciales.eliminarCredencial(dni);
                System.out.println("Su acceso ha expirado. Contacte al administrador.");
                Sesion.salir();
                return;
            }

            IndexSistema.inicio();
        }

        System.out.println("");
    }

    private static void salir() {
        System.out.println("Buena suerte!");
    }

    public static void mostrarMenu() {
        System.out.println("*** SISTEMA DE TESORERIA IASD ***");
        System.out.println("1. Iniciar Sesion");
        System.out.println("2. Salir");
    }

    public static void inicio() {
        Credenciales.cargar();
        Iglesias.cargar();
        Miembros.cargar();
        Depositos.cargar();
        Gastos.cargar();

        if (Credenciales.verCantidad() == 0) {
            System.out.println("""
                    BIENVENIDO(A)!
                    ESTA ES LA PRIMERA VEZ QUE ABRES LA APLICACION.
                    
                    Se ha creado una cuenta de administrador:
                      Email: admin@iasd.com
                      Contrasenia: admin123
                    
                    Inicia sesion y crea los miembros que necesites.
                    Por seguridad, elimina esta cuenta despues.
                    """);

            Miembros.crearMiembro("00000000", "Administrador", "", "admin@iasd.com", "", -1);
            Credenciales.crearCredencial("admin@iasd.com", "admin123", "00000000");
        }

        int opcion;
        do {
            mostrarMenu();
            opcion = Lector.preguntarEntero("Elige una opcion [1-2]");
            System.out.println("");

            switch (opcion) {
                case 1 ->
                    iniciarSesion();
                case 2 ->
                    salir();
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
