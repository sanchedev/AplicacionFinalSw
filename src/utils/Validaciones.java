package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utilidades de validacion de datos de entrada.
 */
public class Validaciones {

    /**
     * Valida que el DNI sea solo numerico y tenga exactamente 8 digitos.
     */
    public static boolean esDNIValido(String dni) {
        if (dni == null) {
            return false;
        }
        return dni.matches("^\\d{8}$");
    }

    /**
     * Valida que la fecha tenga el formato dd-MM-yyyy y sea una fecha real.
     */
    public static boolean esFechaValida(String fecha) {
        if (fecha == null || fecha.isEmpty()) {
            return false;
        }
        try {
            LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Valida que el correo contenga @ y al menos un punto despues de @.
     */
    public static boolean esCorreoValido(String correo) {
        if (correo == null || correo.isEmpty()) {
            return false;
        }
        int arroba = correo.indexOf('@');
        if (arroba < 1) {
            return false;
        }
        String dominio = correo.substring(arroba + 1);
        return dominio.contains(".");
    }

    /**
     * Valida que el telefono sea solo numerico y tenga exactamente 9 digitos.
     */
    public static boolean esTelefonoValido(String telefono) {
        if (telefono == null) {
            return false;
        }
        return telefono.matches("^\\d{9}$");
    }

    /**
     * Valida que un monto sea mayor a cero.
     */
    public static boolean esMontoPositivo(double monto) {
        return monto > 0;
    }
}
