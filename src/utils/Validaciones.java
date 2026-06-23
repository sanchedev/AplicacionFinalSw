package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @author sanchedev
 */
public class Validaciones {

    public static boolean esDNIValido(String dni) {
        if (dni == null) {
            return false;
        }
        return dni.matches("^\\d{8}$"); // regex para 8 chars digitos
    }

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
}
