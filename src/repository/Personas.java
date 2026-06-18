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
public class Personas {

    private static final int LONGITUD_MAXIMA = 1000;
    private static final String[] dnis = new String[LONGITUD_MAXIMA];
    private static final String[] nombres = new String[LONGITUD_MAXIMA];
    private static final int[] codigoIglesias = new int[LONGITUD_MAXIMA];
    private static int cantidad = 0;

    private static Archivo genArchivo() {
        Archivo archivo = new Archivo("saveds/db/personas.csv");
        archivo.agregarCabecera("dnis");
        archivo.agregarCabecera("nombres");
        archivo.agregarCabecera("codigoIglesias");
        return archivo;
    }

    private static void guardarPersonas() {
        Archivo archivo = genArchivo();

        for (int i = 0; i < cantidad; i++) {
            archivo.agregarDatos(archivo.verCabecera(0), dnis[i]);
            archivo.agregarDatos(archivo.verCabecera(1), nombres[i]);
            archivo.agregarDatos(archivo.verCabecera(2), codigoIglesias[i] + "");
        }

        archivo.guardar();
    }

    private static void cargarPersonas() {
        Archivo archivo = genArchivo();
        archivo.leer();

        for (int i = 0; i < archivo.verCantidadDatos(); i++) {
            String dni = archivo.verDato(archivo.verCabecera(0), i);
            String nombre = archivo.verDato(archivo.verCabecera(1), i);
            String codigoIglesia = archivo.verDato(archivo.verCabecera(2), i);

            dnis[cantidad] = dni;
            nombres[cantidad] = nombre;
            codigoIglesias[cantidad] = Integer.parseInt(codigoIglesia);

            cantidad++;
        }
    }

    /**
     * Busca la posición de una persona en el sistema utilizando su DNI.
     *
     * @param dni El Documento Nacional de Identidad que se desea buscar.
     * @return El indice (posicion) en el arreglo {@code dnis} si se encuentra;
     * {@code -1} en caso de que el DNI no este registrado.
     */
    public static int buscarPersona(String dni) {
        int indice = -1;
        for (int i = 0; i < cantidad; i++) {
            if (dnis[i].equals(dni)) {
                indice = i;
                break;
            }
        }
        return indice;
    }

    /**
     * Devuelve el código numérico de la iglesia a la que pertenece una persona
     * mediante su DNI.
     *
     * @param dni El DNI de la persona a consultar.
     * @return El código de la iglesia convertido a entero; {@code -1} si la
     * persona no existe.
     */
    public static int buscarIglesia(String dni) {
        int index = buscarPersona(dni);
        if (index == -1) {
            return -1;
        }

        return codigoIglesias[index];
    }

    /**
     * Realiza una busqueda de personas cuyos codigos de iglesia sean el codigo
     * ingresado.
     *
     * @param codigo El codigo de iglesia de la persona a consultar.
     * @return Un array de enteros que contiene los indices de todas las
     * personas que coincidieron con la busqueda. Si no hay coincidencias,
     * devuelve un array vacio (longitud 0).
     */
    public static int[] buscarPorIglesia(int codigo) {
        int[] indices = new int[cantidad];
        int cantidadDeEncontrados = 0;

        for (int i = 0; i < cantidad; i++) {
            if (codigoIglesias[i] != codigo) {
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
     * Realiza una busqueda de personas cuyos nombres contengan de forma parcial
     * o total el String (texto) especificado.
     *
     * @param nombre Fragmento o nombre completo de la persona por el cual se
     * desea realizar el filtro.
     * @return Un array de enteros que contiene los indices de todas las
     * personas que coincidieron con la busqueda. Si no hay coincidencias,
     * devuelve un array vacio (longitud 0).
     */
    public static int[] buscarPersonas(String nombre) {
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
     * Inserta una nueva persona al final de los arreglos del repositorio.
     * Valida que no exceda el limite del almacenamiento y que el DNI no este
     * duplicado.
     *
     * @param dni DNI único de la persona.
     * @param nombre Nombre completo de la persona.
     * @param codigoIglesia Código de la iglesia a la que pertenece (para
     * indefinido usa {@code -1}).
     */
    public static void crearPersona(String dni, String nombre, int codigoIglesia) {
        if (buscarPersona(dni) != -1) {
            return;
        }
        if (cantidad >= LONGITUD_MAXIMA) {
            return;
        }

        dnis[cantidad] = dni;
        nombres[cantidad] = nombre;
        codigoIglesias[cantidad] = codigoIglesia;

        cantidad++;
        guardarPersonas();
    }

    /**
     * Modifica la informacion de una persona (nombre y código de iglesia)
     * buscando por su DNI.
     *
     * @param dni El DNI de la persona que se pretende actualizar.
     * @param nombre El nuevo nombre completo.
     * @param codigoIglesia El nuevo código de la iglesia (para indefinido usa
     * {@code -1}).
     * @return El indice de la persona cuyos datos fueron modificados;
     * {@code -1} si la persona no existe en los registros.
     */
    public static int editarPersona(String dni, String nombre, int codigoIglesia) {
        int index = buscarPersona(dni);
        if (index == -1) {
            return -1;
        }

        nombres[index] = nombre;
        codigoIglesias[index] = codigoIglesia;

        guardarPersonas();

        return index;
    }

    /**
     * Obtiene el dni de la persona en un indice dado.
     *
     * @param indice El indice de la persona.
     * @return El dni.
     */
    public static String verDNI(int indice) {
        return dnis[indice];
    }

    /**
     * Obtiene el nombre de la persona en un indice dado.
     *
     * @param indice El indice de la persona.
     * @return El nombre.
     */
    public static String verNombre(int indice) {
        return nombres[indice];
    }

    /**
     * Obtiene el codigo de iglesia de la persona en un indice dado.
     *
     * @param indice El indice de la persona.
     * @return El codigo.
     */
    public static int verCodigoIglesia(int indice) {
        return codigoIglesias[indice];
    }

    /**
     * Obtiene la cantidad actual de personas almacenadas.
     *
     * @return El numero de personas actualmente.
     */
    public static int verCantidad() {
        return cantidad;
    }

    /**
     * Imprime en la consola la informacion basica de una persona (Nombre y DNI)
     * por su posicion en el arreglo.
     *
     * @param indice Posicion de la persona dentro de los arreglos.
     */
    public static void mostrarPersona(int indice) {
        if (indice < 0 || indice >= cantidad) {
            return;
        }

        System.out.println("Nombre: " + nombres[indice]);
        System.out.println("DNI: " + dnis[indice]);
        System.out.println("Código de Iglesia: " + codigoIglesias[indice]);
    }

    /**
     * Imprime en la consola la informacion completa de una persona.
     *
     * @param indice Posicion de la persona dentro de los arreglos.
     */
    public static void mostrarDetallePersona(int indice) {
        if (indice < 0 || indice >= cantidad) {
            return;
        }

        int indiceIglesia = Iglesias.buscarIglesia(codigoIglesias[indice]);

        System.out.println("DNI: " + dnis[indice]);
        System.out.println("Nombre: " + nombres[indice]);
        System.out.println("Iglesia: ");
        if (indiceIglesia == -1) {
            System.out.println(" - Iglesia no existente");
        } else {
            System.out.println("- Codigo: " + Iglesias.verCodigo(indice));
            System.out.println("- Nombre: " + Iglesias.verNombre(indice));
        }
    }

    /**
     * Carga los datos necesarios para el correcto funcionamiento de esta clase.
     */
    public static void cargar() {
        cargarPersonas();
    }
}
