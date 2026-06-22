package vistacontrol;

import repository.Credenciales;
import repository.Iglesias;
import repository.Miembros;
import utils.Constantes;
import utils.Correo;
import utils.Errores;
import utils.Lector;
import utils.Validaciones;

/**
 * Gestion de miembros: CRUD, asignacion de roles y filtro por departamento.
 */
public class IndexMiembros {

    private static void listar() {
        System.out.println("*** LISTAR MIEMBROS ***");

        int cantidad = Miembros.verCantidad();

        if (cantidad == 0) {
            System.out.println("!> La lista de miembros esta vacia");
            return;
        }

        for (int i = 0; i < cantidad; i++) {
            Miembros.mostrarMiembro(i);
            System.out.println(" - - - - - - - - - - ");
        }
        System.out.printf("Se mostraron %d miembro(s)\n", cantidad);
    }

    private static void registrar() {
        System.out.println("*** REGISTRAR MIEMBRO ***");

        String dni = Lector.preguntarValidado("DNI", "^\\d{8}$",
                "DNI invalido. Debe tener exactamente 8 digitos numericos.");

        String nombre = Lector.preguntar("Nombre Completo");

        String fechaNac;
        do {
            fechaNac = Lector.preguntar("Fecha de Nacimiento (dd-MM-yyyy)");
            if (Validaciones.esFechaValida(fechaNac)) {
                break;
            }
            Errores.personalizado("Fecha invalida. Use el formato dd-MM-yyyy (ej: 15-03-1990)");
        } while (true);

        String correo;
        do {
            correo = Lector.preguntar("Correo Electronico");
            if (Validaciones.esCorreoValido(correo)) {
                break;
            }
            Errores.personalizado("Correo invalido. Debe contener @ y un dominio (ej: usuario@dominio.com)");
        } while (true);

        String telefono = Lector.preguntarValidado("Numero de Telefono", "^\\d{9}$",
                "Telefono invalido. Debe tener exactamente 9 digitos numericos.");

        int codigoIglesia;
        do {
            Iglesias.mostrarGuiaIglesias();
            codigoIglesia = Lector.preguntarEntero("Codigo de Iglesia [0-" + (Iglesias.verCantidad() - 1) + "]");
            if (Iglesias.buscarIglesia(codigoIglesia) != -1) {
                break;
            }
            Errores.personalizado("Codigo de iglesia invalido. Intente de nuevo.");
        } while (true);

        int resultado = Miembros.crearMiembro(dni, nombre, fechaNac, correo, telefono, codigoIglesia);
        if (resultado == -1) {
            System.out.println("!> No se pudo registrar el miembro (DNI duplicado o almacenamiento lleno)");
        } else {
            System.out.println("Miembro registrado exitosamente!");
        }
    }

    private static void ver() {
        System.out.println("*** VER MIEMBRO ***");

        String dni = Lector.preguntar("DNI");
        int indice = Miembros.buscarMiembro(dni);

        if (indice == -1) {
            System.out.println("!> El miembro con DNI `" + dni + "` no existe");
        } else {
            Miembros.mostrarDetalleMiembro(indice);
        }
    }

    private static void editar() {
        System.out.println("*** EDITAR MIEMBRO ***");

        String dni = Lector.preguntar("DNI");
        int indice = Miembros.buscarMiembro(dni);

        if (indice == -1) {
            System.out.println("!> El miembro con DNI `" + dni + "` no existe");
        } else {
            String nombre = Lector.preguntar("Nombre Completo", Miembros.verNombre(indice));

            String fechaNac;
            do {
                fechaNac = Lector.preguntar("Fecha de Nacimiento (dd-MM-yyyy)", Miembros.verFechaNacimiento(indice));
                if (Validaciones.esFechaValida(fechaNac)) {
                    break;
                }
                Errores.personalizado("Fecha invalida. Use el formato dd-MM-yyyy (ej: 15-03-1990)");
            } while (true);

            String correo;
            do {
                correo = Lector.preguntar("Correo Electronico", Miembros.verCorreo(indice));
                if (Validaciones.esCorreoValido(correo)) {
                    break;
                }
                Errores.personalizado("Correo invalido. Debe contener @ y un dominio (ej: usuario@dominio.com)");
            } while (true);

            String telefono;
            do {
                telefono = Lector.preguntar("Numero de Telefono", Miembros.verTelefono(indice));
                if (Validaciones.esTelefonoValido(telefono)) {
                    break;
                }
                Errores.personalizado("Telefono invalido. Debe tener exactamente 9 digitos numericos.");
            } while (true);

            int codigoIglesia;
            do {
                Iglesias.mostrarGuiaIglesias();
                codigoIglesia = Lector.preguntarEntero("Codigo de Iglesia [0-" + (Iglesias.verCantidad() - 1) + "]", Miembros.verCodigoIglesia(indice));
                if (Iglesias.buscarIglesia(codigoIglesia) != -1) {
                    break;
                }
                Errores.personalizado("Codigo de iglesia invalido. Intente de nuevo.");
            } while (true);

            Miembros.editarMiembro(dni, nombre, fechaNac, correo, telefono, codigoIglesia);
            System.out.println("Miembro editado exitosamente!");
        }
    }

