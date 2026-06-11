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
    
    public static void auth() {
        String email;
        String contrasenia;
        do {
            System.out.println("*** INICIAR SESION ***");
            System.out.print("Email: ");
            email = Lector.leerTexto();
            System.out.print("Contrasenia: ");
            contrasenia = Lector.leerTexto();
            
            if (Usuarios.indiceSiAuth(email, contrasenia) == -1) {
                Errores.deAuth();
            } else {
                Sesion.email = email;
                break;
            }
        } while (true);
    }
    
    public static String verEmail() {
        return email;
    }
    
    public static int verIndice() {
        return Usuarios.buscarUsuario(email);
    }
    
    public static boolean esAdmin() {
        return Usuarios.verRol(email) == 0;
    }
}

