/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package vistacontrol;
import utils.Errores;
import utils.Lector;
import repository.Usuarios;
import utils.Sesion;
/**
 *
 * @author alum.l4
 */
public class IndexUsuarios {
    public static boolean debeSalir = false;
    
    private static void listar() {
        System.out.println("*** USUARIOS ***");
        
        int cantidad = Usuarios.verCantidad();
        
        if (cantidad == 0) {
            System.out.println("!> La lista de usuarios esta vacia");
            return;
        }
        
        for (int i = 0; i < cantidad; i++) {
            Usuarios.mostrarUsuario(i);
            System.out.println(" - - - - - - - - - - ");
        }
        System.out.printf("Se mostraron %d usuario(s)\n", cantidad);
    }
    
    private static void buscarUsuarios() {
        System.out.println("*** BUSCAR USUARIOS ***");
        
        System.out.print("Nombre: ");
        String nombre = Lector.leerTexto();
        System.out.println("");
        
        int[] indices = Usuarios.buscarUsuarios(nombre);
        int cantidad = indices.length;
        
        if (cantidad == 0) {
            System.out.printf("!> No se encontraron usuarios con nombre \"%s\"\n", nombre);
            return;
        }
        
        for (int i = 0; i < cantidad; i++) {
            Usuarios.mostrarUsuario(indices[i]);
            System.out.println(" - - - - - - - - - - ");
        }
        System.out.printf("Se encontraron %d usuario(s)\n", cantidad);
    }
    
    private static void verPerfil() {
        System.out.println("*** MI PERFIL ***");
        
        int indice = Sesion.verIndice();
        
        if (indice == -1) {
            System.out.println("!> No se pudo encontrar tu usuario");
            return;
        }
        
        Usuarios.mostrarMiUsuario(indice);
    }
    
    private static void editarPerfil() {
        System.out.println("*** EDITAR MI PERFIL ***");
        
        String email = Sesion.verEmail();
        
        System.out.print("Contrasenia: ");
        String contrasenia = Lector.leerTexto();
        
        if (!Usuarios.auth(email, contrasenia)) {
            Errores.deAuth();
            return;
        }
        
        System.out.print("Full Name: ");
        String nombre = Lector.leerTexto();
        System.out.print("Password: ");
        String nuevaContra = Lector.leerTexto();
        System.out.print("Birth Date: ");
        String fecha = Lector.leerTexto();
        
        Usuarios.editarPerfil(email, nombre, nuevaContra, fecha);
    }
    
    private static void borrarPerfil() {
        System.out.println("*** ELIMINAR MI PERFIL ***");
        
        String email = Sesion.verEmail();
        
        System.out.print("Contrasenia: ");
        String contrasenia = Lector.leerTexto();
        
        System.out.print("Desea eliminar la cuenta? (S/N): ");
        if (!Lector.leerBooleano()) return;
        
        if (Usuarios.borrarUsuario(email, contrasenia)) {
            System.out.println("\nUsuario eliminado exitosamente!");
        } else {
            Errores.deAuth();
        }
    }
    
    private static void registrarUsuario() {
        System.out.println("*** REGISTRAR ***");
        System.out.print("Email: ");
        String email = Lector.leerTexto();
        System.out.print("Nombre Completo: ");
        String nombre = Lector.leerTexto();
        System.out.print("Contrasenia: ");
        String contrasenia = Lector.leerTexto();
        int rol;
        do {
            System.out.print("Rol (0=Admin, 1=Comun): ");
            rol = Lector.leerEntero();
            if (rol == 0 || rol == 1) {
                break;
            } else {
                Errores.deRango();
            }
        } while (true);
        System.out.print("Fecha de Nacimiento: ");
        String fecha = Lector.leerTexto();
        
        Usuarios.crearUsuario(email, nombre, contrasenia, rol, fecha);
        
        System.out.println("Usuario registrado exitosamente!");
    }
    
    private static void borrarUsuario() {
        System.out.println("*** BORRAR USUARIO ***");
        
        System.out.print("Email: ");
        String email = Lector.leerTexto();
        
        if (email.equals(Sesion.verEmail())) {
            Errores.personalizado("No puedes eliminar tu usuario");
            return;
        }
        
        System.out.print("Contrasenia: ");
        String contrasenia = Lector.leerTexto();
        
        if (Usuarios.borrarUsuario(email, contrasenia)) {
            System.out.println("Usuario eliminado correctamente!");
        } else {
            Errores.deAuth();
        }
    }
    
    private static void volver() {
        debeSalir = true;
        System.out.println("Buena suerte!\n");
    }
    
    public static void mostrarMenu() {
        boolean esAdmin = Sesion.esAdmin();
        
        System.out.println("");
        System.out.println("*** PANEL DE USUARIO ***");
        if (esAdmin) System.out.println(" >>> :Administrador <<< ");
        System.out.println("1. Ver Todos Los Usuarios");
        System.out.println("2. Buscar Usuarios");
        System.out.println("3. Ver Mi Perfil");
        System.out.println("4. Editar Mi Perfil");
        
        int last;
        if (!esAdmin) {
            System.out.println("5. Borrar Mi Perfil");
            last = 6;
        } else {
            System.out.println("5. Registrar Usuario");
            System.out.println("6. Borrar Usuario");
            last = 7;
        }
        System.out.println(last + ". Volver");
        System.out.printf("Elige una opcion [1-%d]: ", last);
    }
    
    public static void menu() {
        int option = Lector.leerEntero();
        System.out.println("");
        
        switch (option) {
            case 1 -> listar();
            case 2 -> buscarUsuarios();
            case 3 -> verPerfil();
            case 4 -> editarPerfil();
            case 5 -> {
                if (Sesion.esAdmin()) registrarUsuario();
                else borrarPerfil();
            }
            case 6 -> {
                if (Sesion.esAdmin()) borrarUsuario();
                else volver();
            }
            case 7 -> {
                if (Sesion.esAdmin()) volver();
                else Errores.deRango();
            }
            default -> Errores.deRango();
        }
    }
    
    public static void inicio() {
        debeSalir = false;
        do {
            mostrarMenu();
            menu();
        } while (!debeSalir);
    }
}