    private static void asignarRol() {
        System.out.println("*** ASIGNAR ROL A MIEMBRO ***");

        String dni = Lector.preguntar("DNI del miembro");
        int indice = Miembros.buscarMiembro(dni);

        if (indice == -1) {
            System.out.println("!> El miembro con DNI `" + dni + "` no existe");
            return;
        }

        if (Miembros.tieneRolActivo(dni)) {
            System.out.println("!> Este miembro ya tiene un rol activo. Retirelo primero.");
            return;
        }

        System.out.println("Miembro: " + Miembros.verNombre(indice));
        System.out.println("Correo: " + Miembros.verCorreo(indice));
        System.out.println("");

        int codigoDepto;
        do {
            Constantes.mostrarDepartamentos();
            codigoDepto = Lector.preguntarEntero("Codigo de Departamento [0-" + (Constantes.DEPARTAMENTOS.length - 1) + "]");
            if (codigoDepto >= 0 && codigoDepto < Constantes.DEPARTAMENTOS.length) {
                break;
            }
            Errores.personalizado("Codigo de departamento invalido.");
        } while (true);

        int codigoCargo;
        do {
            Constantes.mostrarCargos();
            codigoCargo = Lector.preguntarEntero("Codigo de Cargo [0-" + (Constantes.CARGOS.length - 1) + "]");
            if (codigoCargo >= 0 && codigoCargo < Constantes.CARGOS.length) {
                break;
            }
            Errores.personalizado("Codigo de cargo invalido.");
        } while (true);

        String fechaExpiracion;
        do {
            fechaExpiracion = Lector.preguntar("Fecha de Expiracion (dd-MM-yyyy)");
            if (Validaciones.esFechaValida(fechaExpiracion)) {
                break;
            }
            Errores.personalizado("Fecha invalida. Use el formato dd-MM-yyyy (ej: 31-12-2026)");
        } while (true);

        System.out.println("");
        System.out.println("Departamento: " + Constantes.verNombreDepartamento(codigoDepto) + " (" + codigoDepto + ")");
        System.out.println("Cargo: " + Constantes.verNombreCargo(codigoCargo) + " (" + codigoCargo + ")");
        System.out.println("Expiracion: " + fechaExpiracion);

        if (!Lector.confirmar("Confirmar asignacion de rol?")) {
            return;
        }

        Miembros.asignarRol(dni, codigoDepto, codigoCargo, fechaExpiracion);

        String email = Miembros.verCorreo(indice);
        String contrasenia = dni;

        int resultado = Credenciales.crearCredencial(email, contrasenia, dni);
        if (resultado == -1) {
            System.out.println("!> Error al crear credenciales (correo ya registrado o almacenamiento lleno)");
            Miembros.retirarRol(dni);
            return;
        }

        String cuerpo = String.format("""
                Hola %s, bienvenido al Sistema de Tesoreria IASD!

                Tu cuenta ha sido creada exitosamente. Aqui estan tus datos de acceso:

                Email: %s
                Contrasenia temporal: %s

                IMPORTANTE: Por favor, cambia tu contrasenia despues de iniciar sesion.
                Esta contrasenia expirara el %s.

                Nunca compartas estos datos con nadie.""",
                Miembros.verNombre(indice),
                email,
                contrasenia,
                fechaExpiracion
        );

        Correo.abrirClienteCorreo(
                email,
                "Acceso al Sistema de Tesoreria IASD",
                cuerpo
        );

        System.out.println("Rol asignado y credenciales creadas exitosamente!");
    }

