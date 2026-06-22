package repository;

import utils.Archivo;

/**
 * Credenciales de acceso al sistema.
 * Cada credencial esta vinculada a un miembro por su DNI.
 */
public class Credenciales {

    private static final int LONGITUD_MAXIMA = 1000;
    private static final String[] emails = new String[LONGITUD_MAXIMA];
    private static final String[] contrasenias = new String[LONGITUD_MAXIMA];
    private static final String[] dnisMiembros = new String[LONGITUD_MAXIMA];
    private static int cantidad = 0;

    private static Archivo genArchivo() {
        Archivo archivo = new Archivo("saveds/db/credenciales.csv");
        archivo.agregarCabecera("emails");
        archivo.agregarCabecera("contrasenias");
        archivo.agregarCabecera("dnisMiembros");
        return archivo;
    }

    private static void guardarCredenciales() {
        Archivo archivo = genArchivo();

        for (int i = 0; i < cantidad; i++) {
            archivo.agregarDatos(archivo.verCabecera(0), emails[i]);
            archivo.agregarDatos(archivo.verCabecera(1), contrasenias[i]);
            archivo.agregarDatos(archivo.verCabecera(2), dnisMiembros[i]);
        }

        archivo.guardar();
    }

    private static void cargarCredenciales() {
        Archivo archivo = genArchivo();
        archivo.leer();

        for (int i = 0; i < archivo.verCantidadDatos(); i++) {
            String email = archivo.verDato(archivo.verCabecera(0), i);
            String contrasenia = archivo.verDato(archivo.verCabecera(1), i);
            String dniMiembro = archivo.verDato(archivo.verCabecera(2), i);

            if (!email.contains("@")) {
                continue;
            }
            if (contrasenia.trim().isEmpty()) {
                continue;
            }
            if (!dniMiembro.matches("^\\d{8}$")) {
                continue;
            }

            emails[cantidad] = email;
            contrasenias[cantidad] = contrasenia;
            dnisMiembros[cantidad] = dniMiembro;

            cantidad++;
        }

        guardarCredenciales();
    }

    public static int crearCredencial(String email, String contrasenia, String dniMiembro) {
        if (buscarPorEmail(email) != -1) {
            return -1;
        }
        if (cantidad >= LONGITUD_MAXIMA) {
            return -1;
        }

        emails[cantidad] = email;
        contrasenias[cantidad] = contrasenia;
        dnisMiembros[cantidad] = dniMiembro;

        cantidad++;
        guardarCredenciales();

        return cantidad - 1;
    }

    public static boolean eliminarCredencial(String dniMiembro) {
        int index = buscarPorDni(dniMiembro);
        if (index == -1) {
            return false;
        }

        for (int i = index; i < cantidad - 1; i++) {
            emails[i] = emails[i + 1];
            contrasenias[i] = contrasenias[i + 1];
            dnisMiembros[i] = dnisMiembros[i + 1];
        }

        cantidad--;
        guardarCredenciales();

        return true;
    }

    public static boolean auth(String email, String contrasenia) {
        return indiceSiAuth(email, contrasenia) != -1;
    }

    public static int indiceSiAuth(String email, String contrasenia) {
        int index = buscarPorEmail(email);
        if (index == -1) {
            return -1;
        }
        if (!contrasenias[index].equals(contrasenia)) {
            return -1;
        }
        return index;
    }

    public static boolean tieneAcceso(String dniMiembro) {
        int index = buscarPorDni(dniMiembro);
        if (index == -1) {
            return false;
        }
        return !contrasenias[index].isEmpty();
    }

    public static int buscarPorEmail(String email) {
        int indice = -1;
        for (int i = 0; i < cantidad; i++) {
            if (emails[i].equals(email)) {
                indice = i;
                break;
            }
        }
        return indice;
    }

    public static int buscarPorDni(String dniMiembro) {
        int indice = -1;
        for (int i = 0; i < cantidad; i++) {
            if (dnisMiembros[i].equals(dniMiembro)) {
                indice = i;
                break;
            }
        }
        return indice;
    }

    public static String verEmail(int indice) {
        return emails[indice];
    }

    public static String verDniMiembro(int indice) {
        return dnisMiembros[indice];
    }

    public static int verCantidad() {
        return cantidad;
    }

    public static void mostrarCredencial(int indice) {
        if (indice < 0 || indice >= cantidad) {
            return;
        }

        int indiceMiembro = Miembros.buscarMiembro(dnisMiembros[indice]);
        String nombre = indiceMiembro != -1 ? Miembros.verNombre(indiceMiembro) : "Desconocido";

        System.out.println("Email: " + emails[indice] + " | Miembro: " + nombre + " (DNI: " + dnisMiembros[indice] + ")");
    }

    public static void cargar() {
        cargarCredenciales();
    }
}
