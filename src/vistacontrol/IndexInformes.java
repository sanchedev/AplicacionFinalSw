package vistacontrol;

import repository.Depositos;
import repository.Gastos;
import repository.Iglesias;
import repository.Miembros;
import utils.Errores;
import utils.Exportador;
import utils.Lector;

/**
 * Informes de tesoreria: consulta, registro de depositos y estadisticas.
 */
public class IndexInformes {

    private static void registrar() {
        System.out.println("*** REGISTRAR DEPOSITO ***");

        int codigoIglesia;
        do {
            Iglesias.mostrarGuiaIglesias();
            codigoIglesia = Lector.preguntarEntero("Codigo de Iglesia [0-" + (Iglesias.verCantidad() - 1) + "]");
            if (Iglesias.buscarIglesia(codigoIglesia) != -1) {
                break;
            }
            Errores.personalizado("Codigo de iglesia invalido. Intente de nuevo.");
        } while (true);

        String dni;
        int opcionPersona = 0;
        do {
            System.out.println("Tipo de aportante:");
            System.out.println("1. Miembro registrado");
            System.out.println("2. Invitado (sin registro)");
            System.out.println("3. Anonimo");
            opcionPersona = Lector.preguntarEntero("Opcion [1-3]");

            if (opcionPersona == 1) {
                dni = Lector.preguntar("DNI del miembro");
                int indice = Miembros.buscarMiembro(dni);
                if (indice == -1) {
                    System.out.println("!> No se encontro miembro con DNI `" + dni + "`. Intente de nuevo.");
                    continue;
                }
                System.out.println("Miembro: " + Miembros.verNombre(indice));
                break;
            } else if (opcionPersona == 2) {
                dni = Lector.preguntarValidado("DNI del invitado", "^\\d{8}$",
                        "DNI invalido. Debe tener exactamente 8 digitos numericos.");
                String nombreInvitado = Lector.preguntar("Nombre del invitado");
                Miembros.crearMiembro(dni, nombreInvitado, "", "", "", codigoIglesia);
                System.out.println("Invitado registrado temporalmente.");
                break;
            } else if (opcionPersona == 3) {
                dni = "*anonimo";
                break;
            } else {
                Errores.deRango();
            }
        } while (true);

        System.out.println("");
        System.out.println("Ingrese los montos del deposito (dejar en 0 si no aplica):");
        double diezmo = Lector.preguntarDouble("Diezmo", 0);
        double ofrendaSistem = Lector.preguntarDouble("Ofrenda Sistematica", 0);
        double proyectoLocal = Lector.preguntarDouble("Proyecto Local", 0);
        double pagoInstitucion = Lector.preguntarDouble("Pagos a Instituciones", 0);

        double total = diezmo + ofrendaSistem + proyectoLocal + pagoInstitucion;
        System.out.println("");
        System.out.println("─────────────────────────────");
        System.out.println("  Diezmo:              S/. " + String.format("%.2f", diezmo));
        System.out.println("  Ofrenda Sistematica: S/. " + String.format("%.2f", ofrendaSistem));
        System.out.println("  Proyecto Local:      S/. " + String.format("%.2f", proyectoLocal));
        System.out.println("  Pagos Instituciones: S/. " + String.format("%.2f", pagoInstitucion));
        System.out.println("─────────────────────────────");
        System.out.println("  TOTAL:               S/. " + String.format("%.2f", total));
        System.out.println("");

        if (total <= 0) {
            System.out.println("!> El deposito debe tener al menos un monto mayor a 0");
            return;
        }

        if (!Lector.confirmar("Confirmar deposito?")) {
            return;
        }

        int indiceDeposito = Depositos.crearDeposito(dni, codigoIglesia, diezmo,
                ofrendaSistem, proyectoLocal, pagoInstitucion);

        if (indiceDeposito == -1) {
            System.out.println("!> No se pudo registrar el deposito (almacenamiento lleno)");
            return;
        }

        System.out.println("Deposito registrado exitosamente!");

        if (Lector.confirmar("Desea imprimir su recibo?")) {
            Depositos.imprimirDeposito(indiceDeposito);
        }
    }

