package utils;

import repository.Credenciales;

/**
 * Manejo de sesiones del sistema.
 * Un solo login: email + contraseña → se resuelve el DNI del miembro.
 */
public class Sesion {

    private static String email = "";
    private static String dniMiembro = "";

    public static boolean haAuth() {
        return !email.isEmpty();
    }

    public static boolean authUsuario(String email, String contrasenia) {
        if (haAuth()) {
            return true;
        }

        int indice = Credenciales.indiceSiAuth(email, contrasenia);
        if (indice == -1) {
            return false;
        }

        Sesion.email = email;
        Sesion.dniMiembro = Credenciales.verDniMiembro(indice);
        return true;
    }

    public static void salir() {
        if (!haAuth()) {
            return;
        }

        Sesion.email = "";
        Sesion.dniMiembro = "";
    }

    public static String verEmail() {
        return email;
    }

    public static String verDniMiembro() {
        return dniMiembro;
    }
}
