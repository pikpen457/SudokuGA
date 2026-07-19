package ga;

import model.Cromosoma;
import model.Sudoku;
import java.util.Random;

public class Cruza {
    private static Random random = new Random();
    private static final int TAMAÑO = 9;

    public static Cromosoma cruzar(Cromosoma padre1, Cromosoma padre2, Sudoku sudoku) {
        Cromosoma hijo = padre1.clone();
        
        for (int fila = 0; fila < TAMAÑO; fila++) {
            if (random.nextDouble() < 0.5) {
                for (int columna = 0; columna < TAMAÑO; columna++) {
                    if (!sudoku.esPosiconFija(fila, columna)) {
                        hijo.establecerValor(fila, columna, padre2.obtenerValor(fila, columna));
                    }
                }
            }
        }
        
        return hijo;
    }
}
