package ga;

import model.Cromosoma;
import model.Sudoku;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Poblacion {
    private List<Cromosoma> individuos;
    private static final int TAMAÑO_POBLACION = 300;
    private static final int TAMAÑO = 9;
    private static Random random = new Random();

    public Poblacion(Sudoku sudoku) {
        this.individuos = new ArrayList<>();
        generarPoblacionInicial(sudoku);
    }

    private void generarPoblacionInicial(Sudoku sudoku) {
        for (int i = 0; i < TAMAÑO_POBLACION; i++) {
            Cromosoma cromosoma = new Cromosoma(sudoku);
            llenarCeldasVariables(cromosoma, sudoku);
            individuos.add(cromosoma);
        }
    }

    private void llenarCeldasVariables(Cromosoma cromosoma, Sudoku sudoku) {
        for (int fila = 0; fila < TAMAÑO; fila++) {
            for (int columna = 0; columna < TAMAÑO; columna++) {
                if (!sudoku.esPosiconFija(fila, columna) && cromosoma.obtenerValor(fila, columna) == 0) {
                    int numero = random.nextInt(9) + 1;
                    cromosoma.establecerValor(fila, columna, numero);
                }
            }
        }
    }

    public List<Cromosoma> obtenerIndividuos() {
        return individuos;
    }

    public Cromosoma obtenerMejorIndividuo() {
        return Collections.min(individuos);
    }

    public int obtenerTamaño() {
        return individuos.size();
    }

    public void ordenarPorFitness() {
        Collections.sort(individuos);
    }

    public void establecerIndividuos(List<Cromosoma> nuevosIndividuos) {
        this.individuos = nuevosIndividuos;
    }
}
