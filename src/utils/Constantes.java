package utils;

/**
 * Listas estaticas predefinidas de departamentos y cargos del sistema.
 * Los indices del arreglo equivalen al codigo de cada elemento.
 */
public class Constantes {

    public static final String[] DEPARTAMENTOS = {
        "Ministerio de la Mujer",
        "Ministerio Infantil",
        "ADRA",
        "Ministerio de Jovenes",
        "Ministerio de Musica",
        "Ministerio de Publicaciones",
        "Ministerio de Educacion Biblica",
        "Ministerio de Salud",
        "Ministerio de Personal"
    };

    public static final String[] CARGOS = {
        "Director",
        "Vice-director",
        "Secretario",
        "Tesorero",
        "Vocalista",
        "Instrumentista",
        "Instructor",
        "Coordinador"
    };

    public static void mostrarDepartamentos() {
        System.out.println("Departamentos:");
        for (int i = 0; i < DEPARTAMENTOS.length; i++) {
            System.out.printf("* %d -> %s\n", i, DEPARTAMENTOS[i]);
        }
    }

    public static void mostrarCargos() {
        System.out.println("Cargos:");
        for (int i = 0; i < CARGOS.length; i++) {
            System.out.printf("* %d -> %s\n", i, CARGOS[i]);
        }
    }

    public static String verNombreDepartamento(int codigo) {
        if (codigo < 0 || codigo >= DEPARTAMENTOS.length) {
            return "Sin departamento";
        }
        return DEPARTAMENTOS[codigo];
    }

    public static String verNombreCargo(int codigo) {
        if (codigo < 0 || codigo >= CARGOS.length) {
            return "Sin cargo";
        }
        return CARGOS[codigo];
    }
}
