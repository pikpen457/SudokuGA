package ga;

import model.Cromosoma;
import java.util.Random;

public class Mutacion {
    private static Random random = new Random();
    private static final int TAMAÑO = 9;
    private static double PROBABILIDAD_MUTACION = 0.1;

    public static void mutar(Cromosoma cromosoma) {
        if (random.nextDouble() < PROBABILIDAD_MUTACION) {
            int filaAleatoria = random.nextInt(TAMAÑO);
            
            int col1 = random.nextInt(TAMAÑO);
            int col2 = random.nextInt(TAMAÑO);
            
            while (col1 == col2) {
                col2 = random.nextInt(TAMAÑO);
            }
            
            if (!cromosoma.esPosiconFija(filaAleatoria, col1) && !cromosoma.esPosiconFija(filaAleatoria, col2)) {
                int temp = cromosoma.obtenerValor(filaAleatoria, col1);
                cromosoma.establecerValor(filaAleatoria, col1, cromosoma.obtenerValor(filaAleatoria, col2));
                cromosoma.establecerValor(filaAleatoria, col2, temp);
            }
        }
    }

    public static void establecerProbabilidadMutacion(double probabilidad) {
        PROBABILIDAD_MUTACION = probabilidad;
    }
}
