/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;
/**
 *
 * @author alum.l4
 */
public class Login {
    public static void auth() {
        String email;
        String password;
        boolean hasAuth = true;
        do {
            if (!hasAuth) {
                System.out.println("Email or Password Incorrect\n");
            }
            System.out.println("*** LOGIN ***");
            System.out.print("Email: ");
            email = Reader.readText();
            System.out.print("Password: ");
            password = Reader.readText();
            hasAuth = CurrentUser.login(email, password);
        } while (!hasAuth);
    }
}