    private static void listar() {
        System.out.println("*** LISTAR DEPOSITOS ***");

        int cantidad = Depositos.verCantidad();

        if (cantidad == 0) {
            System.out.println("!> La lista de depositos esta vacia");
            return;
        }

        for (int i = cantidad - 1; i >= 0; i--) {
            Depositos.mostrarDeposito(i);
            System.out.println(" - - - - - - - - - - ");
        }
        System.out.printf("Se mostraron %d deposito(s)\n", cantidad);

        String[] encabezados = {"Codigo", "DNI", "Iglesia", "Fecha", "Diezmo", "Ofrenda Sist.", "Proy. Local", "Pagos Instit.", "Total"};
        String[][] datos = new String[cantidad][9];
        for (int i = 0; i < cantidad; i++) {
            int idx = cantidad - 1 - i;
            datos[i][0] = Depositos.verCodigo(idx) + "";
            datos[i][1] = Depositos.verDNI(idx);
            datos[i][2] = Depositos.verCodigoIglesia(idx) + "";
            datos[i][3] = Depositos.verFecha(idx);
            datos[i][4] = "S/. " + String.format("%.2f", Depositos.verDiezmo(idx));
            datos[i][5] = "S/. " + String.format("%.2f", Depositos.verOfrendaSistemtica(idx));
            datos[i][6] = "S/. " + String.format("%.2f", Depositos.verProyectoLocal(idx));
            datos[i][7] = "S/. " + String.format("%.2f", Depositos.verPagoInstitucion(idx));
            datos[i][8] = "S/. " + String.format("%.2f", Depositos.verTotal(idx));
        }
        Exportador.exportar("Lista de Depositos", encabezados, datos);
    }

    private static void listarPorIglesia() {
        System.out.println("*** LISTAR DEPOSITOS POR IGLESIA ***");

        int codigoIglesia;
        do {
            Iglesias.mostrarGuiaIglesias();
            codigoIglesia = Lector.preguntarEntero("Codigo de Iglesia [0-" + (Iglesias.verCantidad() - 1) + "]");
            if (Iglesias.buscarIglesia(codigoIglesia) != -1) {
                break;
            }
            Errores.personalizado("Codigo de iglesia invalido. Intente de nuevo.");
        } while (true);

        int[] indices = Depositos.buscarDepositoPorIglesia(codigoIglesia);

        if (indices.length == 0) {
            System.out.println("!> No hay depositos para esta iglesia");
            return;
        }

        System.out.println();
        Iglesias.mostrarDetalleIglesia(Iglesias.buscarIglesia(codigoIglesia));
        System.out.println();

        for (int i = indices.length - 1; i >= 0; i--) {
            Depositos.mostrarDeposito(indices[i]);
            System.out.println(" - - - - - - - - - - ");
        }
        System.out.printf("Se mostraron %d deposito(s)\n", indices.length);

        String nombreIgls = Iglesias.verNombre(Iglesias.buscarIglesia(codigoIglesia));
        String[] encabezados = {"Codigo", "DNI", "Fecha", "Diezmo", "Ofrenda Sist.", "Proy. Local", "Pagos Instit.", "Total"};
        String[][] datos = new String[indices.length][8];
        for (int i = 0; i < indices.length; i++) {
            int idx = indices[indices.length - 1 - i];
            datos[i][0] = Depositos.verCodigo(idx) + "";
            datos[i][1] = Depositos.verDNI(idx);
            datos[i][2] = Depositos.verFecha(idx);
            datos[i][3] = "S/. " + String.format("%.2f", Depositos.verDiezmo(idx));
            datos[i][4] = "S/. " + String.format("%.2f", Depositos.verOfrendaSistemtica(idx));
            datos[i][5] = "S/. " + String.format("%.2f", Depositos.verProyectoLocal(idx));
            datos[i][6] = "S/. " + String.format("%.2f", Depositos.verPagoInstitucion(idx));
            datos[i][7] = "S/. " + String.format("%.2f", Depositos.verTotal(idx));
        }
        Exportador.exportar("Depositos - " + nombreIgls, encabezados, datos);
    }

