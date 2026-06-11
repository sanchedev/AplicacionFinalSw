/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author alum.l4
 */
public class Users {
    private static final String[] emails = new String[1000];
    private static final String[] passwords = new String[1000];
    private static final String[] names = new String[1000];
    private static final int[] roles = new int[1000]; // 0 -> admin; 1 -> common
    private static final String[] dates = new String[1000];
    private static int length = 0;
    
    private static void loadUsers() {
        addUser("oscar.sanchez@upeu.edu.pe", "José Sánchez", "12345678", 0, "30/10/08");
    }
    
    /** 
     * @param email The user email
     * @return The user index */
    public static int findUser(String email) {
        int index = -1;
        for (int i = 0; i < length; i++) {
            if (emails[i].equals(email)) {
                index = i;
                break;
            }
        }
        return index;
    }
    
    /** 
     * @param name The user name
     * @return The users indexes */
    public static int[] searchUsers(String name) {
        int[] indexes = new int[length];
        int foundLength = 0;
        for (int i = 0; i < length; i++) {
            if (!names[i].toLowerCase().contains(name.toLowerCase())) continue;
            indexes[foundLength] = i;
            foundLength++;
        }
        int[] found = new int[foundLength];
        for (int i = 0; i < foundLength; i++) {
            found[i] = indexes[i];
        }
        return found;
    }
    
    /** Returns the user index */
    public static int addUser(String email, String name, String password, int role, String date) {
        if (findUser(email) != -1) return -1;
        
        emails[length] = email;
        names[length] = name;
        passwords[length] = password;
        roles[length] = role;
        dates[length] = date;
        
        length++;
        
        return length - 1;
    }
    
    /** Returns the user index */
    public static int editProfile(String email, String name, String password, String date) {
        int index = findUser(email);
        if (index == -1) return -1;
        
        names[index] = name;
        passwords[index] = password;
        dates[index] = date;
        
        return index;
    }
    
    /** Returns if the user has be deleted */
    public static boolean deleteUser(String email) {
        int index = findUser(email);
        if (index == -1) return false;
        
        for (int i = index; i < length; i++) {
            emails[i] = emails[i+1];
            passwords[i] = passwords[i+1];
            names[i] = names[i+1];
            roles[i] = roles[i+1];
            dates[i] = dates[i+1];
        }
        length--;

        return true;
    }
    
    public static int auth(String email, String password) {
        int index = findUser(email);
        if (index == -1) return -1;
        if (!passwords[index].equals(password)) return -1;
        return index;
    }
    
    public static int getLength() {
        return length;
    }
    
    public static void showUser(int index) {
        if (index < 0 || index >= length) return;
        
        System.out.println("Full Name: " + names[index]);
        System.out.println("Birth Date: " + dates[index]);
    }
    
    public static void showMyUser(int index) {
        if (index < 0 || index >= length) return;
        
        System.out.println("Email: " + emails[index]);
        System.out.println("Full Name: " + names[index]);
        System.out.println("Role: " + roles[index]);
        System.out.println("Birth Date: " + dates[index]);
    }

    public static int getRole(String email) {
        int index = findUser(email);
        if (index == -1) return 2; // 2 is not found
        return roles[index];
    }
    
    public static void init() {
        loadUsers();
    }
}
