/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

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
    
    private static void cargarPersonas() {
        crearPersona("12345678", "Juan Perez", 1);
    }
    
    /**
     * Busca la posición de una persona en el sistema utilizando su DNI.
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
     * Devuelve el código numérico de la iglesia a la que pertenece una persona mediante su DNI.
     * @param dni El DNI de la persona a consultar.
     * @return El código de la iglesia convertido a entero; {@code -1} si la persona no existe.
     */
    public static int buscarIglesia(String dni) {
        int index = buscarPersona(dni);
        if (index == -1) return -1;
        
        // Convierte el String del código de la iglesia a int para cumplir con la firma del método
        return codigoIglesias[index];
    }
    
    /** Realiza una busqueda de personas cuyos nombres contengan de forma parcial o total
     * el String (texto) especificado.
     * @param nombre Fragmento o nombre completo de la persona por el cual se desea realizar el filtro.
     * @return Un array de enteros que contiene los indices de todas las personas que coincidieron 
     * con la busqueda. Si no hay coincidencias, devuelve un array vacio (longitud 0).
     */
    public static int[] buscarPersonas(String nombre) {
        int[] indices = new int[cantidad];
        int cantidadDeEncontrados = 0;
        String filtro = nombre.toLowerCase();

        for (int i = 0; i < cantidad; i++) {
            if (!nombres[i].toLowerCase().contains(filtro)) continue;
            indices[cantidadDeEncontrados] = i;
            cantidadDeEncontrados++;
        }
        
        int[] encontrados = new int[cantidadDeEncontrados];
        for (int i = 0; i < cantidadDeEncontrados; i++) {
            encontrados[i] = indices[i];
        }
        return encontrados;
    }
    
    /** Inserta una nueva persona al final de los arreglos del repositorio. 
     * Valida que no exceda el limite del almacenamiento y que el DNI no este duplicado.
     * @param dni            DNI único de la persona.
     * @param nombre         Nombre completo de la persona.
     * @param codigoIglesia  Código de la iglesia a la que pertenece.
     * @return El indice de la posicion donde fue guardada la persona exitosamente; 
     * {@code -1} si el almacenamiento esta lleno o el DNI ya se encuentra registrado.
     */
     public static int crearPersona(String dni, String nombre, int codigoIglesia) {
        if (buscarPersona(dni) != -1) return -1;
        if (cantidad >= LONGITUD_MAXIMA) return -1;
        
        dnis[cantidad] = dni;
        nombres[cantidad] = nombre;
        codigoIglesias[cantidad] = codigoIglesia;
        
        cantidad++;
        
        return cantidad - 1;
    }
    
    /**
     * Modifica la informacion de una persona (nombre y código de iglesia) buscando por su DNI.
     * @param dni            El DNI de la persona que se pretende actualizar.
     * @param nombre         El nuevo nombre completo.
     * @param codigoIglesia  El nuevo código de la iglesia.
     * @return El indice de la persona cuyos datos fueron modificados; 
     * {@code -1} si la persona no existe en los registros.
     */
    public static int editarPersona(String dni, String nombre, int codigoIglesia) {
        int index = buscarPersona(dni);
        if (index == -1) return -1;
        
        nombres[index] = nombre;
        codigoIglesias[index] = codigoIglesia;
        
        return index;
    }
        
    /**
     * Obtiene el dni de la persona en un indice dado.
     * @param indice El indice de la persona.
     * @return El dni.
     */
    public static String verDNI(int indice) {
        return dnis[indice];
    }
    
    /**
     * Obtiene el nombre de la persona en un indice dado.
     * @param indice El indice de la persona.
     * @return El nombre.
     */
    public static String verNombre(int indice) {
        return nombres[indice];
    }
    
    /**
     * Obtiene la cantidad actual de personas almacenadas.
     * @return El numero de personas actualmente.
     */
    public static int verCantidad() {
        return cantidad;
    }
    
    /**
     * Imprime en la consola la informacion basica de una persona (Nombre y DNI)
     * por su posicion en el arreglo.
     * @param indice Posicion de la persona dentro de los arreglos.
     */
    public static void mostrarPersona(int indice) {
        if (indice < 0 || indice >= cantidad) return;
        
        System.out.println("Nombre: " + nombres[indice]);
        System.out.println("DNI: " + dnis[indice]);
        System.out.println("Código de Iglesia: " + codigoIglesias[indice]);
    }
    
    /**
     * Imprime en la consola la informacion completa de una persona.
     * @param indice Posicion de la persona dentro de los arreglos.
     */
    public static void mostrarDetallePersona(int indice) {
        if (indice < 0 || indice >= cantidad) return;
        
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