    private static void ver() {
        System.out.println("*** VER DEPOSITO ***");

        int codigo = Lector.preguntarEntero("Codigo del deposito");
        int indice = Depositos.buscarDeposito(codigo);

        if (indice == -1) {
            System.out.println("!> El deposito con codigo `" + codigo + "` no existe");
        } else {
            Depositos.mostrarDetalleDeposito(indice);
            if (Lector.confirmar("Desea imprimir este recibo?")) {
                Depositos.imprimirDeposito(indice);
            }
        }
    }

    private static void resumenPorTipo() {
        System.out.println("*** RESUMEN POR TIPO DE APORTE ***");

        int cantidad = Depositos.verCantidad();
        if (cantidad == 0) {
            System.out.println("!> No hay depositos registrados");
            return;
        }

        double totalDiezmo = 0;
        double totalOfrenda = 0;
        double totalProyecto = 0;
        double totalPagos = 0;

        for (int i = 0; i < cantidad; i++) {
            totalDiezmo += Depositos.verDiezmo(i);
            totalOfrenda += Depositos.verOfrendaSistemtica(i);
            totalProyecto += Depositos.verProyectoLocal(i);
            totalPagos += Depositos.verPagoInstitucion(i);
        }

        double granTotal = totalDiezmo + totalOfrenda + totalProyecto + totalPagos;

        System.out.println("");
        System.out.println("─────────────────────────────────");
        System.out.printf("  Diezmo:              S/. %10.2f%n", totalDiezmo);
        System.out.printf("  Ofrenda Sistematica: S/. %10.2f%n", totalOfrenda);
        System.out.printf("  Proyecto Local:      S/. %10.2f%n", totalProyecto);
        System.out.printf("  Pagos Instituciones: S/. %10.2f%n", totalPagos);
        System.out.println("─────────────────────────────────");
        System.out.printf("  TOTAL:               S/. %10.2f%n", granTotal);
        System.out.println("─────────────────────────────────");
        System.out.println("");

        String[] encabezados = {"Tipo de Aporte", "Total"};
        String[][] datos = {
            {"Diezmo", "S/. " + String.format("%.2f", totalDiezmo)},
            {"Ofrenda Sistematica", "S/. " + String.format("%.2f", totalOfrenda)},
            {"Proyecto Local", "S/. " + String.format("%.2f", totalProyecto)},
            {"Pagos a Instituciones", "S/. " + String.format("%.2f", totalPagos)},
            {"TOTAL", "S/. " + String.format("%.2f", granTotal)}
        };
        Exportador.exportar("Resumen por Tipo de Aporte", encabezados, datos);
    }

    private static void resumenPorIglesia() {
        System.out.println("*** RESUMEN POR IGLESIA ***");

        int cantidadDep = Depositos.verCantidad();
        if (cantidadDep == 0) {
            System.out.println("!> No hay depositos registrados");
            return;
        }

        int cantidadIgls = Iglesias.verCantidad();
        String[] encabezados = {"Iglesia", "Cant. Depositos", "Diezmo", "Ofrenda Sist.", "Proy. Local", "Pagos Instit.", "Gran Total"};
        String[][] datos = new String[cantidadIgls + 1][7];

        double totalDiezmo = 0;
        double totalOfrenda = 0;
        double totalProyecto = 0;
        double totalPagos = 0;

        for (int i = 0; i < cantidadIgls; i++) {
            int[] indices = Depositos.buscarDepositoPorIglesia(Iglesias.verCodigo(i));
            double diezmoI = 0, ofrendaI = 0, proyectoI = 0, pagosI = 0;

            for (int j = 0; j < indices.length; j++) {
                diezmoI += Depositos.verDiezmo(indices[j]);
                ofrendaI += Depositos.verOfrendaSistemtica(indices[j]);
                proyectoI += Depositos.verProyectoLocal(indices[j]);
                pagosI += Depositos.verPagoInstitucion(indices[j]);
            }

            double totalI = diezmoI + ofrendaI + proyectoI + pagosI;
            totalDiezmo += diezmoI;
            totalOfrenda += ofrendaI;
            totalProyecto += proyectoI;
            totalPagos += pagosI;

            System.out.printf("* %s: %d deposito(s) | Total: S/. %.2f%n",
                    Iglesias.verNombre(i), indices.length, totalI);

            datos[i][0] = Iglesias.verNombre(i);
            datos[i][1] = indices.length + "";
            datos[i][2] = "S/. " + String.format("%.2f", diezmoI);
            datos[i][3] = "S/. " + String.format("%.2f", ofrendaI);
            datos[i][4] = "S/. " + String.format("%.2f", proyectoI);
            datos[i][5] = "S/. " + String.format("%.2f", pagosI);
            datos[i][6] = "S/. " + String.format("%.2f", totalI);
        }

        double granTotal = totalDiezmo + totalOfrenda + totalProyecto + totalPagos;
        System.out.println("─────────────────────────────────");
        System.out.printf("  TOTAL GENERAL: S/. %.2f%n", granTotal);

        datos[cantidadIgls][0] = "TOTAL";
        datos[cantidadIgls][1] = "";
        datos[cantidadIgls][2] = "S/. " + String.format("%.2f", totalDiezmo);
        datos[cantidadIgls][3] = "S/. " + String.format("%.2f", totalOfrenda);
        datos[cantidadIgls][4] = "S/. " + String.format("%.2f", totalProyecto);
        datos[cantidadIgls][5] = "S/. " + String.format("%.2f", totalPagos);
        datos[cantidadIgls][6] = "S/. " + String.format("%.2f", granTotal);
        System.out.println("");

        Exportador.exportar("Resumen por Iglesia", encabezados, datos);
    }