    private static void retirarRol() {
        System.out.println("*** RETIRAR ROL A MIEMBRO ***");

        String dni = Lector.preguntar("DNI del miembro");
        int indice = Miembros.buscarMiembro(dni);

        if (indice == -1) {
            System.out.println("!> El miembro con DNI `" + dni + "` no existe");
            return;
        }

        if (!Miembros.tieneRolActivo(dni)) {
            System.out.println("!> Este miembro no tiene un rol activo");
            return;
        }

        System.out.println("Miembro: " + Miembros.verNombre(indice));
        System.out.println("Departamento: " + Constantes.verNombreDepartamento(Miembros.verCodigoDepartamento(indice)));
        System.out.println("Cargo: " + Constantes.verNombreCargo(Miembros.verCodigoCargo(indice)));
        System.out.println("Expiracion: " + Miembros.verFechaExpiracion(indice));

        if (!Lector.confirmar("Confirmar retiro de rol?")) {
            return;
        }

        Miembros.retirarRol(dni);
        Credenciales.eliminarCredencial(dni);

        System.out.println("Rol retirado y credenciales eliminadas exitosamente!");
    }

    private static void verPorDepartamento() {
        System.out.println("*** MIEMBROS POR DEPARTAMENTO ***");

        int codigoDepto;
        do {
            Constantes.mostrarDepartamentos();
            codigoDepto = Lector.preguntarEntero("Codigo de Departamento [0-" + (Constantes.DEPARTAMENTOS.length - 1) + "]");
            if (codigoDepto >= 0 && codigoDepto < Constantes.DEPARTAMENTOS.length) {
                break;
            }
            Errores.personalizado("Codigo de departamento invalido.");
        } while (true);

        int[] indices = Miembros.buscarPorDepartamento(codigoDepto);

        if (indices.length == 0) {
            System.out.println("!> No hay miembros en el departamento: " + Constantes.verNombreDepartamento(codigoDepto));
            return;
        }

        System.out.println("");
        System.out.println("Departamento: " + Constantes.verNombreDepartamento(codigoDepto));
        System.out.println("");

        for (int i = 0; i < indices.length; i++) {
            int idx = indices[i];
            System.out.println("* DNI: " + Miembros.verDNI(idx)
                    + " | Nombre: " + Miembros.verNombre(idx)
                    + " | Cargo: " + Constantes.verNombreCargo(Miembros.verCodigoCargo(idx))
                    + " | Expira: " + Miembros.verFechaExpiracion(idx));
        }
        System.out.printf("Se mostraron %d miembro(s)\n", indices.length);
    }

    private static void volver() {
        System.out.println("Buena suerte!\n");
    }

    public static void mostrarMenu() {
        int cantidad = Miembros.verCantidad();
        System.out.println("");
        System.out.println("*** GESTION DE MIEMBROS *** (" + cantidad + " miembro(s))");
        System.out.println("1. Ver Todos los Miembros");
        System.out.println("2. Registrar Miembro");
        System.out.println("3. Ver Miembro");
        System.out.println("4. Editar Miembro");
        System.out.println("5. Asignar Rol a Miembro");
        System.out.println("6. Retirar Rol a Miembro");
        System.out.println("7. Ver Miembros por Departamento");
        System.out.println("8. Volver");
    }

    public static void inicio() {
        int opcion;
        do {
            mostrarMenu();
            opcion = Lector.preguntarEntero("Elige una opcion [1-8]");
            System.out.println("");

            switch (opcion) {
                case 1 ->
                    listar();
                case 2 ->
                    registrar();
                case 3 ->
                    ver();
                case 4 ->
                    editar();
                case 5 ->
                    asignarRol();
                case 6 ->
                    retirarRol();
                case 7 ->
                    verPorDepartamento();
                case 8 ->
                    volver();
                default ->
                    Errores.deRango();
            }
        } while (opcion != 8);
    }
}
