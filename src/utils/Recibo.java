package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import vistacontrol.IndexDepositos;
import vistacontrol.IndexIglesias;
import vistacontrol.IndexMiembros;

/**
 * @author sanchedev
 */
public class Recibo {

    private File archivoHtml;
    private final int indexDeposito;

    public Recibo(int indexDeposito) {
        this.indexDeposito = indexDeposito;
        generarArchivo();
    }

    private void generarArchivo() {
        int codigoDeposito = IndexDepositos.codigos[indexDeposito];
        String dniAportante = IndexDepositos.dnis[indexDeposito];
        int codigoIglesia = IndexDepositos.codigoIglesias[indexDeposito];
        String fecha = IndexDepositos.fechas[indexDeposito];
        double diezmo = IndexDepositos.diezmos[indexDeposito];
        double ofrendaSistem = IndexDepositos.ofrendasSistemicas[indexDeposito];
        double proyectoLocal = IndexDepositos.ofrendasProyectoLocal[indexDeposito];
        double pagoInstitucion = IndexDepositos.ofrendasInstituciones[indexDeposito];

        int indicePersona = IndexMiembros.buscarPorDNI(dniAportante);
        String nombrePersona = "Visitante / Anonimo";
        if (indicePersona != -1) {
            nombrePersona = IndexMiembros.verNombre(dniAportante);
        }

        int indiceIglesia = IndexIglesias.buscarPorCodigo(codigoIglesia);
        String nombreIglesia = "IASD 404";
        String direccionIglesia = "No se pudo encontrar la iglesia";
        if (indiceIglesia != -1) {
            nombreIglesia = IndexIglesias.verNombre(codigoIglesia);
            direccionIglesia = IndexIglesias.verDireccion(codigoIglesia);
        }

        double totalCalculado = diezmo + ofrendaSistem + proyectoLocal + pagoInstitucion;

        String nombreArchivo = "recibo_pago_" + codigoDeposito + ".html";

        File carpetaRecibos = new File("saveds/recibos/");

        if (!carpetaRecibos.exists()) {
            carpetaRecibos.mkdirs();
        }

        archivoHtml = new File(carpetaRecibos, nombreArchivo);

        try (FileWriter writer = new FileWriter(archivoHtml)) {
            writer.write("<!DOCTYPE html>\n<html lang='es'>\n<head>\n");
            writer.write("<meta charset='UTF-8'>\n");
            writer.write("<title>Recibo de Pago #" + codigoDeposito + "</title>\n");
            writer.write("<style>\n");
            writer.write(
                    "  body { font-family: 'Courier New', Courier, monospace; margin: 20px; background-color: #f0f0f0; display: flex; justify-content: center; }\n");
            writer.write(
                    "  .ticket { max-width: 380px; width: 100%; padding: 20px; border: 1px dashed #777; background: #fff; box-shadow: 0 0 10px rgba(0,0,0,0.05); }\n");
            writer.write("  .center { text-align: center; }\n");
            writer.write("  .bold { font-weight: bold; }\n");
            writer.write(
                    "  .flex { display: flex; justify-content: space-between; margin: 6px 0; font-size: 13px; }\n");
            writer.write("  .divider { border-top: 1px dashed #333; margin: 12px 0; }\n");
            writer.write("</style>\n</head>\n<body>\n");

            writer.write("<div class='ticket'>\n");
            writer.write("  <h2 class='center bold' style='margin-bottom: 5px;'>" + nombreIglesia + "</h2>\n");
            writer.write("  <p class='center' style='margin-top: 0; font-size: 12px;'>RUC: 00000000000<br>"
                    + direccionIglesia + "</p>\n");
            writer.write("  <div class='divider'></div>\n");

            writer.write("  <p class='bold center' style='margin: 10px 0;'>COMPROBANTE DE APORTE</p>\n");
            writer.write("  <div class='flex'><span>Nro. Operacion:</span> <span class='bold'>" + codigoDeposito
                    + "</span></div>\n");
            writer.write("  <div class='flex'><span>Fecha:</span> <span>" + fecha + "</span></div>\n");
            writer.write("  <div class='flex'><span>DNI:</span> <span>" + dniAportante + "</span></div>\n");
            writer.write("  <div class='flex'><span>Aportante:</span> <span class='bold'>" + nombrePersona
                    + "</span></div>\n");
            writer.write("  <div class='divider'></div>\n");

            writer.write("  <p class='bold' style='font-size: 14px; margin: 5px 0;'>DETALLE</p>\n");
            writer.write("  <div class='flex'><span>Diezmo</span> <span>S/. " + String.format("%.2f", diezmo)
                    + "</span></div>\n");
            writer.write("  <div class='flex'><span>Ofrenda Sistematica</span> <span>S/. "
                    + String.format("%.2f", ofrendaSistem) + "</span></div>\n");
            writer.write("  <div class='flex'><span>Proyecto Local</span> <span>S/. "
                    + String.format("%.2f", proyectoLocal) + "</span></div>\n");
            writer.write("  <div class='flex'><span>Pagos a Instituciones</span> <span>S/. "
                    + String.format("%.2f", pagoInstitucion) + "</span></div>\n");
            writer.write("  <div class='divider'></div>\n");

            writer.write("  <div class='flex bold' style='font-size: 15px;'><span>TOTAL GENERAL:</span> <span>S/. "
                    + String.format("%.2f", totalCalculado) + "</span></div>\n");
            writer.write("  <div class='divider'></div>\n");

            writer.write(
                    "  <p class='center bold' style='margin-top: 15px; font-size: 12px;'>Que Dios bendiga su generosidad!</p>\n");
            writer.write("</div>\n");

            writer.write("</body>\n</html>");

        } catch (IOException e) {
            System.err.println("Error critico al estructurar el recibo HTML: " + e.getMessage());
        }
    }

    public void abrir() {
        if (archivoHtml == null || !archivoHtml.exists()) {
            System.err.println("Error: El archivo del recibo no existe.");
            return;
        }

        System.out.println("Abriendo el recibo...");

        try {
            String os = System.getProperty("os.name").toLowerCase();
            ProcessBuilder pb;

            if (os.contains("win")) {
                pb = new ProcessBuilder("cmd.exe", "/c", "start", '"' + "Recibo" + '"', archivoHtml.getAbsolutePath());
            } else if (os.contains("mac")) {
                pb = new ProcessBuilder("open", archivoHtml.getAbsolutePath());
            } else {
                pb = new ProcessBuilder("xdg-open", archivoHtml.getAbsolutePath());
            }

            pb.redirectOutput(ProcessBuilder.Redirect.DISCARD);
            pb.redirectError(ProcessBuilder.Redirect.DISCARD);

            pb.start();

        } catch (IOException e) {
            System.err.println("No se pudo abrir el recibo en el navegador: " + e.getMessage());
        }
    }
}