    private static void balanceGeneral() {
        System.out.println("*** BALANCE GENERAL ***");

        int cantidadDep = Depositos.verCantidad();
        int cantidadGast = Gastos.verCantidad();

        if (cantidadDep == 0 && cantidadGast == 0) {
            System.out.println("!> No hay depositos ni gastos registrados");
            return;
        }

        double totalIngresos = 0;
        for (int i = 0; i < cantidadDep; i++) {
            totalIngresos += Depositos.verTotal(i);
        }

        double totalGastos = 0;
        for (int i = 0; i < cantidadGast; i++) {
            totalGastos += Gastos.verMonto(i);
        }

        double balance = totalIngresos - totalGastos;

        System.out.println("");
        System.out.println("─────────────────────────────────");
        System.out.printf("  Ingresos (Depositos): S/. %10.2f%n", totalIngresos);
        System.out.printf("  Gastos (Retiros):     S/. %10.2f%n", totalGastos);
        System.out.println("─────────────────────────────────");
        System.out.printf("  BALANCE:              S/. %10.2f%n", balance);
        System.out.println("─────────────────────────────────");
        System.out.println("");

        String[] encabezados = {"Concepto", "Monto"};
        String[][] datos = {
            {"Ingresos (Depositos)", "S/. " + String.format("%.2f", totalIngresos)},
            {"Gastos (Retiros)", "S/. " + String.format("%.2f", totalGastos)},
            {"BALANCE", "S/. " + String.format("%.2f", balance)}
        };
        Exportador.exportar("Balance General", encabezados, datos);
    }

    private static void volver() {
        System.out.println("Buena suerte!\n");
    }

    public static void mostrarMenu() {
        int cantidad = Depositos.verCantidad();
        System.out.println("");
        System.out.println("*** INFORMES DE TESORERIA *** (" + cantidad + " deposito(s))");
        System.out.println("1. Registrar Deposito");
        System.out.println("2. Ver Todos los Depositos");
        System.out.println("3. Ver Por Iglesia");
        System.out.println("4. Ver Un Deposito");
        System.out.println("5. Resumen por Tipo de Aporte");
        System.out.println("6. Resumen por Iglesia");
        System.out.println("7. Balance General");
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
                    registrar();
                case 2 ->
                    listar();
                case 3 ->
                    listarPorIglesia();
                case 4 ->
                    ver();
                case 5 ->
                    resumenPorTipo();
                case 6 ->
                    resumenPorIglesia();
                case 7 ->
                    balanceGeneral();
                case 8 ->
                    volver();
                default ->
                    Errores.deRango();
            }
        } while (opcion != 8);
    }
}
