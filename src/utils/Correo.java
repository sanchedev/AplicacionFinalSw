/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.awt.Desktop;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author sanchedev
 */
public class Correo {

    public static void abrirClienteCorreo(String destinatario, String asunto, String cuerpo) {
        if (!Desktop.isDesktopSupported()) {
            Errores.personalizado("La clase Desktop no está soportada en este sistema.");
            return;
        }

        Desktop desktop = Desktop.getDesktop();

        if (!desktop.isSupported(Desktop.Action.MAIL)) {
            Errores.personalizado("El envío de correos no está soportado en este sistema.");
            return;
        }

        try {
            String subjectEncoded = URLEncoder.encode(asunto, StandardCharsets.UTF_8);
            String bodyEncoded = URLEncoder.encode(cuerpo, StandardCharsets.UTF_8);

            String mailto = String.format("mailto:%s?subject=%s&body=%s",
                    destinatario, subjectEncoded, bodyEncoded);

            desktop.mail(new URI(mailto));

        } catch (Exception e) {
            Errores.personalizado("Error al abrir la aplicación de correo: " + e.getMessage());
        }
    }
}
