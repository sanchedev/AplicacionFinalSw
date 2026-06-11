/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author sanchedev
 */
public class Errores {
    public static void personalizado(String msg) {
        System.out.println("\nERROR: "+msg);
    }
    
    public static void deAuth() {
        personalizado("Email o Contrasenia incorrectos");
    }
    public static void deRango() {
        personalizado("El numero esta fuera de rango");
    }
}
