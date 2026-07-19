package model;

public class Sudoku {
    private int[][] tablero;
    private int[][] tableroOriginal;
    private static final int TAMAÑO = 9;
    private static final int SUBCUADRICULA = 3;

    public Sudoku() {
        this.tablero = new int[TAMAÑO][TAMAÑO];
        this.tableroOriginal = new int[TAMAÑO][TAMAÑO];
    }

    public int obtenerValor(int fila, int columna) {
        return tablero[fila][columna];
    }

    public void establecerValor(int fila, int columna, int valor) {
        if (!esPosiconFija(fila, columna)) {
            tablero[fila][columna] = valor;
        }
    }

    public boolean esPosiconFija(int fila, int columna) {
        return tableroOriginal[fila][columna] != 0;
    }

    public int[][] obtenerCopia() {
        int[][] copia = new int[TAMAÑO][TAMAÑO];
        for (int i = 0; i < TAMAÑO; i++) {
            for (int j = 0; j < TAMAÑO; j++) {
                copia[i][j] = tablero[i][j];
            }
        }
        return copia;
    }

    public void establecerTableroOriginal(int[][] tableroOriginal) {
        for (int i = 0; i < TAMAÑO; i++) {
            for (int j = 0; j < TAMAÑO; j++) {
                this.tableroOriginal[i][j] = tableroOriginal[i][j];
                this.tablero[i][j] = tableroOriginal[i][j];
            }
        }
    }

    public int[][] obtenerTablero() {
        return tablero;
    }

    public int[][] obtenerTableroOriginal() {
        return tableroOriginal;
    }

    public static int getTAMAÑO() {
        return TAMAÑO;
    }

    public static int getSUBCUADRICULA() {
        return SUBCUADRICULA;
    }
}
