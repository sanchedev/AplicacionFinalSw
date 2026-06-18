/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package vistacontrol;

import repository.Depositos;
import repository.Iglesias;
import repository.Personas;
import utils.Sesion;
import utils.Lector;
import repository.Usuarios;
import utils.Errores;

/**
 *
 * @author alum.l4
 */
public class Index {

    private static void entrarTesoreria() {
        String email;
        String contrasenia;
        boolean haEntrado = Sesion.haAuthUsuario();
        boolean haSkipeado = false;

        // Si no tiene sesion abierta y no ha skipeado
        while (!haEntrado && !haSkipeado) {
            System.out.println("*** INICIAR SESION ***");
            email = Lector.preguntar("Email");
            contrasenia = Lector.preguntar("Contrasenia");

            haEntrado = Sesion.authUsuario(email, contrasenia);
            if (!haEntrado) {
                Errores.deAuthUsuario();
                haSkipeado = !Lector.confirmar("¿Desea volver a intentar?");
            }
        }

        if (haEntrado) {
            IndexTesoreria.inicio();
        }

        System.out.println("");
    }

    private static void entrarFeligres() {
        String dni;
        boolean haEntrado = Sesion.haAuthPersona() || Lector.confirmar("¿Desea entrar como incognito?");
        boolean haSkipeado = false;

        // Si no tiene sesion abierta y no ha skipeado
        while (!haEntrado && !haSkipeado) {
            System.out.println("*** ENTRAR ***");

            dni = Lector.preguntar("DNI");

            haEntrado = Sesion.authPersona(dni);
            if (!haEntrado) {
                Errores.deAuthPersona();
                haSkipeado = !Lector.confirmar("¿Desea volver a intentar?");
            }
        }

        if (haEntrado) {
            IndexFeligres.inicio();
        }

        System.out.println("");
    }

    private static void salir() {
        System.out.println("Buena suerte!");
    }

    public static void mostrarMenu() {
        System.out.println("*** BIENVENIDO(A) ***");
        System.out.println("1. Entrar como Tesorero(a)");
        System.out.println("2. Entrar como Feligres");
        System.out.println("3. Salir");
    }

    public static void inicio() {
        Usuarios.cargar();
        Iglesias.cargar();
        Personas.cargar();
        Depositos.cargar();

        int opcion;
        do {
            mostrarMenu();
            opcion = Lector.preguntarEntero("Elige una opcion [1-3]");
            System.out.println("");

            switch (opcion) {
                case 1 ->
                    entrarTesoreria();
                case 2 ->
                    entrarFeligres();
                case 3 ->
                    salir();
                default ->
                    Errores.deRango();
            }
        } while (opcion != 3);
        Lector.cerrar();
    }

    public static void main(String[] args) {
        inicio();
    }

}
