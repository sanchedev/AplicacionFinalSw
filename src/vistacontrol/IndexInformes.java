package vistacontrol;

import utils.Errores;
import utils.Exportador;
import utils.Lector;

/**
 * @author sanchedev
 */
public class IndexInformes {

    private static void miembro() {
        System.out.println("*** Informe por Miembro ***");
        if (IndexMiembros.cantidad == 0) {
            Errores.personalizado("No hay Miembros registrados");
            return;
        }
        String dni = Lector.preguntar("DNI");
        int indiceMiembro = IndexMiembros.buscarPorDNI(dni);
        if (indiceMiembro == -1) {
            Errores.personalizado("Miembro con DNI \"" + dni + "\" no existe");
            return;
        }
        int indicesCantidad = 0;
        int[] indices = new int[IndexDepositos.cantidad];
        for (int i = 0; i < IndexDepositos.cantidad; i++) {
            if (IndexDepositos.dnis[i].equals(dni)) {
                indices[indicesCantidad] = i;
                indicesCantidad++;
            }
        }
        String tituloReporte = "Informe de Miembro -  " + IndexMiembros.verNombre(dni);
        Exportador.exportar(tituloReporte, indices, indicesCantidad);
    }

    private static void iglesia() {
        System.out.println("*** Informe por Iglesia ***");
        if (IndexIglesias.cantidad == 0) {
            Errores.personalizado("No hay Iglesias registradas");
            return;
        }
        IndexIglesias.verIglesias();
        int codigoIglesia = Lector.preguntarEntero("Codigo de Iglesia");
        int indice = IndexIglesias.buscarPorCodigo(codigoIglesia);
        if (indice == -1) {
            Errores.personalizado("Iglesia con codigo \"" + codigoIglesia + "\" no existe");
            return;
        }
        int indicesCantidad = 0;
        int[] indices = new int[IndexDepositos.cantidad];
        for (int i = 0; i < IndexDepositos.cantidad; i++) {
            if (IndexDepositos.codigoIglesias[i] == codigoIglesia) {
                indices[indicesCantidad] = i;
                indicesCantidad++;
            }
        }
        String tituloReporte = "Reporte de Aportes - " + IndexIglesias.verNombre(codigoIglesia);
        Exportador.exportar(tituloReporte, indices, indicesCantidad);
    }

    private static void volver() {
        System.out.println("Buena suerte!\n");
    }

    public static void mostrarMenu() {
        System.out.println("");
        System.out.println("*** MENU INFORMES ***");
        System.out.println("1. Miembro");
        System.out.println("2. Iglesia");
        System.out.println("3. Volver");
    }

    public static void inicio() {
        int opcion;
        do {
            mostrarMenu();
            opcion = Lector.preguntarEntero("Elige una opcion [1-3]");
            System.out.println("");

            switch (opcion) {
                case 1 ->
                    miembro();
                case 2 ->
                    iglesia();
                case 3 ->
                    volver();
                default ->
                    Errores.deRango();
            }
        } while (opcion != 3);
    }
}
