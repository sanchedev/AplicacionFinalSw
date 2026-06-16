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
        System.out.println("\nERROR: "+msg+"\n");
    }
    
    public static void deAuthUsuario() {
        personalizado("Email o Contrasenia incorrectos");
    }
    public static void deAuthPersona() {
        personalizado("DNI no registrado");
    }
    public static void deRango() {
        personalizado("El numero esta fuera de rango");
    }
}
