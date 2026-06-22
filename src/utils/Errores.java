package utils;

/**
 * Mensajes de error centralizados.
 */
public class Errores {

    public static void personalizado(String msg) {
        System.out.println("\nERROR: " + msg + "\n");
    }

    public static void deAuthUsuario() {
        personalizado("Email o Contrasenia incorrectos");
    }

    public static void deRango() {
        personalizado("El numero esta fuera de rango");
    }
}
