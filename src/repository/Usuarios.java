/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import utils.Archivo;
import utils.Sesion;

/**
 *
 * @author alum.l4
 */
public class Usuarios {

    private static final int LONGITUD_MAXIMA = 1000;
    private static final String[] emails = new String[LONGITUD_MAXIMA];
    private static final String[] contrasenias = new String[LONGITUD_MAXIMA];
    private static final String[] nombres = new String[LONGITUD_MAXIMA];
    private static int cantidad = 0;

    private static Archivo genArchivo() {
        Archivo archivo = new Archivo("saveds/db/usuarios.csv");
        archivo.agregarCabecera("emails");
        archivo.agregarCabecera("contrasenias");
        archivo.agregarCabecera("nombres");
        return archivo;
    }

    private static void guardarUsuarios() {
        Archivo archivo = genArchivo();

        for (int i = 0; i < cantidad; i++) {
            archivo.agregarDatos(archivo.verCabecera(0), emails[i]);
            archivo.agregarDatos(archivo.verCabecera(1), contrasenias[i]);
            archivo.agregarDatos(archivo.verCabecera(2), nombres[i]);
        }

        archivo.guardar();
    }

    private static void cargarUsuarios() {
        Archivo archivo = genArchivo();
        archivo.leer();

        for (int i = 0; i < archivo.verCantidadDatos(); i++) {
            String email = archivo.verDato(archivo.verCabecera(0), i);
            String contrasenia = archivo.verDato(archivo.verCabecera(1), i);
            String nombre = archivo.verDato(archivo.verCabecera(2), i);

            emails[cantidad] = email;
            nombres[cantidad] = nombre;
            contrasenias[cantidad] = contrasenia;

            cantidad++;
        }
        if (cantidad == 0) {
            String email = "primera@vez.com";
            String nombre = "Nuevo Usuario";
            String contrasenia = "12345678";
            System.out.println("""
                              BIENVENIDO(A)!
                              ESTA ES LA PRIMERA VEZ QUE ABRES LA APLICACION
                              ESPEREMOS QUE TE LA PASES BIEN AQUI, ASI QUE POR
                              ESTE MOMENTO ESPECIAL TE TENEMOS ESTO PARA TI:
                              
                                        O\\/O
                              --------------------------
                              | Email: primera@vez.com |
                              | Contrasenia: 12345678  |
                              --------------------------
                              
                              ES UNA CUENTA PARA QUE PUEDAS EMPEZAR *YA*, AUNQUE
                              ASEGURATE DE CREAR UNA CUENTA NUEVA Y ELIMINAR ESTA
                              POR SEGURIDAD, YA SABES
                               
                              EN FIN, PASALO BIEN Y DISFRUTA! <3
                              """
                    );
            crearUsuario(email, nombre, contrasenia);
        }
    }

    /**
     * Busca un usuario en el sistema utilizando su email.
     *
     * @param email La direccion de correo electronico unica que se desea
     * buscar.
     * @return El indice (posicion) en el arreglo {@code emails} si se
     * encuentra; {@code -1} en caso de que el correo no este registrado.
     */
    public static int buscarUsuario(String email) {
        int indice = -1;
        for (int i = 0; i < cantidad; i++) {
            if (emails[i].equals(email)) {
                indice = i;
                break;
            }
        }
        return indice;
    }

    /**
     * Realiza una busqueda de usuarios cuyos nombres contengan de forma parcial
     * o total el String (texto) especificado.
     *
     * @param nombre Fragmento o nombre completo del usuario por el cual se
     * desea realizar el filtro.
     * @return Un array de enteros que contiene los indices de todos los
     * usuarios que coincidieron con la busqueda. Si no hay coincidencias,
     * devuelve un array vacio (longitud 0).
     */
    public static int[] buscarUsuarios(String nombre) {
        int[] indices = new int[cantidad];
        int cantidadDeEncontrados = 0;
        for (int i = 0; i < cantidad; i++) {
            if (!nombres[i].toLowerCase().contains(nombre.toLowerCase())) {
                continue;
            }
            indices[cantidadDeEncontrados] = i;
            cantidadDeEncontrados++;
        }
        int[] encontrados = new int[cantidadDeEncontrados];
        for (int i = 0; i < cantidadDeEncontrados; i++) {
            encontrados[i] = indices[i];
        }
        return encontrados;
    }

