/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package vistacontrol;
import utils.Login;
import utils.Reader;
import utils.Users;
/**
 *
 * @author alum.l4
 */
public class Index {
    public static void init() {
        Users.init();
        Login.auth();
        Indexpersona.init();
        Reader.close();
    }
    
    public static void main(String[] args) {
        init();
    }
    
}
