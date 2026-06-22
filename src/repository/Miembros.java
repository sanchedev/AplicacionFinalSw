package repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import utils.Archivo;
import utils.Constantes;

/**
 * Repositorio de miembros de la iglesia.
 * Unifican Personas + Usuarios: un miembro con rol tiene acceso al sistema.
 */
public class Miembros {

    private static final int LONGITUD_MAXIMA = 1000;
    private static final String[] dnis = new String[LONGITUD_MAXIMA];
    private static final String[] nombresCompletos = new String[LONGITUD_MAXIMA];
    private static final String[] fechasNacimiento = new String[LONGITUD_MAXIMA];
    private static final String[] correos = new String[LONGITUD_MAXIMA];
    private static final String[] telefonos = new String[LONGITUD_MAXIMA];
    private static final int[] codigoIglesias = new int[LONGITUD_MAXIMA];
    private static final int[] codigoDepartamentos = new int[LONGITUD_MAXIMA];
    private static final int[] codigoCargos = new int[LONGITUD_MAXIMA];
    private static final String[] fechasExpiracion = new String[LONGITUD_MAXIMA];
    private static int cantidad = 0;

    private static Archivo genArchivo() {
        Archivo archivo = new Archivo("saveds/db/miembros.csv");
        archivo.agregarCabecera("dnis");
        archivo.agregarCabecera("nombresCompletos");
        archivo.agregarCabecera("fechasNacimiento");
        archivo.agregarCabecera("correos");
        archivo.agregarCabecera("telefonos");
        archivo.agregarCabecera("codigoIglesias");
        archivo.agregarCabecera("codigoDepartamentos");
        archivo.agregarCabecera("codigoCargos");
        archivo.agregarCabecera("fechasExpiracion");
        return archivo;
    }

    private static void guardarMiembros() {
        Archivo archivo = genArchivo();

        for (int i = 0; i < cantidad; i++) {
            archivo.agregarDatos(archivo.verCabecera(0), dnis[i]);
            archivo.agregarDatos(archivo.verCabecera(1), nombresCompletos[i]);
            archivo.agregarDatos(archivo.verCabecera(2), fechasNacimiento[i]);
            archivo.agregarDatos(archivo.verCabecera(3), correos[i]);
            archivo.agregarDatos(archivo.verCabecera(4), telefonos[i]);
            archivo.agregarDatos(archivo.verCabecera(5), codigoIglesias[i] + "");
            archivo.agregarDatos(archivo.verCabecera(6), codigoDepartamentos[i] + "");
            archivo.agregarDatos(archivo.verCabecera(7), codigoCargos[i] + "");
            archivo.agregarDatos(archivo.verCabecera(8), fechasExpiracion[i]);
        }

        archivo.guardar();
    }

    private static void cargarMiembros() {
        Archivo archivo = genArchivo();
        archivo.leer();

        for (int i = 0; i < archivo.verCantidadDatos(); i++) {
            String dni = archivo.verDato(archivo.verCabecera(0), i);
            String fechaNac = archivo.verDato(archivo.verCabecera(2), i);
            String correo = archivo.verDato(archivo.verCabecera(3), i);
            String telefono = archivo.verDato(archivo.verCabecera(4), i);
            int codigoIgls = Integer.parseInt(archivo.verDato(archivo.verCabecera(5), i));

            if (!dni.matches("^\\d{8}$")) {
                continue;
            }
            if (!fechaNac.matches("^\\d{2}-\\d{2}-\\d{4}$")) {
                continue;
            }
            if (!correo.contains("@")) {
                continue;
            }
            if (!telefono.matches("^\\d{9}$")) {
                continue;
            }
            if (codigoIgls != -1 && Iglesias.buscarIglesia(codigoIgls) == -1) {
                continue;
            }

            dnis[cantidad] = dni;
            nombresCompletos[cantidad] = archivo.verDato(archivo.verCabecera(1), i);
            fechasNacimiento[cantidad] = fechaNac;
            correos[cantidad] = correo;
            telefonos[cantidad] = telefono;
            codigoIglesias[cantidad] = codigoIgls;
            codigoDepartamentos[cantidad] = Integer.parseInt(archivo.verDato(archivo.verCabecera(6), i));
            codigoCargos[cantidad] = Integer.parseInt(archivo.verDato(archivo.verCabecera(7), i));
            fechasExpiracion[cantidad] = archivo.verDato(archivo.verCabecera(8), i);

            cantidad++;
        }

        guardarMiembros();
    }

    public static int buscarMiembro(String dni) {
        int indice = -1;
        for (int i = 0; i < cantidad; i++) {
            if (dnis[i].equals(dni)) {
                indice = i;
                break;
            }
        }
        return indice;
    }