    /**
     * Inserta un nuevo usuario al final de los arreglos del repositorio. Valida
     * que no exceda el limite del almacenamiento y que el correo no este
     * duplicado.
     *
     * @param email Correo electronico unica del nuevo usuario.
     * @param nombre Nombre completo del usuario.
     * @param contrasenia Contrasenia de acceso a la cuenta.
     * @return El indice de la posicion donde fue guardado el usuario
     * exitosamente; {@code -1} si el almacenamiento esta lleno o el correo ya
     * se encuentra registrado.
     */
    public static int crearUsuario(String email, String nombre, String contrasenia) {
        if (buscarUsuario(email) != -1) {
            return -1;
        }
        if (cantidad >= LONGITUD_MAXIMA) {
            return -1;
        }

        emails[cantidad] = email;
        nombres[cantidad] = nombre;
        contrasenias[cantidad] = contrasenia;

        cantidad++;
        guardarUsuarios();

        return cantidad - 1;
    }

    /**
     * Modifica la informacion del perfil del usuario (nombre, contrasenia y
     * fecha de nacimiento) por su correo.
     *
     * @param email El correo de la cuenta que se pretende actualizar.
     * @param nombre El nuevo nombre completo que reemplazara al anterior.
     * @param contrasenia La nueva contrasenia de seguridad.
     * @return El indice del usuario cuyos datos fueron modificados; {@code -1}
     * si el usuario no existe en los registros.
     */
    public static int editarPerfil(String email, String nombre, String contrasenia) {
        int index = buscarUsuario(email);
        if (index == -1) {
            return -1;
        }

        nombres[index] = nombre;
        contrasenias[index] = contrasenia;

        guardarUsuarios();

        return index;
    }

    /**
     * Elimina un usuario del sistema por su correo y contrasenia.
     *
     * @param email El correo electronico del usuario que se desea dar de baja.
     * @param contrasenia La contrasenia para asegurar que si sea el usuario.
     * @return {@code true} si el usuario existia y se elimino correctamente;
     * {@code false} en caso de que no se encontrara el correo indicado.
     */
    public static boolean borrarUsuario(String email, String contrasenia) {
        int index = indiceSiAuth(email, contrasenia);
        if (index == -1) {
            return false;
        }

        for (int i = index; i < cantidad; i++) {
            emails[i] = emails[i + 1];
            contrasenias[i] = contrasenias[i + 1];
            nombres[i] = nombres[i + 1];
        }
        guardarUsuarios();
        cantidad--;

        return true;
    }

    /**
     * Valida las credenciales de acceso de un usuario comparando su correo y
     * contrasenia.
     *
     * @param email El correo ingresado por el usuario.
     * @param contrasenia La contrasenia ingresada por el usuario.
     * @return {@code true} si el usuario existia y la contrasenia coincida;
     * {@code false} en caso de que alguno de los dos datos sea invalido.
     */
    public static boolean auth(String email, String contrasenia) {
        return indiceSiAuth(email, contrasenia) != -1;
    }

    /**
     * Valida las credenciales de acceso de un usuario comparando su correo y
     * contrasenia.
     *
     * @param email El correo ingresado por el usuario.
     * @param contrasenia La contrasenia ingresada por el usuario.
     * @return El indice numerico del usuario si la autenticacion es correcta;
     * {@code -1} si el correo electronico no existe o si la contrasenia no
     * coincide.
     */
    public static int indiceSiAuth(String email, String contrasenia) {
        int index = buscarUsuario(email);
        if (index == -1) {
            return -1;
        }
        if (!contrasenias[index].equals(contrasenia)) {
            return -1;
        }
        return index;
    }

    /**
     * Obtiene el nombre del usuario en un indice dado.
     *
     * @param indice El indice del usuario.
     * @return El nombre.
     */
    public static String verNombre(int indice) {
        return nombres[indice];
    }

    /**
     * Obtiene la cantidad actual de usuarios almacenados.
     *
     * @return El numero de usuarios actualmente.
     */
    public static int verCantidad() {
        return cantidad;
    }

    /**
     * Imprime en la consola la informacion publica basica de un usuario (Nombre
     * y Fecha de Nacimiento) por su posicion en el arreglo.
     *
     * @param indice Posicion del usuario dentro de los arreglos.
     */
    public static void mostrarUsuario(int indice) {
        if (indice < 0 || indice >= cantidad) {
            return;
        }

        System.out.println("Nombre: " + nombres[indice]);
    }

    /**
     * Imprime en la consola la informacion privada y completa de un usuario
     * (Email, Nombre y Fecha) por su posicion en el arreglo. Es utilizado para
     * las vistas de perfil propio de la cuenta.
     */
    public static void mostrarMiUsuario() {
        int indice = buscarUsuario(Sesion.verEmail());
        if (indice < 0 || indice >= cantidad) {
            return;
        }

        System.out.println("Correo: " + emails[indice]);
        System.out.println("Nombre: " + nombres[indice]);
    }

    /**
     * Carga los datos necesarios para el correcto funcionamiento de esta clase.
     */
    public static void cargar() {
        cargarUsuarios();
    }
}
