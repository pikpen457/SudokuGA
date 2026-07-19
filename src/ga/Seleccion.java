package ga;

import model.Cromosoma;
import java.util.List;
import java.util.Random;

public class Seleccion {
    private static Random random = new Random();
    private static final int TAMAÑO_TORNEO = 5;

    public static Cromosoma seleccionarPorTorneo(List<Cromosoma> poblacion) {
        Cromosoma mejorCromosoma = poblacion.get(random.nextInt(poblacion.size()));
        
        for (int i = 1; i < TAMAÑO_TORNEO; i++) {
            Cromosoma candidato = poblacion.get(random.nextInt(poblacion.size()));
            if (candidato.obtenerFitness() < mejorCromosoma.obtenerFitness()) {
                mejorCromosoma = candidato;
            }
        }
        
        return mejorCromosoma;
    }
}
