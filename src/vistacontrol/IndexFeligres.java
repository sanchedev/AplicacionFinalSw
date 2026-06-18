package vistacontrol;

import java.time.LocalDate;
import repository.Depositos;
import repository.Iglesias;
import repository.Personas;
import utils.Errores;
import utils.Lector;
import utils.Sesion;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author sanchedev
 */
public class IndexFeligres {

    private static void verInformacion() {
        System.out.println("*** MI INFORMACION ***");
        int indice = Personas.buscarPersona(Sesion.verDNI());

        if (indice == -1) {
            System.out.println("!> Estas en modo anonimo");
        } else {
            Personas.mostrarDetallePersona(indice);
        }
    }

    private static void verDepositos() {
        System.out.println("*** MIS DEPOSITOS ***");

        String dni = Sesion.verDNI();
        int indice = Personas.buscarPersona(dni);

        if (indice == -1) {
            System.out.println("!> Estas en modo anonimo");
            return;
        }

        int[] resultados = Depositos.buscarDepositosPorDni(dni);

        if (resultados.length == 0) {
            System.out.println("!> Aun no has hecho un pago");
            return;
        }

        for (int i = 0; i < resultados.length; i++) {
            Depositos.mostrarDeposito(resultados[i]);
            System.out.println(" - - - - - - - - - - ");
        }
        System.out.printf("Se mostraron %d deposito(s)\n", resultados.length);

        if (Lector.confirmar("¿Desea ver las estadisticas de sus donaciones?")) {
            LocalDate ahora = LocalDate.now();

            double diezmosTotalesAnio = 0;
            double ofrendasTotalesAnio = 0;
            double diezmosTotalesMes = 0;
            double ofrendasTotalesMes = 0;

            for (int i = 0; i < resultados.length; i++) {
                String fecha = Depositos.verFecha(resultados[i]);
                if (!fecha.endsWith(Integer.toString(ahora.getYear()))) {
                    continue;
                }
                double diezmo = Depositos.verDiezmo(resultados[i]);
                double ofrenda = Depositos.verOfrenda(resultados[i]);
                diezmosTotalesAnio += diezmo;
                ofrendasTotalesAnio += ofrenda;
                if (!fecha.endsWith((ahora.getMonthValue()) + "-" + ahora.getYear())) {
                    continue;
                }
                diezmosTotalesMes += diezmo;
                ofrendasTotalesMes += ofrenda;
            }

            String diezmosMes = String.format("%.2f", diezmosTotalesMes);
            String ofrendasMes = String.format("%.2f", ofrendasTotalesMes);
            String totalMes = String.format("%.2f", diezmosTotalesMes + ofrendasTotalesMes);
            String padDMes = " ".repeat(totalMes.length() - diezmosMes.length());
            String padOMes = " ".repeat(totalMes.length() - ofrendasMes.length());

            String diezmosAnio = String.format("%.2f", diezmosTotalesAnio);
            String ofrendasAnio = String.format("%.2f", ofrendasTotalesAnio);
            String totalAnio = String.format("%.2f", diezmosTotalesAnio + ofrendasTotalesAnio);
            String padDAnio = " ".repeat(totalAnio.length() - diezmosAnio.length());
            String padOAnio = " ".repeat(totalAnio.length() - ofrendasAnio.length());

            System.out.println("**> ESTADISTICAS DE DONACIONES");
            System.out.println("> EN EL MES:");
            System.out.println("  Diezmos:  S/." + padDMes + diezmosMes);
            System.out.println("  Ofrendas: S/." + padOMes + ofrendasMes);
            System.out.println("  Total:    S/." + totalMes);
            System.out.println("");
            System.out.println("> EN EL ANIO:");
            System.out.println("  Diezmos:  S/." + padDAnio + diezmosAnio);
            System.out.println("  Ofrendas: S/." + padOAnio + ofrendasAnio);
            System.out.println("  Total:    S/." + totalAnio);
        }
    }

    private static void depositar() {
        String dni = Sesion.verDNI();

        Iglesias.mostrarGuiaIglesias();
        int codigoIglesia;
        do {
            if (Sesion.haAuthPersona()) {
                codigoIglesia = Lector.preguntarEntero("Codigo de Iglesia", Personas.buscarIglesia(dni));
            } else {
                codigoIglesia = Lector.preguntarEntero("Codigo de Iglesia");
            }
        } while (Iglesias.buscarIglesia(codigoIglesia) == -1);

        double diezmo = Lector.preguntarDecimal("Diezmo");
        double ofrenda = Lector.preguntarDecimal("Ofrenda");

        int indiceDeposito = Depositos.crearDeposito(dni, codigoIglesia, diezmo, ofrenda);
        if (Lector.confirmar("¿Desea imprimir su recibo?")) {
            Depositos.imprimirDeposito(indiceDeposito);
        }
    }

    private static void volver() {
        System.out.println("Buena suerte!\n");
    }

    public static void mostrarMenu() {
        System.out.println("");
        System.out.println("*** FELIGRESIA ***");
        System.out.println("1. Ver Mi Informacion");
        System.out.println("2. Ver Mis Depositos");
        System.out.println("3. Realizar un Deposito");
        System.out.println("4. Volver");
    }

    public static void inicio() {
        int opcion;
        do {
            mostrarMenu();
            opcion = Lector.preguntarEntero("Elige una opcion [1-4]");
            System.out.println("");

            switch (opcion) {
                case 1 ->
                    verInformacion();
                case 2 ->
                    verDepositos();
                case 3 ->
                    depositar();
                case 4 ->
                    volver();
                default ->
                    Errores.deRango();
            }
        } while (opcion != 4);
    }

}
