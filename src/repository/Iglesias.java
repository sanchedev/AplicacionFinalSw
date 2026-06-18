/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import utils.Archivo;

/**
 *
 * @author sanchedev
 */
public class Iglesias {

    private static final int LONGITUD_MAXIMA = 1000;
    private static final int[] codigos = new int[LONGITUD_MAXIMA];
    private static final String[] nombres = new String[LONGITUD_MAXIMA];
    private static final String[] direcciones = new String[LONGITUD_MAXIMA];
    private static final int[] aforos = new int[LONGITUD_MAXIMA];
    private static int cantidad = 0;

    private static Archivo genArchivo() {
        Archivo archivo = new Archivo("saveds/db/iglesias.csv");
        archivo.agregarCabecera("codigos");
        archivo.agregarCabecera("nombres");
        archivo.agregarCabecera("direcciones");
        archivo.agregarCabecera("aforos");
        return archivo;
    }

    private static void guardarIglesias() {
        Archivo archivo = genArchivo();

        for (int i = 0; i < cantidad; i++) {
            archivo.agregarDatos(archivo.verCabecera(0), codigos[i] + "");
            archivo.agregarDatos(archivo.verCabecera(1), nombres[i]);
            archivo.agregarDatos(archivo.verCabecera(2), direcciones[i]);
            archivo.agregarDatos(archivo.verCabecera(3), aforos[i] + "");
        }

        archivo.guardar();
    }

    private static void cargarIglesias() {
        Archivo archivo = genArchivo();
        archivo.leer();

        for (int i = 0; i < archivo.verCantidadDatos(); i++) {
            String codigo = archivo.verDato(archivo.verCabecera(0), i);
            String nombre = archivo.verDato(archivo.verCabecera(1), i);
            String direccion = archivo.verDato(archivo.verCabecera(2), i);
            String aforo = archivo.verDato(archivo.verCabecera(3), i);

            codigos[cantidad] = Integer.parseInt(codigo);
            nombres[cantidad] = nombre;
            direcciones[cantidad] = direccion;
            aforos[cantidad] = Integer.parseInt(aforo);

            cantidad++;
        }
    }

    /**
     * Busca una iglesia en el sistema utilizando su codigo único.
     *
     * @param codigo El codigo de identificacion unico que se desea buscar.
     * @return El indice (posicion) en el arreglo {@code codigos} si se
     * encuentra; {@code -1} en caso de que el codigo no este registrado.
     */
    public static int buscarIglesia(int codigo) {
        int indice = -1;
        for (int i = 0; i < cantidad; i++) {
            if (codigos[i] == codigo) {
                indice = i;
                break;
            }
        }
        return indice;
    }

