package ga;

import model.Cromosoma;
import model.Sudoku;
import java.util.ArrayList;
import java.util.List;

public class AlgoritmoGenetico {
    private Sudoku sudoku;
    private Poblacion poblacion;
    private int generacionActual;
    private int maxGeneraciones;
    private Cromosoma mejorSolucion;
    private static final int TAMAÑO_ELITE = 10;
    private boolean solucionEncontrada;

    public AlgoritmoGenetico(Sudoku sudoku, int maxGeneraciones) {
        this.sudoku = sudoku;
        this.maxGeneraciones = maxGeneraciones;
        this.generacionActual = 0;
        this.poblacion = new Poblacion(sudoku);
        this.mejorSolucion = null;
        this.solucionEncontrada = false;
    }

    public void ejecutar() {
        while (generacionActual < maxGeneraciones && !solucionEncontrada) {
            for (Cromosoma cromosoma : poblacion.obtenerIndividuos()) {
                double fitness = Fitness.calcularFitness(cromosoma);
                cromosoma.establecerFitness(fitness);
            }

            poblacion.ordenarPorFitness();

            Cromosoma mejorGeneral = poblacion.obtenerMejorIndividuo();
            if (mejorSolucion == null || mejorGeneral.obtenerFitness() < mejorSolucion.obtenerFitness()) {
                mejorSolucion = mejorGeneral.clone();
            }

            if (mejorSolucion.obtenerFitness() == 0) {
                solucionEncontrada = true;
                break;
            }

            List<Cromosoma> nuevaPoblacion = new ArrayList<>();

            for (int i = 0; i < TAMAÑO_ELITE; i++) {
                nuevaPoblacion.add(poblacion.obtenerIndividuos().get(i).clone());
            }

            while (nuevaPoblacion.size() < poblacion.obtenerTamaño()) {
                Cromosoma padre1 = Seleccion.seleccionarPorTorneo(poblacion.obtenerIndividuos());
                Cromosoma padre2 = Seleccion.seleccionarPorTorneo(poblacion.obtenerIndividuos());

                Cromosoma hijo = Cruza.cruzar(padre1, padre2, sudoku);

                Mutacion.mutar(hijo);

                nuevaPoblacion.add(hijo);
            }

            poblacion.establecerIndividuos(nuevaPoblacion);
            generacionActual++;
        }
    }

    public Cromosoma obtenerMejorSolucion() {
        return mejorSolucion;
    }

    public int obtenerGeneracionActual() {
        return generacionActual;
    }

    public boolean esSolucionEncontrada() {
        return solucionEncontrada;
    }

    public double obtenerFitnessFinal() {
        return mejorSolucion != null ? mejorSolucion.obtenerFitness() : -1;
    }
}
