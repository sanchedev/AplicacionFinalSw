/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import utils.Archivo;
import utils.Recibo;

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
    private static final double[] diezmos = new double[LONGITUD_MAXIMA];
    private static final double[] ofrendas = new double[LONGITUD_MAXIMA];
    private static int cantidad = 0;

    private static Archivo genArchivo() {
        Archivo archivo = new Archivo("saveds/db/depositos.csv");
        archivo.agregarCabecera("codigos");
        archivo.agregarCabecera("dnis");
        archivo.agregarCabecera("codigoIglesias");
        archivo.agregarCabecera("fechas");
        archivo.agregarCabecera("diezmos");
        archivo.agregarCabecera("ofrendas");
        return archivo;
    }

    private static void guardarDepositos() {
        Archivo archivo = genArchivo();

        for (int i = 0; i < cantidad; i++) {
            archivo.agregarDatos(archivo.verCabecera(0), codigos[i] + "");
            archivo.agregarDatos(archivo.verCabecera(1), dnis[i]);
            archivo.agregarDatos(archivo.verCabecera(2), codigoIglesias[i] + "");
            archivo.agregarDatos(archivo.verCabecera(3), fechas[i]);
            archivo.agregarDatos(archivo.verCabecera(4), diezmos[i] + "");
            archivo.agregarDatos(archivo.verCabecera(5), ofrendas[i] + "");
        }

        archivo.guardar();
    }

    private static void cargarDepositos() {
        Archivo archivo = genArchivo();
        archivo.leer();

        for (int i = 0; i < archivo.verCantidadDatos(); i++) {
            String codigo = archivo.verDato(archivo.verCabecera(0), i);
            String dni = archivo.verDato(archivo.verCabecera(1), i);
            String codigoIglesia = archivo.verDato(archivo.verCabecera(2), i);
            String fecha = archivo.verDato(archivo.verCabecera(3), i);
            String diezmo = archivo.verDato(archivo.verCabecera(4), i);
            String ofrenda = archivo.verDato(archivo.verCabecera(5), i);

            codigos[cantidad] = Integer.parseInt(codigo);
            dnis[cantidad] = dni;
            codigoIglesias[cantidad] = Integer.parseInt(codigoIglesia);
            fechas[cantidad] = fecha;
            diezmos[cantidad] = Double.parseDouble(diezmo);
            ofrendas[cantidad] = Double.parseDouble(ofrenda);

            cantidad++;
        }
    }

    /**
     * Busca un depósito en el sistema utilizando su código numérico único.
     *
     * @param codigo El codigo de identificacion numerico que se desea buscar.
     * @return El indice (posicion) en el arreglo {@code codigos} si se
     * encuentra; {@code -1} en caso de que el codigo no este registrado.
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

    /**
     * Realiza una búsqueda de depósitos asociados a un miembro mediante su DNI.
     *
     * @param dni El Documento Nacional de Identidad del usuario para filtrar
     * los depósitos.
     * @return Un array de enteros que contiene los índices de todos los
     * depósitos que coincidieron con el DNI. Si no hay coincidencias, devuelve
     * un array vacío (longitud 0).
     */
    public static int[] buscarDepositosPorDni(String dni) {
        int[] indices = new int[cantidad];
        int cantidadDeEncontrados = 0;

        for (int i = 0; i < cantidad; i++) {
            if (!dnis[i].equals(dni)) {
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
     * Realiza una búsqueda de depósitos asociados a una iglesia mediante su
     * codigo.
     *
     * @param codigo El codigo de iglesia para filtrar los depósitos.
     * @return Un array de enteros que contiene los índices de todos los
     * depósitos que coincidieron con la iglesia. Si no hay coincidencias,
     * devuelve un array vacío (longitud 0).
     */
    public static int[] buscarDepositoPorIglesia(int codigo) {
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
     * Inserta un nuevo depósito al final de los arreglos del repositorio.
     * Valida que no exceda el límite del almacenamiento y que el código no esté
     * duplicado.
     *
     * @param dni DNI del miembro que realiza el depósito o vacio si es anonimo.
     * @param iglesia Iglesia al que se le depositara el monto.
     * @param diezmo Monto del diezmo.
     * @param ofrenda Monto de la ofrenda.
     * @return (-1) si no pudo crear el deposito e (indice del deposito) si pudo
     * crearlo.
     */
    public static int crearDeposito(String dni, int iglesia, double diezmo, double ofrenda) {
        if (cantidad >= LONGITUD_MAXIMA) {
            return -1;
        }

        int codigo = 0;
        if (cantidad > 0) {
            codigo = codigos[cantidad - 1] + 1;
        }

        if (dni.isEmpty()) {
            dni = "*anonimo";
        }

        codigos[cantidad] = codigo;
        dnis[cantidad] = dni;
        codigoIglesias[cantidad] = iglesia;
        fechas[cantidad] = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        diezmos[cantidad] = diezmo;
        ofrendas[cantidad] = ofrenda;

        cantidad++;
        guardarDepositos();

        return cantidad - 1;
    }

    public static void imprimirDeposito(int indice) {
        Recibo recibo = new Recibo(indice);
        recibo.abrir();
    }

    /**
     * Obtiene el codigo de un depósito en un índice dado.
     *
     * @param indice El indice del depósito
     * @return El código del deposito.
     */
    public static int verCodigo(int indice) {
        return codigos[indice];
    }

    /**
     * Obtiene el dni del que hizo un depósito en un índice dado.
     *
     * @param indice El indice del depósito
     * @return El dni del hacedor del deposito.
     */
    public static String verDNI(int indice) {
        return dnis[indice];
    }

    /**
     * Obtiene el codigo de iglesia de un depósito en un índice dado.
     *
     * @param indice El indice del depósito
     * @return El codigo de iglesia del deposito.
     */
    public static int verCodigoIglesia(int indice) {
        return codigoIglesias[indice];
    }

    /**
     * Obtiene la fecha de un depósito en un índice dado.
     *
     * @param indice El indice del depósito
     * @return La fecha del deposito.
     */
    public static String verFecha(int indice) {
        return fechas[indice];
    }

    /**
     * Obtiene el diezmo de un depósito en un índice dado.
     *
     * @param indice El indice del depósito
     * @return El diezmo del deposito.
     */
    public static double verDiezmo(int indice) {
        return diezmos[indice];
    }

    /**
     * Obtiene la ofrenda de un depósito en un índice dado.
     *
     * @param indice El indice del depósito
     * @return La ofrenda del deposito.
     */
    public static double verOfrenda(int indice) {
        return ofrendas[indice];
    }

    /**
     * Obtiene la cantidad actual de depósitos almacenados.
     *
     * @return El número de depósitos actualmente.
     */
    public static int verCantidad() {
        return cantidad;
    }

    /**
     * Imprime en la consola la información básica de un depósito (Código, DNI y
     * Fecha) por su posición en el arreglo.
     *
     * @param indice Posición del depósito dentro de los arreglos.
     */
    public static void mostrarDeposito(int indice) {
        if (indice < 0 || indice >= cantidad) {
            return;
        }

        System.out.println("Código Depósito: " + codigos[indice]);
        System.out.println("DNI Miembro: " + dnis[indice]);
        System.out.println("Codigo de Iglesia: " + codigoIglesias[indice]);
        System.out.println("Fecha: " + fechas[indice]);
        System.out.println("Monto Diezmo: S/. " + String.format("%.2f", diezmos[indice]));
        System.out.println("Monto Ofrenda: S/. " + String.format("%.2f", ofrendas[indice]));
    }

    /**
     * Imprime en la consola la información detallada y económica del depósito.
     *
     * @param indice Posición del depósito dentro de los arreglos.
     */
    public static void mostrarDetalleDeposito(int indice) {
        if (indice < 0 || indice >= cantidad) {
            return;
        }

        int indicePersona = Iglesias.buscarIglesia(codigoIglesias[indice]);
        int indiceIglesia = Iglesias.buscarIglesia(codigoIglesias[indice]);

        System.out.println("Código Depósito: " + codigos[indice]);
        System.out.println("Miembro: " + dnis[indice]);
        if (indicePersona == -1) {
            System.out.println(" - Anonimo");
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
        System.out.println("Monto Diezmo: S/. " + String.format("%.2f", diezmos[indice]));
        System.out.println("Monto Ofrenda: S/. " + String.format("%.2f", ofrendas[indice]));
    }

    /**
     * Carga los datos necesarios para el correcto funcionamiento de esta clase.
     */
    public static void cargar() {
        cargarDepositos();
    }
}
