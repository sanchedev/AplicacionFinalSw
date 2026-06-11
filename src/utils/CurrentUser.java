/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author alum.l4
 */
public class CurrentUser {
    public static String email;
    
    public static boolean isAdmin() {
        return Users.getRole(email) == 0;
    }
    
    public static boolean login(String email, String password) {
        if (Users.auth(email, password) == -1) return false;
        CurrentUser.email = email;
        return true;
    }
}