    public static int[] buscarMiembros(String nombre) {
        int[] indices = new int[cantidad];
        int cantidadDeEncontrados = 0;
        String filtro = nombre.toLowerCase();

        for (int i = 0; i < cantidad; i++) {
            if (!nombresCompletos[i].toLowerCase().contains(filtro)) {
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

    public static int[] buscarPorDepartamento(int codigoDepto) {
        int[] indices = new int[cantidad];
        int cantidadDeEncontrados = 0;

        for (int i = 0; i < cantidad; i++) {
            if (codigoDepartamentos[i] != codigoDepto) {
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

    public static int crearMiembro(String dni, String nombreCompleto, String fechaNacimiento,
            String correo, String telefono, int codigoIglesia) {
        if (buscarMiembro(dni) != -1) {
            return -1;
        }
        if (cantidad >= LONGITUD_MAXIMA) {
            return -1;
        }

        dnis[cantidad] = dni;
        nombresCompletos[cantidad] = nombreCompleto;
        fechasNacimiento[cantidad] = fechaNacimiento;
        correos[cantidad] = correo;
        telefonos[cantidad] = telefono;
        codigoIglesias[cantidad] = codigoIglesia;
        codigoDepartamentos[cantidad] = -1;
        codigoCargos[cantidad] = -1;
        fechasExpiracion[cantidad] = "";

        cantidad++;
        guardarMiembros();

        return cantidad - 1;
    }

    public static int editarMiembro(String dni, String nombreCompleto, String fechaNacimiento,
            String correo, String telefono, int codigoIglesia) {
        int index = buscarMiembro(dni);
        if (index == -1) {
            return -1;
        }

        nombresCompletos[index] = nombreCompleto;
        fechasNacimiento[index] = fechaNacimiento;
        correos[index] = correo;
        telefonos[index] = telefono;
        codigoIglesias[index] = codigoIglesia;

        guardarMiembros();

        return index;
    }

    public static void asignarRol(String dni, int codigoDepto, int codigoCargo, String fechaExpiracion) {
        int index = buscarMiembro(dni);
        if (index == -1) {
            return;
        }

        codigoDepartamentos[index] = codigoDepto;
        codigoCargos[index] = codigoCargo;
        fechasExpiracion[index] = fechaExpiracion;

        guardarMiembros();
    }

    public static void retirarRol(String dni) {
        int index = buscarMiembro(dni);
        if (index == -1) {
            return;
        }

        codigoDepartamentos[index] = -1;
        codigoCargos[index] = -1;
        fechasExpiracion[index] = "";

        guardarMiembros();
    }

    public static boolean tieneRolActivo(String dni) {
        int index = buscarMiembro(dni);
        if (index == -1) {
            return false;
        }
        return codigoDepartamentos[index] != -1 && !fechasExpiracion[index].isEmpty();
    }

    public static boolean verificarRolExpirado(String dni) {
        int index = buscarMiembro(dni);
        if (index == -1) {
            return false;
        }
        if (fechasExpiracion[index].isEmpty()) {
            return false;
        }

        try {
            LocalDate fechaExp = LocalDate.parse(fechasExpiracion[index],
                    DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            return LocalDate.now().isAfter(fechaExp);
        } catch (Exception e) {
            return false;
        }
    }

    public static String verDNI(int indice) {
        return dnis[indice];
    }

    public static String verNombre(int indice) {
        return nombresCompletos[indice];
    }

    public static String verFechaNacimiento(int indice) {
        return fechasNacimiento[indice];
    }

    public static String verCorreo(int indice) {
        return correos[indice];
    }

    public static String verTelefono(int indice) {
        return telefonos[indice];
    }

    public static int verCodigoIglesia(int indice) {
        return codigoIglesias[indice];
    }

    public static int verCodigoDepartamento(int indice) {
        return codigoDepartamentos[indice];
    }

    public static int verCodigoCargo(int indice) {
        return codigoCargos[indice];
    }

    public static String verFechaExpiracion(int indice) {
        return fechasExpiracion[indice];
    }

    public static int verCantidad() {
        return cantidad;
    }

    public static void mostrarMiembro(int indice) {
        if (indice < 0 || indice >= cantidad) {
            return;
        }

        System.out.println("* DNI: " + dnis[indice] + " | Nombre: " + nombresCompletos[indice]
                + " | Iglesia: " + codigoIglesias[indice]);
    }

    public static void mostrarDetalleMiembro(int indice) {
        if (indice < 0 || indice >= cantidad) {
            return;
        }

        int indiceIglesia = Iglesias.buscarIglesia(codigoIglesias[indice]);

        System.out.println("DNI:             " + dnis[indice]);
        System.out.println("Nombre:          " + nombresCompletos[indice]);
        System.out.println("Fecha Nac:       " + fechasNacimiento[indice]);
        System.out.println("Correo:          " + correos[indice]);
        System.out.println("Telefono:        " + telefonos[indice]);
        System.out.println("Iglesia:         " + (indiceIglesia == -1 ? "Sin iglesia"
                : Iglesias.verNombre(indiceIglesia) + " (codigo " + codigoIglesias[indice] + ")"));
        System.out.println("Departamento:    " + Constantes.verNombreDepartamento(codigoDepartamentos[indice]));
        System.out.println("Cargo:           " + Constantes.verNombreCargo(codigoCargos[indice]));
        System.out.println("Expiracion:      " + (fechasExpiracion[indice].isEmpty() ? "Sin cargo"
                : fechasExpiracion[indice]));
    }

    public static void cargar() {
        cargarMiembros();
    }
}
