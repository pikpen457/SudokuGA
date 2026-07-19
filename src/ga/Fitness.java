package ga;

import model.Cromosoma;

public class Fitness {
    private static final int TAMAÑO = 9;
    private static final int SUBCUADRICULA = 3;
    private static final int SUMA_OBJETIVO = 45;

    public static double calcularFitness(Cromosoma cromosoma) {
        int conflictos = 0;

        conflictos += contarConflictosFilas(cromosoma);
        conflictos += contarConflictosColumnas(cromosoma);
        conflictos += contarConflictosSubcuadriculas(cromosoma);

        return conflictos;
    }

    private static int contarConflictosFilas(Cromosoma cromosoma) {
        int conflictos = 0;
        for (int fila = 0; fila < TAMAÑO; fila++) {
            boolean[] numerosVistos = new boolean[10];
            for (int columna = 0; columna < TAMAÑO; columna++) {
                int valor = cromosoma.obtenerValor(fila, columna);
                if (valor > 0 && valor <= TAMAÑO) {
                    if (numerosVistos[valor]) {
                        conflictos++;
                    }
                    numerosVistos[valor] = true;
                }
            }
        }
        return conflictos;
    }

    private static int contarConflictosColumnas(Cromosoma cromosoma) {
        int conflictos = 0;
        for (int columna = 0; columna < TAMAÑO; columna++) {
            boolean[] numerosVistos = new boolean[10];
            for (int fila = 0; fila < TAMAÑO; fila++) {
                int valor = cromosoma.obtenerValor(fila, columna);
                if (valor > 0 && valor <= TAMAÑO) {
                    if (numerosVistos[valor]) {
                        conflictos++;
                    }
                    numerosVistos[valor] = true;
                }
            }
        }
        return conflictos;
    }

    private static int contarConflictosSubcuadriculas(Cromosoma cromosoma) {
        int conflictos = 0;
        for (int bloqueFilas = 0; bloqueFilas < SUBCUADRICULA; bloqueFilas++) {
            for (int bloqueColumnas = 0; bloqueColumnas < SUBCUADRICULA; bloqueColumnas++) {
                boolean[] numerosVistos = new boolean[10];
                for (int fila = bloqueFilas * SUBCUADRICULA; fila < (bloqueFilas + 1) * SUBCUADRICULA; fila++) {
                    for (int columna = bloqueColumnas * SUBCUADRICULA; columna < (bloqueColumnas + 1) * SUBCUADRICULA; columna++) {
                        int valor = cromosoma.obtenerValor(fila, columna);
                        if (valor > 0 && valor <= TAMAÑO) {
                            if (numerosVistos[valor]) {
                                conflictos++;
                            }
                            numerosVistos[valor] = true;
                        }
                    }
                }
            }
        }
        return conflictos;
    }
}
