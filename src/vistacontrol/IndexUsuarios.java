package vistacontrol;

import utils.Archivo;
import utils.Errores;
import utils.Lector;

/**
 * @author sanchedev
 */
public class IndexUsuarios {

    private static final int[] codigos = new int[1000];
    private static final String[] nombres = new String[1000];
    private static final String[] correos = new String[1000];
    private static final String[] contrasenias = new String[1000];
    public static int cantidad = 0;
    public static int usuarioActual = -1;

    private static Archivo genArchivo() {
        Archivo archivo = new Archivo("saveds/db/usuarios.csv");
        archivo.agregarCabecera("codigos");
        archivo.agregarCabecera("nombres");
        archivo.agregarCabecera("correos");
        archivo.agregarCabecera("contrasenias");
        return archivo;
    }

    private static void guardarUsuarios() {
        Archivo archivo = genArchivo();
        for (int i = 0; i < cantidad; i++) {
            archivo.agregarDatos(archivo.verCabecera(0), codigos[i] + "");
            archivo.agregarDatos(archivo.verCabecera(1), nombres[i]);
            archivo.agregarDatos(archivo.verCabecera(2), correos[i]);
            archivo.agregarDatos(archivo.verCabecera(3), contrasenias[i]);
        }
        archivo.guardar();
    }

    private static void cargarUsuarios() {
        Archivo archivo = genArchivo();
        archivo.leer();
        for (int i = 0; i < archivo.verCantidadDatos(); i++) {
            try {
                int codigo = Integer.parseInt(archivo.verDato(archivo.verCabecera(0), i));
                String nombre = archivo.verDato(archivo.verCabecera(1), i);
                String correo = archivo.verDato(archivo.verCabecera(2), i);
                String contrasenia = archivo.verDato(archivo.verCabecera(3), i);
                if (nombre.isEmpty() || correo.isEmpty() || contrasenia.isEmpty()) {
                    continue;
                }
                if (!correo.contains("@")) {
                    continue;
                }
                codigos[cantidad] = codigo;
                nombres[cantidad] = nombre;
                correos[cantidad] = correo;
                contrasenias[cantidad] = contrasenia;
                cantidad++;
            } catch (NumberFormatException e) {
            }
        }
        guardarUsuarios();
    }

    public static void cargar() {
        cargarUsuarios();
    }

    public static void agregarDirecto(String nombre, String correo, String contrasenia, int codigo) {
        codigos[cantidad] = codigo;
        nombres[cantidad] = nombre;
        correos[cantidad] = correo;
        contrasenias[cantidad] = contrasenia;
        cantidad++;
        guardarUsuarios();
    }

    public static int buscarPorCodigo(int codigo) {
        int indice = -1;
        for (int i = 0; i < cantidad; i++) {
            if (codigos[i] == codigo) {
                indice = i;
                break;
            }
        }
        return indice;
    }

    public static int buscarPorCorreo(String correo) {
        int indice = -1;
        for (int i = 0; i < cantidad; i++) {
            if (correos[i].equals(correo.trim())) {
                indice = i;
                break;
            }
        }
        return indice;
    }

    public static boolean iniciarSesion(String correo, String contrasenia) {
        int indice = buscarPorCorreo(correo);
        if (indice == -1) {
            return false;
        }
        if (!contrasenias[indice].equals(contrasenia)) {
            return false;
        }
        usuarioActual = indice;
        return true;
    }

    public static void cerrarSesion() {
        usuarioActual = -1;
    }

    public static String verCorreoActual() {
        if (usuarioActual == -1) {
            return "";
        }
        return correos[usuarioActual];
    }

    public static String verNombre(int codigo) {
        int indice = buscarPorCodigo(codigo);
        if (indice == -1) {
            return "";
        }
        return nombres[indice];
    }

    private static void agregar() {
        System.out.println("*** Agregar Usuario ***");
        String nombre = Lector.preguntar("Nombre");
        if (nombre.isEmpty()) {
            Errores.personalizado("El nombre no puede estar vacio");
            return;
        }
        String correo = Lector.preguntar("Correo");
        if (!correo.contains("@")) {
            Errores.personalizado("\"" + correo + "\" no es un correo valido");
            return;
        }
        if (buscarPorCorreo(correo) != -1) {
            Errores.personalizado("El correo ya se encuentra registrado");
            return;
        }
        String contrasenia = Lector.preguntar("Contrasenia");
        if (contrasenia.isEmpty()) {
            Errores.personalizado("La contrasenia no puede estar vacia");
            return;
        }
        int codigo = 0;
        if (cantidad > 0) {
            codigo = codigos[cantidad - 1] + 1;
        }
        codigos[cantidad] = codigo;
        nombres[cantidad] = nombre;
        correos[cantidad] = correo;
        contrasenias[cantidad] = contrasenia;
        cantidad++;
        guardarUsuarios();
        System.out.println("Usuario agregado con exito!");
    }

