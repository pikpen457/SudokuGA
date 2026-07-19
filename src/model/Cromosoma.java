package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cromosoma implements Comparable<Cromosoma> {
    private int[][] genes;
    private int[][] tableroOriginal;
    private double fitness;
    private static final int TAMAÑO = 9;
    private static final int SUBCUADRICULA = 3;
    private static Random random = new Random();
    private List<Integer> posicionesVariables;

    public Cromosoma(Sudoku sudoku) {
        this.tableroOriginal = sudoku.obtenerTableroOriginal();
        this.genes = sudoku.obtenerCopia();
        this.posicionesVariables = new ArrayList<>();
        
        for (int i = 0; i < TAMAÑO; i++) {
            for (int j = 0; j < TAMAÑO; j++) {
                if (tableroOriginal[i][j] == 0) {
                    posicionesVariables.add(i * TAMAÑO + j);
                }
            }
        }
        
        this.fitness = 0;
    }

    public Cromosoma(Cromosoma otro) {
        this.tableroOriginal = otro.tableroOriginal;
        this.genes = new int[TAMAÑO][TAMAÑO];
        for (int i = 0; i < TAMAÑO; i++) {
            for (int j = 0; j < TAMAÑO; j++) {
                this.genes[i][j] = otro.genes[i][j];
            }
        }
        this.posicionesVariables = new ArrayList<>(otro.posicionesVariables);
        this.fitness = otro.fitness;
    }

    public int obtenerValor(int fila, int columna) {
        return genes[fila][columna];
    }

    public void establecerValor(int fila, int columna, int valor) {
        if (tableroOriginal[fila][columna] == 0) {
            genes[fila][columna] = valor;
        }
    }

    public int[][] obtenerGenes() {
        return genes;
    }

    public void establecerFitness(double fitness) {
        this.fitness = fitness;
    }

    public double obtenerFitness() {
        return fitness;
    }

    @Override
    public Cromosoma clone() {
        return new Cromosoma(this);
    }

    @Override
    public int compareTo(Cromosoma otro) {
        return Double.compare(this.fitness, otro.fitness);
    }

    public List<Integer> obtenerPosicionesVariables() {
        return posicionesVariables;
    }

    public boolean esPosiconFija(int fila, int columna) {
        return tableroOriginal[fila][columna] != 0;
    }
}
