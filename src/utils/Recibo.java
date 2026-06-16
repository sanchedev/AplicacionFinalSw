package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import repository.Depositos;
import repository.Iglesias;
import repository.Personas;

/**
 *
 * @author sanchedev
 */
public class Recibo {
    private File archivoHtml;
    private final int indexDeposito;

    /**
     * Constructor que inicializa el recibo y genera automáticamente el archivo HTML.
     * @param indexDeposito Posición del depósito dentro de los arreglos del repositorio.
     */
    public Recibo(int indexDeposito) {
        this.indexDeposito = indexDeposito;
        generarArchivo();
    }

    /**
     * Extrae la información de los repositorios estáticos y genera el archivo HTML
     * con un diseño limpio y profesional de ticketera térmica.
     */
    private void generarArchivo() {
        int codigoDeposito = Depositos.verCodigo(indexDeposito);
        String dniAportante = Depositos.verDNI(indexDeposito);
        int codigoIglesia = Depositos.verCodigoIglesia(indexDeposito);
        String fecha = Depositos.verFecha(indexDeposito);
        double diezmo = Depositos.verDiezmo(indexDeposito);
        double ofrenda = Depositos.verOfrenda(indexDeposito);
        
        int indicePersona = Personas.buscarPersona(dniAportante);
        String nombrePersona = "Visitante / Anónimo";
        if (indicePersona != -1) {
            nombrePersona = Personas.verNombre(indicePersona);
        }
        
        int indiceIglesia = Iglesias.buscarIglesia(codigoIglesia);
        String nombreIglesia = "IASD 404";
        String direccionIglesia = "No se pudo encontrar la iglesia";
        if (indiceIglesia != -1) {
            nombreIglesia = Iglesias.verNombre(indiceIglesia);
            direccionIglesia = Iglesias.verDireccion(indiceIglesia);
        }

        double totalCalculado = diezmo + ofrenda;

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
            writer.write("  body { font-family: 'Courier New', Courier, monospace; margin: 20px; background-color: #f0f0f0; display: flex; justify-content: center; }\n");
            writer.write("  .ticket { max-width: 380px; width: 100%; padding: 20px; border: 1px dashed #777; background: #fff; box-shadow: 0 0 10px rgba(0,0,0,0.05); }\n");
            writer.write("  .center { text-align: center; }\n");
            writer.write("  .bold { font-weight: bold; }\n");
            writer.write("  .flex { display: flex; justify-content: space-between; margin: 6px 0; font-size: 13px; }\n");
            writer.write("  .divider { border-top: 1px dashed #333; margin: 12px 0; }\n");
            writer.write("</style>\n</head>\n<body>\n");

            // Encabezado del Ticket
            writer.write("<div class='ticket'>\n");
            writer.write("  <h2 class='center bold' style='margin-bottom: 5px;'>" + nombreIglesia + "</h2>\n");
            writer.write("  <p class='center' style='margin-top: 0; font-size: 12px;'>RUC: 00000000000<br>" + direccionIglesia + "</p>\n");
            writer.write("  <div class='divider'></div>\n");
            
            // Datos de la Transacción
            writer.write("  <p class='bold center' style='margin: 10px 0;'>COMPROBANTE DE APORTE</p>\n");
            writer.write("  <div class='flex'><span>Nro. Operación:</span> <span class='bold'>" + codigoDeposito + "</span></div>\n");
            writer.write("  <div class='flex'><span>Fecha:</span> <span>" + fecha + "</span></div>\n");
            writer.write("  <div class='flex'><span>DNI:</span> <span>" + dniAportante + "</span></div>\n");
            writer.write("  <div class='flex'><span>Aportante:</span> <span class='bold'>" + nombrePersona + "</span></div>\n");
            writer.write("  <div class='divider'></div>\n");
            
            // Desglose de importes (Agregado el formato de dos decimales .2f)
            writer.write("  <p class='bold' style='font-size: 14px; margin: 5px 0;'>DETALLE</p>\n");
            writer.write("  <div class='flex'><span>Diezmo</span> <span>S/. " + String.format("%.2f", diezmo) + "</span></div>\n");
            writer.write("  <div class='flex'><span>Ofrenda</span> <span>S/. " + String.format("%.2f", ofrenda) + "</span></div>\n");
            writer.write("  <div class='divider'></div>\n");
            
            // Total a pagar
            writer.write("  <div class='flex bold' style='font-size: 15px;'><span>TOTAL GENERAL:</span> <span>S/. " + String.format("%.2f", totalCalculado) + "</span></div>\n");
            writer.write("  <div class='divider'></div>\n");
            
            // Mensaje de cierre
            writer.write("  <p class='center bold' style='margin-top: 15px; font-size: 12px;'>¡Que Dios bendiga su generosidad!</p>\n");
            writer.write("</div>\n");

            writer.write("</body>\n</html>");
            
        } catch (IOException e) {
            System.err.println("Error crítico al estructurar el recibo HTML: " + e.getMessage());
        }
    }

    /**
     * Lanza el navegador web predeterminado del sistema operativo apuntando al archivo generado.
     */
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
            pb = new ProcessBuilder("cmd.exe", "/c", "start", '\"' + "Recibo" + '\"', archivoHtml.getAbsolutePath());
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