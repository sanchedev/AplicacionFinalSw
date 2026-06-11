/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package vistacontrol;
import utils.Sesion;
import utils.Lector;
import repository.Usuarios;
/**
 *
 * @author alum.l4
 */
public class Index {
    public static void inicio() {
        Usuarios.cargar(); // Cargamos usuarios existentes
        Sesion.auth(); // Pedimos que inicie sesion
        IndexUsuarios.inicio();
        Lector.cerrar();
    }
    
    public static void main(String[] args) {
        inicio();
    }
    
}
