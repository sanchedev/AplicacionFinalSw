package repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import utils.Archivo;

/**
 * Repositorio de gastos (retiros de dinero de una iglesia para un ministerio).
 */
public class Gastos {

    private static final int LONGITUD_MAXIMA = 1000;
    private static final int[] codigos = new int[LONGITUD_MAXIMA];
    private static final int[] codigoIglesias = new int[LONGITUD_MAXIMA];
    private static final String[] ministerios = new String[LONGITUD_MAXIMA];
    private static final double[] montos = new double[LONGITUD_MAXIMA];
    private static final String[] descripciones = new String[LONGITUD_MAXIMA];
    private static final String[] fechas = new String[LONGITUD_MAXIMA];
    private static int cantidad = 0;

    private static Archivo genArchivo() {
        Archivo archivo = new Archivo("saveds/db/gastos.csv");
        archivo.agregarCabecera("codigos");
        archivo.agregarCabecera("codigoIglesias");
        archivo.agregarCabecera("ministerios");
        archivo.agregarCabecera("montos");
        archivo.agregarCabecera("descripciones");
        archivo.agregarCabecera("fechas");
        return archivo;
    }

    private static void guardarGastos() {
        Archivo archivo = genArchivo();

        for (int i = 0; i < cantidad; i++) {
            archivo.agregarDatos(archivo.verCabecera(0), codigos[i] + "");
            archivo.agregarDatos(archivo.verCabecera(1), codigoIglesias[i] + "");
            archivo.agregarDatos(archivo.verCabecera(2), ministerios[i]);
            archivo.agregarDatos(archivo.verCabecera(3), montos[i] + "");
            archivo.agregarDatos(archivo.verCabecera(4), descripciones[i]);
            archivo.agregarDatos(archivo.verCabecera(5), fechas[i]);
        }

        archivo.guardar();
    }

    private static void cargarGastos() {
        Archivo archivo = genArchivo();
        archivo.leer();

        for (int i = 0; i < archivo.verCantidadDatos(); i++) {
            int codigoIgls = Integer.parseInt(archivo.verDato(archivo.verCabecera(1), i));
            double monto = Double.parseDouble(archivo.verDato(archivo.verCabecera(3), i));

            if (monto <= 0) {
                continue;
            }
            if (Iglesias.buscarIglesia(codigoIgls) == -1) {
                continue;
            }

            codigos[cantidad] = Integer.parseInt(archivo.verDato(archivo.verCabecera(0), i));
            codigoIglesias[cantidad] = codigoIgls;
            ministerios[cantidad] = archivo.verDato(archivo.verCabecera(2), i);
            montos[cantidad] = monto;
            descripciones[cantidad] = archivo.verDato(archivo.verCabecera(4), i);
            fechas[cantidad] = archivo.verDato(archivo.verCabecera(5), i);

            cantidad++;
        }

        guardarGastos();
    }

    public static int buscarGasto(int codigo) {
        int indice = -1;
        for (int i = 0; i < cantidad; i++) {
            if (codigos[i] == codigo) {
                indice = i;
                break;
            }
        }
        return indice;
    }

    public static int[] buscarGastosPorIglesia(int codigoIglesia) {
        int[] indices = new int[cantidad];
        int cantidadDeEncontrados = 0;

        for (int i = 0; i < cantidad; i++) {
            if (codigoIglesias[i] != codigoIglesia) {
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

    public static int crearGasto(int codigoIglesia, String ministerio, double monto, String descripcion) {
        if (cantidad >= LONGITUD_MAXIMA) {
            return -1;
        }

        int codigo = 0;
        if (cantidad > 0) {
            codigo = codigos[cantidad - 1] + 1;
        }

        codigos[cantidad] = codigo;
        codigoIglesias[cantidad] = codigoIglesia;
        ministerios[cantidad] = ministerio;
        montos[cantidad] = monto;
        descripciones[cantidad] = descripcion;
        fechas[cantidad] = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        cantidad++;
        guardarGastos();

        return cantidad - 1;
    }

    public static int verCodigo(int indice) {
        return codigos[indice];
    }

    public static int verCodigoIglesia(int indice) {
        return codigoIglesias[indice];
    }

    public static String verMinisterio(int indice) {
        return ministerios[indice];
    }

    public static double verMonto(int indice) {
        return montos[indice];
    }

    public static String verDescripcion(int indice) {
        return descripciones[indice];
    }

    public static String verFecha(int indice) {
        return fechas[indice];
    }

    public static int verCantidad() {
        return cantidad;
    }

    public static void mostrarGasto(int indice) {
        if (indice < 0 || indice >= cantidad) {
            return;
        }

        System.out.println("Codigo Gasto: " + codigos[indice]);
        System.out.println("Iglesia: " + codigoIglesias[indice]);
        System.out.println("Ministerio: " + ministerios[indice]);
        System.out.println("Monto: S/. " + String.format("%.2f", montos[indice]));
        System.out.println("Descripcion: " + descripciones[indice]);
        System.out.println("Fecha: " + fechas[indice]);
    }

    public static void cargar() {
        cargarGastos();
    }
}
