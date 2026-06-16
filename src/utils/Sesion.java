/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import repository.Usuarios;

/**
 *
 * @author alum.l4
 */
public class Sesion {
    private static String email;
    
    public static boolean auth() {
        if (email != null) return true;
        
        String email;
        String contrasenia;
        
        System.out.println("*** INICIAR SESION ***");
        email = Lector.preguntar("Email");
        contrasenia = Lector.preguntar("Contrasenia");

        if (Usuarios.auth(email, contrasenia)) {
            Sesion.email = email;
            return true;
        } else {
            Errores.deAuth();
            return false;
        }
    }
    
    public static String verEmail() {
        return email;
    }
    
    public static int verIndice() {
        if (email == null) return -1;
        return Usuarios.buscarUsuario(email);
    }
}

