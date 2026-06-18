/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.util.Scanner;

/**
 *
 * @author alum.l4
 */
public class Lector {

    private static final Scanner sc = new Scanner(System.in);

    public static String preguntar(String pregunta) {
        System.out.print(pregunta + ": ");
        return sc.nextLine();
    }

    public static String preguntar(String pregunta, String porDefecto) {
        System.out.print(pregunta + " [por defecto: " + porDefecto + "]: ");
        String respuesta = sc.nextLine();
        if (respuesta.isEmpty()) {
            return porDefecto;
        }
        return respuesta;
    }

    public static int preguntarEntero(String pregunta) {
        String respuesta;
        do {
            respuesta = preguntar(pregunta);
            try {
                return Integer.parseInt(respuesta);
            } catch (NumberFormatException e) {
                Errores.personalizado("`" + respuesta + "` no es un numero valido...");
            }
        } while (true);
    }

    public static int preguntarEntero(String pregunta, int porDefecto) {
        String respuesta;
        do {
            respuesta = preguntar(pregunta, Integer.toString(porDefecto));
            try {
                return Integer.parseInt(respuesta);
            } catch (NumberFormatException e) {
                Errores.personalizado("`" + respuesta + "` no es un numero valido...");
            }
        } while (true);
    }

    public static double preguntarDecimal(String pregunta) {
        String respuesta;
        do {
            respuesta = preguntar(pregunta);
            try {
                return Double.parseDouble(respuesta);
            } catch (NumberFormatException e) {
                Errores.personalizado("`" + respuesta + "` no es un numero valido...");
            }
        } while (true);
    }

    public static double preguntarDouble(String pregunta, double porDefecto) {
        String respuesta;
        do {
            respuesta = preguntar(pregunta, Double.toString(porDefecto));
            try {
                return Double.parseDouble(respuesta);
            } catch (NumberFormatException e) {
                Errores.personalizado("`" + respuesta + "` no es un numero valido...");
            }
        } while (true);
    }

    public static boolean confirmar(String pregunta) {
        System.out.print(pregunta + " (S/N): ");
        return sc.nextLine().trim().toLowerCase().startsWith("s");
    }

    public static void cerrar() {
        sc.close();
    }
}
