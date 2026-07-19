package io;

import model.Sudoku;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LectorSudoku {
    private static final int TAMAÑO = 9;

    public static Sudoku leerSudoku(String rutaArchivo) throws IOException {
        Sudoku sudoku = new Sudoku();
        int[][] tablero = new int[TAMAÑO][TAMAÑO];

        try (BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            int filaActual = 0;

            while ((linea = lector.readLine()) != null && filaActual < TAMAÑO) {
                linea = linea.trim();
                if (!linea.isEmpty()) {
                    String[] numeros = linea.split("\\s+");
                    
                    for (int columna = 0; columna < TAMAÑO && columna < numeros.length; columna++) {
                        try {
                            tablero[filaActual][columna] = Integer.parseInt(numeros[columna]);
                        } catch (NumberFormatException e) {
                            System.err.println("Error al parsear número en fila " + filaActual + ", columna " + columna);
                            tablero[filaActual][columna] = 0;
                        }
                    }
                    filaActual++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            throw e;
        }

        sudoku.establecerTableroOriginal(tablero);
        return sudoku;
    }

    public static boolean validarFormato(String rutaArchivo) {
        try (BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            int lineas = 0;

            while ((linea = lector.readLine()) != null && lineas < TAMAÑO) {
                linea = linea.trim();
                if (!linea.isEmpty()) {
                    String[] numeros = linea.split("\\s+");
                    if (numeros.length != TAMAÑO) {
                        return false;
                    }
                    lineas++;
                }
            }

            return lineas == TAMAÑO;
        } catch (IOException e) {
            return false;
        }
    }
}
