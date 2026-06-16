/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

/**
 *
 * @author sanchedev
 */
public class Depositos {
    private static final int LONGITUD_MAXIMA = 1000;
    private static final int[] codigos = new int[LONGITUD_MAXIMA];
    private static final String[] dnis = new String[LONGITUD_MAXIMA];
    private static final int[] codigoIglesias = new int[LONGITUD_MAXIMA];
    private static final String[] fechas = new String[LONGITUD_MAXIMA];
    private static final double[] diesmos = new double[LONGITUD_MAXIMA];
    private static final double[] ofrendas = new double[LONGITUD_MAXIMA];
    private static int cantidad = 0;
    
    private static void cargarDepositos() {
        crearDeposito("12345678", "16/06/2026", 50, 10);
    }
    
    /**
     * Busca un depósito en el sistema utilizando su código numérico único.
     * @param codigo El codigo de identificacion numerico que se desea buscar.
     * @return El indice (posicion) en el arreglo {@code codigos} si se encuentra; 
     * {@code -1} en caso de que el codigo no este registrado.
     */
    public static int buscarDeposito(int codigo) {
        int indice = -1;
        for (int i = 0; i < cantidad; i++) {
            if (codigos[i] == codigo) {
                indice = i;
                break;
            }
        }
        return indice;
    }
    
    /** Realiza una búsqueda de depósitos asociados a un miembro mediante su DNI.
     * @param dni El Documento Nacional de Identidad del usuario para filtrar los depósitos.
     * @return Un array de enteros que contiene los índices de todos los depósitos que coincidieron 
     * con el DNI. Si no hay coincidencias, devuelve un array vacío (longitud 0).
     */
    public static int[] buscarDepositosPorDni(String dni) {
        int[] indices = new int[cantidad];
        int cantidadDeEncontrados = 0;

        for (int i = 0; i < cantidad; i++) {
            if (!dnis[i].equals(dni)) continue;
            indices[cantidadDeEncontrados] = i;
            cantidadDeEncontrados++;
        }
        
        int[] encontrados = new int[cantidadDeEncontrados];
        for (int i = 0; i < cantidadDeEncontrados; i++) {
            encontrados[i] = indices[i];
        }
        return encontrados;
    }
    
    /** Inserta un nuevo depósito al final de los arreglos del repositorio. 
     * Valida que no exceda el límite del almacenamiento y que el código no esté duplicado.
     * @param dni            DNI del miembro que realiza el depósito.
     * @param fecha          Fecha del depósito en formato dd/mm/yyyy.
     * @param diezmo         Monto del diezmo.
     * @param ofrenda        Monto de la ofrenda.
     * @return El código del depósito guardado.
     */
     public static int crearDeposito(String dni, String fecha, double diezmo, double ofrenda) {
        if (cantidad >= LONGITUD_MAXIMA) return -1;
        
        int codigo = 0;
        if (cantidad == 0) {
            codigo = codigos[cantidad-1] + 1;
        }
        
        codigos[cantidad] = codigo;
        dnis[cantidad] = dni;
        codigoIglesias[cantidad] = Personas.buscarIglesia(dni);
        fechas[cantidad] = fecha;
        diesmos[cantidad] = diezmo;
        ofrendas[cantidad] = ofrenda;
        
        cantidad++;
        
        return cantidad - 1;
    }

    /**
     * Obtiene la cantidad actual de depósitos almacenados.
     * @return El número de depósitos actualmente.
     */
    public static int verCantidad() {
        return cantidad;
    }
    
    /**
     * Imprime en la consola la información básica de un depósito (Código, DNI y Fecha)
     * por su posición en el arreglo.
     * @param indice Posición del depósito dentro de los arreglos.
     */
    public static void mostrarDeposito(int indice) {
        if (indice < 0 || indice >= cantidad) return;
        
        System.out.println("Código Depósito: " + codigos[indice]);
        System.out.println("DNI Miembro: " + dnis[indice]);
        System.out.println("Fecha: " + fechas[indice]);
    }
    
    /**
     * Imprime en la consola la información detallada y económica del depósito.
     * @param indice Posición del depósito dentro de los arreglos.
     */
    public static void mostrarDetalleDeposito(int indice) {
        if (indice < 0 || indice >= cantidad) return;
        
        int indicePersona = Iglesias.buscarIglesia(codigoIglesias[indice]);
        int indiceIglesia = Iglesias.buscarIglesia(codigoIglesias[indice]);
        
        System.out.println("Código Depósito: " + codigos[indice]);
        System.out.println("Miembro: " + dnis[indice]);
        if (indicePersona == -1) {
            System.out.println(" - Persona no existente");
        } else {
            System.out.println("- DNI: " + Personas.verDNI(indice));
            System.out.println("- Nombre: " + Personas.verNombre(indice));
        }
        System.out.println("Iglesia: " + codigoIglesias[indice]);
        if (indiceIglesia == -1) {
            System.out.println(" - Iglesia no existente");
        } else {
            System.out.println("- Codigo: " + Iglesias.verCodigo(indice));
            System.out.println("- Nombre: " + Iglesias.verNombre(indice));
            System.out.println("- Direccion: " + Iglesias.verDireccion(indice));
        }
        System.out.println("Fecha: " + fechas[indice]);
        System.out.println("Monto Diezmo: S/. " + diesmos[indice]);
        System.out.println("Monto Ofrenda: S/. " + ofrendas[indice]);
    }

    /**
     * Carga los datos necesarios para el correcto funcionamiento de esta clase.
     */
    public static void cargar() {
        cargarDepositos();
    }
}