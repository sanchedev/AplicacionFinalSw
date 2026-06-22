package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utilidad para exportar datos a CSV (Excel) o HTML (imprimir a PDF).
 * Genera archivos en saveds/reportes/ con timestamp en el nombre.
 */
public class Exportador {

    private static final String CARPETA_REPORTES = "saveds/reportes/";

    /**
     * Pregunta al usuario en que formato desea exportar.
     *
     * @return 1 para CSV, 2 para HTML, 0 si cancela.
     */
    public static int preguntarFormato() {
        System.out.println("");
        System.out.println("*** FORMATO DE EXPORTACION ***");
        System.out.println("1. CSV (abre en Excel)");
        System.out.println("2. HTML (imprimir a PDF desde el navegador)");
        System.out.println("3. Cancelar");
        int opcion = Lector.preguntarEntero("Elige una opcion [1-3]");
        System.out.println("");

        if (opcion == 3 || (opcion != 1 && opcion != 2)) {
            return 0;
        }
        return opcion;
    }

    /**
     * Pregunta si desea exportar y ejecuta la exportacion segun el formato.
     *
     * @param titulo Titulo del reporte
     * @param encabezados Nombres de las columnas
     * @param datos Filas de datos (cada fila es un array de Strings)
     */
    public static void exportar(String titulo, String[] encabezados, String[][] datos) {
        if (datos.length == 0) {
            System.out.println("!> No hay datos para exportar");
            return;
        }

        if (!Lector.confirmar("Desea exportar estos datos?")) {
            return;
        }

        int formato = preguntarFormato();
        if (formato == 0) {
            System.out.println("Exportacion cancelada.");
            return;
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm"));
        String nombreBase = "reporte_" + timestamp;

        File carpeta = new File(CARPETA_REPORTES);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        if (formato == 1) {
            File archivo = new File(carpeta, nombreBase + ".csv");
            csv(archivo, encabezados, datos);
        } else {
            File archivo = new File(carpeta, nombreBase + ".html");
            html(archivo, titulo, encabezados, datos);
        }
    }

    /**
     * Genera un archivo CSV con delimitador coma y lo abre.
     */
    private static void csv(File archivo, String[] encabezados, String[][] datos) {
        try (FileWriter writer = new FileWriter(archivo)) {
            for (int i = 0; i < encabezados.length; i++) {
                if (i != 0) {
                    writer.write(",");
                }
                writer.write(encabezados[i]);
            }
            writer.write("\n");

            for (int i = 0; i < datos.length; i++) {
                for (int j = 0; j < datos[i].length; j++) {
                    if (j != 0) {
                        writer.write(",");
                    }
                    String valor = datos[i][j] != null ? datos[i][j] : "";
                    if (valor.contains(",") || valor.contains("\"") || valor.contains("\n")) {
                        valor = "\"" + valor.replace("\"", "\"\"") + "\"";
                    }
                    writer.write(valor);
                }
                writer.write("\n");
            }

            System.out.println("Reporte exportado: " + archivo.getAbsolutePath());
            abrir(archivo);

        } catch (IOException e) {
            System.out.println("!> Error al exportar CSV: " + e.getMessage());
        }
    }

    /**
     * Genera un archivo HTML con tabla estilizada y lo abre en el navegador.
     */
    private static void html(File archivo, String titulo, String[] encabezados, String[][] datos) {
        try (FileWriter writer = new FileWriter(archivo)) {
            writer.write("<!DOCTYPE html>\n<html lang='es'>\n<head>\n");
            writer.write("<meta charset='UTF-8'>\n");
            writer.write("<title>" + titulo + "</title>\n");
            writer.write("<style>\n");
            writer.write("  body { font-family: Arial, sans-serif; margin: 30px; background: #f5f5f5; }\n");
            writer.write("  .reporte { background: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); max-width: 1100px; margin: 0 auto; }\n");
            writer.write("  h1 { text-align: center; color: #2c3e50; margin-bottom: 5px; font-size: 22px; }\n");
            writer.write("  .subtitulo { text-align: center; color: #7f8c8d; font-size: 13px; margin-bottom: 25px; }\n");
            writer.write("  table { width: 100%; border-collapse: collapse; margin-top: 15px; }\n");
            writer.write("  th { background: #2c3e50; color: #fff; padding: 10px 12px; text-align: left; font-size: 13px; }\n");
            writer.write("  td { padding: 8px 12px; border-bottom: 1px solid #ecf0f1; font-size: 13px; }\n");
            writer.write("  tr:nth-child(even) { background: #f9f9f9; }\n");
            writer.write("  tr:hover { background: #eef5ff; }\n");
            writer.write("  .total-row { font-weight: bold; background: #ecf0f1 !important; }\n");
            writer.write("  .derecha { text-align: right; }\n");
            writer.write("  @media print { body { background: #fff; margin: 0; } .reporte { box-shadow: none; padding: 15px; } }\n");
            writer.write("</style>\n</head>\n<body>\n");
            writer.write("<div class='reporte'>\n");
            writer.write("  <h1>" + titulo + "</h1>\n");
            writer.write("  <p class='subtitulo'>Generado: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + "</p>\n");
            writer.write("  <table>\n");

            writer.write("    <thead><tr>");
            for (int i = 0; i < encabezados.length; i++) {
                writer.write("<th>" + encabezados[i] + "</th>");
            }
            writer.write("</tr></thead>\n");

            writer.write("    <tbody>\n");
            for (int i = 0; i < datos.length; i++) {
                boolean esFilaTotal = datos[i][0] != null && datos[i][0].startsWith("TOTAL");
                writer.write("      <tr" + (esFilaTotal ? " class='total-row'" : "") + ">");
                for (int j = 0; j < datos[i].length; j++) {
                    String valor = datos[i][j] != null ? datos[i][j] : "";
                    boolean derecha = esNumero(valor);
                    writer.write("<td" + (derecha ? " class='derecha'" : "") + ">" + valor + "</td>");
                }
                writer.write("</tr>\n");
            }
            writer.write("    </tbody>\n");

            writer.write("  </table>\n");
            writer.write("</div>\n</body>\n</html>");

            System.out.println("Reporte exportado: " + archivo.getAbsolutePath());
            System.out.println("Para guardar como PDF: Ctrl+P en el navegador > Guardar como PDF");
            abrir(archivo);

        } catch (IOException e) {
            System.out.println("!> Error al exportar HTML: " + e.getMessage());
        }
    }

    private static boolean esNumero(String valor) {
        if (valor == null || valor.isEmpty()) {
            return false;
        }
        String v = valor.trim();
        if (v.startsWith("S/.")) {
            return true;
        }
        try {
            Double.parseDouble(v.replace(",", ""));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Abre un archivo en la aplicacion predeterminada del sistema operativo.
     */
    private static void abrir(File archivo) {
        if (!archivo.exists()) {
            return;
        }

        try {
            String os = System.getProperty("os.name").toLowerCase();
            ProcessBuilder pb;

            if (os.contains("win")) {
                pb = new ProcessBuilder("cmd.exe", "/c", "start", '"' + "Reporte" + '"', archivo.getAbsolutePath());
            } else if (os.contains("mac")) {
                pb = new ProcessBuilder("open", archivo.getAbsolutePath());
            } else {
                pb = new ProcessBuilder("xdg-open", archivo.getAbsolutePath());
            }

            pb.redirectOutput(ProcessBuilder.Redirect.DISCARD);
            pb.redirectError(ProcessBuilder.Redirect.DISCARD);
            pb.start();

        } catch (IOException e) {
            System.out.println("!> No se pudo abrir el archivo: " + e.getMessage());
        }
    }
}
