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
    public static int preguntarEntero(String pregunta) {
        return Integer.parseInt(preguntar(pregunta));
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
