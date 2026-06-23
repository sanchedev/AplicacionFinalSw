/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author Laptop
 */
public class Archivo {

    private final String[] encabezados = new String[1000];
    private int cantidadDeEncabezados = 0;
    private final String[][] datos = new String[1000][1000];
    private final int[] cantidades = new int[1000];

    private final String nombreArchivo;

    public Archivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public void agregarCabecera(String nombre) {
        encabezados[cantidadDeEncabezados] = nombre;
        cantidadDeEncabezados++;
    }

    public int buscarCabecera(String nombre) {
        int indice = -1;
        for (int i = 0; i < cantidadDeEncabezados; i++) {
            if (encabezados[i].equals(nombre)) {
                indice = i;
                break;
            }
        }
        return indice;
    }

    public void agregarDatos(String nombre, String dato) {
        int indice = buscarCabecera(nombre);
        if (indice == -1) {
            return;
        }
        datos[indice][cantidades[indice]] = dato;
        cantidades[indice]++;
    }

    public String verDato(String nombre, int indice) {
        int indicecabecera = buscarCabecera(nombre);
        if (indicecabecera == -1) {
            return " ";
        }
        return datos[indicecabecera][indice];
    }

    public String verCabecera(int indice) {
        if (indice < 0 || indice >= cantidadDeEncabezados) {
            return "";
        }
        return encabezados[indice];
    }

    public int verCantidad(String nombre) {
        int indice = buscarCabecera(nombre);
        if (indice == -1) {
            return 0;
        }
        return cantidades[indice];
    }

    public int verCantidadDatos() {
        int cantidad = cantidades[0];
        for (int i = 0; i < cantidadDeEncabezados; i++) {
            if (cantidades[i] < cantidad) {
                cantidad = cantidades[i];
            }
        }
        return cantidad;
    }

    public int verCantidadCabeceras() {
        return cantidadDeEncabezados;
    }

    public void guardar() {
        Path path = Path.of(nombreArchivo);
        try {
            Files.createDirectories(path.getParent());

            try (FileWriter fw = new FileWriter(path.toFile(), false)) {
                PrintWriter escri = new PrintWriter(fw);
                for (int i = 0; i < cantidadDeEncabezados; i++) {
                    if (i != 0) {
                        escri.print(";");
                    }
                    escri.print(encabezados[i]);
                }
                escri.println();
                for (int i = 0; i < cantidades[0]; i++) {
                    if (i != 0) {
                        escri.println();
                    }
                    for (int j = 0; j < cantidadDeEncabezados; j++) {
                        if (j != 0) {
                            escri.print(";");
                        }
                        escri.print(datos[j][i]);
                    }
                }
            }

        } catch (IOException e) {
        }
    }

    public void leer() {
        Path path = Path.of(nombreArchivo);
        try {
            Files.createDirectories(path.getParent());
            try (BufferedReader bf = new BufferedReader(new FileReader(path.toFile()))) {
                String linea = bf.readLine();
                String[] encabezadosLeidos = linea.split(";", -1);

                if (verCantidadCabeceras() != encabezadosLeidos.length) {
                    bf.close();
                    return;
                }

                for (int i = 0; i < encabezadosLeidos.length; i++) {
                    if (!verCabecera(i).equals(encabezadosLeidos[i])) {
                        bf.close();
                        return;
                    }
                }
                while ((linea = bf.readLine()) != null) {
                    String[] fila = linea.split(";", -1);
                    if (encabezadosLeidos.length != fila.length) {
                        break;
                    }
                    for (int i = 0; i < fila.length; i++) {
                        agregarDatos(encabezadosLeidos[i], fila[i]);
                    }
                }
            }
        } catch (IOException e) {
        }

    }
}