    /**
     * Realiza una busqueda de iglesias cuyos nombres contengan de forma parcial
     * o total el String (texto) especificado.
     *
     * @param nombre Fragmento o nombre completo de la iglesia por el cual se
     * desea realizar el filtro.
     * @return Un array de enteros que contiene los indices de todas las
     * iglesias que coincidieron con la busqueda. Si no hay coincidencias,
     * devuelve un array vacio (longitud 0).
     */
    public static int[] buscarIglesias(String nombre) {
        int[] indices = new int[cantidad];
        int cantidadDeEncontrados = 0;
        String filtro = nombre.toLowerCase();

        for (int i = 0; i < cantidad; i++) {
            if (!nombres[i].toLowerCase().contains(filtro)) {
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
     * Inserta una nueva iglesia al final de los arreglos del repositorio.
     * Valida que no exceda el limite del almacenamiento y que el codigo no este
     * duplicado.
     *
     * @param nombre Nombre de la iglesia.
     * @param direccion Direccion fisica de la iglesia.
     * @param aforo Capacidad maxima de personas permitidas.
     */
    public static void agregarIglesia(String nombre, String direccion, int aforo) {
        if (cantidad >= LONGITUD_MAXIMA) {
            return;
        }

        int codigo = 0;
        if (cantidad > 0) {
            codigo = codigos[cantidad - 1] + 1;
        }

        codigos[cantidad] = codigo;
        nombres[cantidad] = nombre;
        direcciones[cantidad] = direccion;
        aforos[cantidad] = aforo;

        cantidad++;
        guardarIglesias();
    }

    /**
     * Modifica la informacion de una iglesia (nombre, direccion y aforo)
     * buscando por su codigo.
     *
     * @param codigo El codigo de la iglesia que se pretende actualizar.
     * @param nombre El nuevo nombre que reemplazara al anterior.
     * @param direccion La nueva direccion.
     * @param aforo El nuevo aforo permitido.
     * @return El indice de la iglesia cuyos datos fueron modificados;
     * {@code -1} si la iglesia no existe en los registros.
     */
    public static int editarIglesia(int codigo, String nombre, String direccion, int aforo) {
        int index = buscarIglesia(codigo);
        if (index == -1) {
            return -1;
        }

        nombres[index] = nombre;
        direcciones[index] = direccion;
        aforos[index] = aforo;

        guardarIglesias();

        return index;
    }

    /**
     * Elimina una iglesia del sistema por su codigo único.
     *
     * @param codigo El codigo unico de la iglesia que se desea dar de baja.
     * @return {@code true} si la iglesia existia y se elimino correctamente;
     * {@code false} en caso de que no se encontrara el codigo indicado.
     */
    public static boolean borrarIglesia(int codigo) {
        int index = buscarIglesia(codigo);
        if (index == -1) {
            return false;
        }

        for (int i = index; i < cantidad - 1; i++) {
            codigos[i] = codigos[i + 1];
            nombres[i] = nombres[i + 1];
            direcciones[i] = direcciones[i + 1];
            aforos[i] = aforos[i + 1];
        }

        guardarIglesias();

        cantidad--;

        int[] feligreses = Personas.buscarPorIglesia(codigo);
        for (int i = 0; i < feligreses.length; i++) {
            Personas.editarPersona(
                    Personas.verDNI(feligreses[i]),
                    Personas.verNombre(feligreses[i]),
                    -1
            );
        }

        return true;
    }

    /**
     * Obtiene el codigo de la iglesia en un indice dado.
     *
     * @param indice El indice de la iglesia.
     * @return El codigo.
     */
    public static int verCodigo(int indice) {
        return codigos[indice];
    }

    /**
     * Obtiene el nombre de la iglesia en un indice dado.
     *
     * @param indice El indice de la iglesia.
     * @return El nombre.
     */
    public static String verNombre(int indice) {
        return nombres[indice];
    }

    /**
     * Obtiene la direccion de la iglesia en un indice dado.
     *
     * @param indice El indice de la iglesia.
     * @return La direccion.
     */
    public static String verDireccion(int indice) {
        return direcciones[indice];
    }

    /**
     * Obtiene el aforo de la iglesia en un indice dado.
     *
     * @param indice El indice de la iglesia.
     * @return La aforo.
     */
    public static int verAforo(int indice) {
        return aforos[indice];
    }

    /**
     * Obtiene la cantidad actual de iglesias almacenadas.
     *
     * @return El numero de iglesias actualmente.
     */
    public static int verCantidad() {
        return cantidad;
    }

    /**
     * Imprime en la consola la una guia de seleccion de iglesias (Codigo y
     * Nombre).
     */
    public static void mostrarGuiaIglesias() {
        System.out.println("Iglesias:");
        for (int i = 0; i < cantidad; i++) {
            System.out.printf("* %d -> %s\n", codigos[i], nombres[i]);
        }
    }

    /**
     * Imprime en la consola la informacion basica de una iglesia (Nombre y
     * Direccion) por su posicion en el arreglo.
     *
     * @param indice Posicion de la iglesia dentro de los arreglos.
     */
    public static void mostrarIglesia(int indice) {
        if (indice < 0 || indice >= cantidad) {
            return;
        }

        System.out.println("Nombre: " + nombres[indice]);
        System.out.println("Dirección: " + direcciones[indice]);
    }

    /**
     * Imprime en la consola la informacion completa de una iglesia (Codigo,
     * Nombre, Direccion y Aforo) por su posicion en el arreglo.
     *
     * @param indice Posicion de la iglesia dentro de los arreglos.
     */
    public static void mostrarDetalleIglesia(int indice) {
        if (indice < 0 || indice >= cantidad) {
            return;
        }

        System.out.println("Código: " + codigos[indice]);
        System.out.println("Nombre: " + nombres[indice]);
        System.out.println("Dirección: " + direcciones[indice]);
        System.out.println("Aforo: " + aforos[indice]);
    }

    /**
     * Carga los datos necesarios para el correcto funcionamiento de esta clase.
     */
    public static void cargar() {
        cargarIglesias();
    }
}
