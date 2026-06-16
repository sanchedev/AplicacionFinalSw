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
        
        String nombre = Lector.preguntar("Nombre");
        
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
        Usuarios.mostrarMiUsuario();
    }
    
    private static void editarPerfil() {
        System.out.println("*** EDITAR MI PERFIL ***");
        
        String email = Sesion.verEmail();
        
        String contrasenia = Lector.preguntar("Contrasenia");
        
        if (!Usuarios.auth(email, contrasenia)) {
            Errores.deAuthUsuario();
            return;
        }
        
        String nombre = Lector.preguntar("Nombre Completo");
        String nuevaContra = Lector.preguntar("Contrasenia");
        String fecha = Lector.preguntar("Fecha de Nacimiento");
        
        Usuarios.editarPerfil(email, nombre, nuevaContra, fecha);
    }
    
    private static void registrarUsuario() {
        System.out.println("*** REGISTRAR ***");
        
        String email = Lector.preguntar("Email");
        String nombre = Lector.preguntar("Nombre Completo");
        String contrasenia = Lector.preguntar("Contrasenia");
        String fecha = Lector.preguntar("Fecha de Nacimiento");
        
        Usuarios.crearUsuario(email, nombre, contrasenia, fecha);
        
        System.out.println("Usuario registrado exitosamente!");
    }
    
    private static void borrarUsuario() {
        System.out.println("*** BORRAR USUARIO ***");
        
        String email = Lector.preguntar("Email");
        
        if (email.equals(Sesion.verEmail())) {
            Errores.personalizado("No puedes eliminar tu usuario");
            return;
        }
        
        String contrasenia = Lector.preguntar("Contrasenia");
        
        if (Usuarios.borrarUsuario(email, contrasenia)) {
            System.out.println("Usuario eliminado correctamente!");
        } else {
            Errores.deAuthUsuario();
        }
    }
    
    private static void volver() {
        System.out.println("Buena suerte!\n");
    }
    
    public static void mostrarMenu() {
        System.out.println("");
        System.out.println("*** PANEL DE USUARIO ***");
        System.out.println("1. Ver Todos Los Usuarios");
        System.out.println("2. Buscar Usuarios");
        System.out.println("3. Ver Mi Perfil");
        System.out.println("4. Editar Mi Perfil");
        System.out.println("5. Registrar Usuario");
        System.out.println("6. Borrar Usuario");
        System.out.println("7. Volver");
    }
    
    public static void inicio() {
        int opcion;
        do {
            mostrarMenu();
            opcion = Lector.preguntarEntero("Elige una opcion [1-7]");
            System.out.println("");

            switch (opcion) {
                case 1 -> listar();
                case 2 -> buscarUsuarios();
                case 3 -> verPerfil();
                case 4 -> editarPerfil();
                case 5 -> registrarUsuario();
                case 6 -> borrarUsuario();
                case 7 -> volver();
                default -> Errores.deRango();
            }
        } while (opcion != 7);
    }
}
