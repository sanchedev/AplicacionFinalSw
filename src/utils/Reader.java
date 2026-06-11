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
public class Reader {
    private static final Scanner sc = new Scanner(System.in);
    
    public static String readText() {
        return sc.nextLine();
    }
    public static int readInt() {
        return Integer.parseInt(readText());
    }
    public static boolean readBool() {
        return readText().toLowerCase().startsWith("y");
    }
    public static void close() {
        sc.close();
    }
}