    private static void eliminar() {
        System.out.println("*** Eliminar Usuario ***");
        int codigo = Lector.preguntarEntero("Codigo de Usuario a eliminar");
        int indice = buscarPorCodigo(codigo);
        if (indice == -1) {
            Errores.personalizado("El usuario con ese codigo no existe");
            return;
        }
        if (usuarioActual != -1 && codigos[indice] == codigos[usuarioActual]) {
            Errores.personalizado("No puede eliminar su propia cuenta");
            return;
        }
        if (!Lector.confirmar("Desea eliminar el usuario " + nombres[indice] + "?")) {
            return;
        }
        cantidad--;
        for (int i = indice; i < cantidad; i++) {
            codigos[i] = codigos[i + 1];
            nombres[i] = nombres[i + 1];
            correos[i] = correos[i + 1];
            contrasenias[i] = contrasenias[i + 1];
        }
        guardarUsuarios();
        System.out.println("Usuario eliminado con exito!");
    }

    private static void editar() {
        System.out.println("*** Editar Usuario ***");
        int codigo = Lector.preguntarEntero("Ingrese el Codigo a editar");
        int indice = buscarPorCodigo(codigo);
        if (indice == -1) {
            Errores.personalizado("El codigo no esta registrado");
            return;
        }
        String nombre = Lector.preguntar("Nombre", nombres[indice]);
        String correo = Lector.preguntar("Correo", correos[indice]);
        if (!correo.contains("@")) {
            Errores.personalizado("\"" + correo + "\" no es un correo valido");
            return;
        }
        int existeOtro = buscarPorCorreo(correo);
        if (existeOtro != -1 && existeOtro != indice) {
            Errores.personalizado("El correo ya esta en uso por otro usuario");
            return;
        }
        String contrasenia = Lector.preguntar("Contrasenia", contrasenias[indice]);
        if (contrasenia.isEmpty()) {
            Errores.personalizado("La contrasenia no puede estar vacia");
            return;
        }
        nombres[indice] = nombre;
        correos[indice] = correo;
        contrasenias[indice] = contrasenia;
        guardarUsuarios();
        System.out.println("Usuario editado con exito!");
    }

    private static void listar() {
        System.out.println("*** Listar Usuarios ***");
        if (cantidad == 0) {
            System.out.println("!> No hay usuarios");
            return;
        }
        for (int i = 0; i < cantidad; i++) {
            System.out.println("Codigo:      " + codigos[i]);
            System.out.println("Nombre:      " + nombres[i]);
            System.out.println("Correo:      " + correos[i]);
            System.out.println("Contrasenia: " + contrasenias[i]);
            System.out.println("- - - - - - - - - - -");
        }
        System.out.println("Se mostraron " + cantidad + " usuario(s)");
    }

    private static void buscar() {
        System.out.println("*** Buscar Usuario ***");
        String nombre = Lector.preguntar("Nombre");
        int indice = -1;
        for (int i = 0; i < cantidad; i++) {
            if (nombres[i].toLowerCase().contains(nombre.toLowerCase())) {
                indice = i;
                break;
            }
        }
        if (indice == -1) {
            System.out.println("!> No se encontro ningun usuario con el nombre \"" + nombre + "\"");
            return;
        }
        System.out.println("- - -");
        System.out.println("Codigo:      " + codigos[indice]);
        System.out.println("Nombre:      " + nombres[indice]);
        System.out.println("Correo:      " + correos[indice]);
        System.out.println("Contrasenia: " + contrasenias[indice]);
    }

    private static void volver() {
        System.out.println("Buena suerte!\n");
    }

    public static void mostrarMenu() {
        System.out.println("");
        System.out.println("*** MENU USUARIOS ***");
        System.out.println("1. Agregar");
        System.out.println("2. Eliminar");
        System.out.println("3. Editar");
        System.out.println("4. Listar");
        System.out.println("5. Buscar");
        System.out.println("6. Volver");
    }

    public static void inicio() {
        int opcion;
        do {
            mostrarMenu();
            opcion = Lector.preguntarEntero("Elige una opcion [1-6]");
            System.out.println("");

            switch (opcion) {
                case 1 ->
                    agregar();
                case 2 ->
                    eliminar();
                case 3 ->
                    editar();
                case 4 ->
                    listar();
                case 5 ->
                    buscar();
                case 6 ->
                    volver();
                default ->
                    Errores.deRango();
            }
        } while (opcion != 6);
    }
}
