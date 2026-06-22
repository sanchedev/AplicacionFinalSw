package repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import utils.Archivo;
import utils.Recibo;

/**
 * Repositorio de depositos financieros de la iglesia.
 * Cada deposito registra 4 tipos de monto: diezmo, ofrenda sistematica,
 * proyecto local y pagos a instituciones.
 */
public class Depositos {

    private static final int LONGITUD_MAXIMA = 1000;
    private static final int[] codigos = new int[LONGITUD_MAXIMA];
    private static final String[] dnis = new String[LONGITUD_MAXIMA];
    private static final int[] codigoIglesias = new int[LONGITUD_MAXIMA];
    private static final String[] fechas = new String[LONGITUD_MAXIMA];
    private static final double[] diezmos = new double[LONGITUD_MAXIMA];
    private static final double[] ofrendasSistematicas = new double[LONGITUD_MAXIMA];
    private static final double[] proyectosLocales = new double[LONGITUD_MAXIMA];
    private static final double[] pagosInstituciones = new double[LONGITUD_MAXIMA];
    private static int cantidad = 0;

    private static Archivo genArchivo() {
        Archivo archivo = new Archivo("saveds/db/depositos.csv");
        archivo.agregarCabecera("codigos");
        archivo.agregarCabecera("dnis");
        archivo.agregarCabecera("codigoIglesias");
        archivo.agregarCabecera("fechas");
        archivo.agregarCabecera("diezmos");
        archivo.agregarCabecera("ofrendasSistematicas");
        archivo.agregarCabecera("proyectosLocales");
        archivo.agregarCabecera("pagosInstituciones");
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
            archivo.agregarDatos(archivo.verCabecera(5), ofrendasSistematicas[i] + "");
            archivo.agregarDatos(archivo.verCabecera(6), proyectosLocales[i] + "");
            archivo.agregarDatos(archivo.verCabecera(7), pagosInstituciones[i] + "");
        }

        archivo.guardar();
    }

    private static void cargarDepositos() {
        Archivo archivo = genArchivo();
        archivo.leer();

        for (int i = 0; i < archivo.verCantidadDatos(); i++) {
            int codigoIgls = Integer.parseInt(archivo.verDato(archivo.verCabecera(2), i));
            double diezmo = Double.parseDouble(archivo.verDato(archivo.verCabecera(4), i));
            double ofrendaSist = Double.parseDouble(archivo.verDato(archivo.verCabecera(5), i));
            double proyectoLoc = Double.parseDouble(archivo.verDato(archivo.verCabecera(6), i));
            double pagoInst = Double.parseDouble(archivo.verDato(archivo.verCabecera(7), i));
            double total = diezmo + ofrendaSist + proyectoLoc + pagoInst;

            if (total <= 0) {
                continue;
            }
            if (Iglesias.buscarIglesia(codigoIgls) == -1) {
                continue;
            }

            codigos[cantidad] = Integer.parseInt(archivo.verDato(archivo.verCabecera(0), i));
            dnis[cantidad] = archivo.verDato(archivo.verCabecera(1), i);
            codigoIglesias[cantidad] = codigoIgls;
            fechas[cantidad] = archivo.verDato(archivo.verCabecera(3), i);
            diezmos[cantidad] = diezmo;
            ofrendasSistematicas[cantidad] = ofrendaSist;
            proyectosLocales[cantidad] = proyectoLoc;
            pagosInstituciones[cantidad] = pagoInst;

            cantidad++;
        }

        guardarDepositos();
    }

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

    public static int crearDeposito(String dni, int iglesia, double diezmo,
            double ofrendaSistem, double proyectoLocal, double pagoInstitucion) {
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
        ofrendasSistematicas[cantidad] = ofrendaSistem;
        proyectosLocales[cantidad] = proyectoLocal;
        pagosInstituciones[cantidad] = pagoInstitucion;

        cantidad++;
        guardarDepositos();

        return cantidad - 1;
    }

    public static void imprimirDeposito(int indice) {
        Recibo recibo = new Recibo(indice);
        recibo.abrir();
    }

    public static int verCodigo(int indice) {
        return codigos[indice];
    }

    public static String verDNI(int indice) {
        return dnis[indice];
    }

    public static int verCodigoIglesia(int indice) {
        return codigoIglesias[indice];
    }

    public static String verFecha(int indice) {
        return fechas[indice];
    }

    public static double verDiezmo(int indice) {
        return diezmos[indice];
    }

    public static double verOfrendaSistemtica(int indice) {
        return ofrendasSistematicas[indice];
    }

    public static double verProyectoLocal(int indice) {
        return proyectosLocales[indice];
    }

    public static double verPagoInstitucion(int indice) {
        return pagosInstituciones[indice];
    }

    public static int verCantidad() {
        return cantidad;
    }

    public static void mostrarDeposito(int indice) {
        if (indice < 0 || indice >= cantidad) {
            return;
        }

        System.out.println("Codigo Deposito: " + codigos[indice]);
        System.out.println("DNI Miembro: " + dnis[indice]);
        System.out.println("Codigo de Iglesia: " + codigoIglesias[indice]);
        System.out.println("Fecha: " + fechas[indice]);
        System.out.println("Diezmo:           S/. " + String.format("%.2f", diezmos[indice]));
        System.out.println("Ofrenda Sistematica: S/. " + String.format("%.2f", ofrendasSistematicas[indice]));
        System.out.println("Proyecto Local:      S/. " + String.format("%.2f", proyectosLocales[indice]));
        System.out.println("Pago Instituciones:  S/. " + String.format("%.2f", pagosInstituciones[indice]));
        System.out.println("Total:               S/. " + String.format("%.2f", verTotal(indice)));
    }

    public static void mostrarDetalleDeposito(int indice) {
        if (indice < 0 || indice >= cantidad) {
            return;
        }

        int indicePersona = Miembros.buscarMiembro(dnis[indice]);
        int indiceIglesia = Iglesias.buscarIglesia(codigoIglesias[indice]);

        System.out.println("Codigo Deposito: " + codigos[indice]);
        System.out.println("Miembro: " + dnis[indice]);
        if (indicePersona == -1) {
            System.out.println(" - Anonimo");
        } else {
            System.out.println("- DNI: " + Miembros.verDNI(indicePersona));
            System.out.println("- Nombre: " + Miembros.verNombre(indicePersona));
        }
        System.out.println("Iglesia: " + codigoIglesias[indice]);
        if (indiceIglesia == -1) {
            System.out.println(" - Iglesia no existente");
        } else {
            System.out.println("- Codigo: " + Iglesias.verCodigo(indiceIglesia));
            System.out.println("- Nombre: " + Iglesias.verNombre(indiceIglesia));
            System.out.println("- Direccion: " + Iglesias.verDireccion(indiceIglesia));
        }
        System.out.println("Fecha: " + fechas[indice]);
        System.out.println("Diezmo:              S/. " + String.format("%.2f", diezmos[indice]));
        System.out.println("Ofrenda Sistematica: S/. " + String.format("%.2f", ofrendasSistematicas[indice]));
        System.out.println("Proyecto Local:      S/. " + String.format("%.2f", proyectosLocales[indice]));
        System.out.println("Pago Instituciones:  S/. " + String.format("%.2f", pagosInstituciones[indice]));
        System.out.println("Total:               S/. " + String.format("%.2f", verTotal(indice)));
    }

    public static double verTotal(int indice) {
        return diezmos[indice] + ofrendasSistematicas[indice]
                + proyectosLocales[indice] + pagosInstituciones[indice];
    }

    public static void cargar() {
        cargarDepositos();
    }
}
