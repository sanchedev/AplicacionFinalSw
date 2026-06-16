/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import repository.Personas;
import repository.Usuarios;

/**
 *
 * @author alum.l4
 */
public class Sesion {
    private static String email = "";
    private static String dni = "";
    
    public static boolean haAuthUsuario() {
        return !email.equals("");
    }
    public static boolean haAuthPersona() {
        return !dni.equals("");
    }
    
    public static boolean authUsuario(String email, String contrasenia) {
        if (haAuthUsuario()) return true;
        
        if (!Usuarios.auth(email, contrasenia)) {
            return false;
        }
        
        Sesion.email = email;
        return true;
    }
    public static boolean authPersona(String dni) {
        if (haAuthPersona()) return true;
        
        if (Personas.buscarPersona(dni) == -1) {
            return false;
        }
        
        Sesion.dni = dni;
        return true;
    }
    
    public static String verEmail() {
        return email;
    }
    public static String verDNI() {
        return dni;
    }
    
}

