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
        System.out.print(pregunta+ ": ");
        return sc.nextLine();
    }
    public static String preguntar(String pregunta, String porDefecto) {
        System.out.print(pregunta + " (defecto: "+porDefecto+"): ");
        String respuesta = sc.nextLine();
        if (respuesta.isEmpty()) return porDefecto;
        return respuesta;
    }
    public static int preguntarEntero(String pregunta) {
        return Integer.parseInt(preguntar(pregunta));
    }
    public static int preguntarEntero(String pregunta, int porDefecto) {
        String respuesta = preguntar(pregunta, Integer.toString(porDefecto));
        return Integer.parseInt(respuesta);
    }
    public static double preguntarDecimal(String pregunta) {
        return Double.parseDouble(preguntar(pregunta));
    }
    public static boolean confirmar(String pregunta) {
        System.out.print(pregunta+ " (S/N): ");
        return sc.nextLine().toLowerCase().startsWith("s");
    }
    public static String leerTexto() {
        return sc.nextLine();
    }
    public static int leerEntero() {
        return Integer.parseInt(leerTexto());
    }
    public static boolean leerBooleano() {
        return leerTexto().toLowerCase().startsWith("s");
    }
    public static void cerrar() {
        sc.close();
    }
}
