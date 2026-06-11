/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package vistacontrol;
import utils.CurrentUser;
import utils.Reader;
import utils.Users;
/**
 *
 * @author alum.l4
 */
public class Indexpersona {
    public static final Reader Reader = new Reader();
    
    private static void list() {
        System.out.println("*** LIST ***");
        
        int length = Users.getLength();
        
        if (length == 0) {
            System.out.println("Users list is empty");
            System.out.println("Start to register users");
            return;
        }
        
        for (int i = 0; i < length; i++) {
            Users.showUser(i);
        }
    }
    
    private static void search() {
        System.out.println("*** SEARCH ***");
        System.out.print("Search by Name: ");
        String name = Reader.readText();
        
        int[] indexes = Users.searchUsers(name);
        
        for (int i = 0; i < indexes.length; i++) {
            Users.showUser(indexes[i]);
            System.out.println("----------");
        }
    }
    
    private static void find() {
        System.out.println("*** FIND ***");
        System.out.print("Email: ");
        String email = Reader.readText();
        
        int index = Users.findUser(email);
        
        if (index == -1) {
            System.out.println("User not found");
            return;
        }
        
        Users.showUser(index);
    }
    
    private static void viewProfile() {
        System.out.println("*** MY PROFILE ***");
        
        int index = Users.findUser(CurrentUser.email);
        
        if (index == -1) {
            System.out.println("User not found");
            return;
        }
        
        Users.showMyUser(index);
    }
    
    private static void editProfile() {
        System.out.println("*** EDIT MY PROFILE ***");
        
        System.out.print("Password: ");
        String password = Reader.readText();
        
        if (Users.auth(CurrentUser.email, password) == -1) {
            System.out.println("User or Password Incorrect!");
            System.out.println("Try again later...");
            return;
        }
        
        System.out.print("Full Name: ");
        String name = Reader.readText();
        System.out.print("Password: ");
        String newPassword = Reader.readText();
        System.out.print("Birth Date: ");
        String date = Reader.readText();
        
        Users.editProfile(CurrentUser.email, name, newPassword, date);
    }
    
    private static void deleteProfile() {
        System.out.println("*** DELETE MY PROFILE ***");
        
        System.out.print("Password: ");
        String password = Reader.readText();
        
        if (Users.auth(CurrentUser.email, password) == -1) {
            System.out.println("User or Password Incorrect!");
            System.out.println("Try again later...");
            return;
        }
        
        System.out.print("Are you sure? (Y/N): ");
        if (!Reader.readBool()) return;
        
        Users.deleteUser(CurrentUser.email);
    }
    
    private static void register() {
        System.out.println("*** REGISTER ***");
        System.out.print("Email: ");
        String email = Reader.readText();
        System.out.print("Full Name: ");
        String name = Reader.readText();
        System.out.print("Password: ");
        String password = Reader.readText();
        System.out.print("Role (0 -> Admin, 1 -> Common): ");
        int role = Reader.readInt();
        System.out.print("Birth Date: ");
        String date = Reader.readText();
        
        Users.addUser(email, name, password, role, date);
        
        System.out.println("Datas added successfully!");
    }
    
    private static void delete() {
        System.out.println("*** DELETE ***");
        
        System.out.print("User's Email to delete: ");
        String email = Reader.readText();
        
        System.out.print("User password: ");
        String password = Reader.readText();
        
        int index = Users.auth(email, password);
        
        if (index == -1) {
            System.out.println("Email or Password is Incorrect!");
            System.out.println("Try again later...");
            return;
        }
        
        if (Users.deleteUser(email)) {
            System.out.println("User deleted!");
        } else {
            System.out.println("We cant delete this user...");
            System.out.println("Try again later...");
        }
    }
    
    private static void exit() {
        System.out.println("Exiting...");
        System.out.println("Good Bye!");
    }
    
    private static void error() {
        System.out.println("\nERROR! You need put a number in range\n");
    }
    
    public static int showMenu() {
        System.out.println("*** CRUD ***");
        System.out.println("1. List Users");
        System.out.println("2. Search Users");
        System.out.println("3. Find User");
        System.out.println("4. View Profile");
        System.out.println("5. Edit Profile");
        int last;
        if (!CurrentUser.isAdmin()) {
            System.out.println("6. Delete Profile");
            last = 7;
        } else {
            System.out.println("6. Register");
            System.out.println("7. Delete User");
            last = 8;
        }
        System.out.println(last + ". Exit");
        System.out.print("Choose an option [1-"+last+"]: ");
        return last;
    }
    
    public static int menu() {
        int option = Reader.readInt();
        switch (option) {
            case 1 -> list();
            case 2 -> search();
            case 3 -> find();
            case 4 -> viewProfile();
            case 5 -> editProfile();
            default -> {
                if (!CurrentUser.isAdmin()) {
                    switch (option) {
                        case 6 -> deleteProfile();
                        case 7 -> exit();
                        default -> error();
                    }
                } else {
                    switch (option) {
                        case 6 -> register();
                        case 7 -> delete();
                        case 8 -> exit();
                        default -> error();
                    }
                }
            }
        }
        return option;
    }
    
    public static void init() {
        int opt;
        int exitNum = 0;
        do {
            exitNum = showMenu();
            opt = menu();
        } while (opt != exitNum);
    }
}
