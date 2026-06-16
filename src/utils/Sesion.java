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
    private static String email;
    private static String dni;
    
    public static boolean authUsuario() {
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
            Errores.deAuthUsuario();
            return false;
        }
    }
    public static boolean authPersona() {
        if (dni != null) return true;
        
        String dni;
        
        System.out.println("*** INGRESANDO COMO PERSONA ***");
        dni = Lector.preguntar("DNI");

        if (Personas.buscarPersona(dni) != -1) {
            Sesion.dni = dni;
            return true;
        } else {
            Errores.deAuthPersona();
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

