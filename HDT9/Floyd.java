import java.util.ArrayList;

/**
 * Implementacion del algoritmo de Floyd-Warshall para calcular
 * el camino mas corto entre cualquier par de nodos del grafo.
 */
public class Floyd {

    private static final double INF = Graph.INF;

    private double[][] dist; // matriz con las distancias minimas finales
    private int[][] next;    // para reconstruir el camino
    private int size;
    private ArrayList<String> cities;

    /**
     * Corre el algoritmo de Floyd sobre la matriz del grafo.
     * Hay que llamar este metodo antes de consultar distancias o rutas.
     */
    public void runFloyd(double[][] matrix, ArrayList<String> cities) {
        this.size = matrix.length;
        this.cities = cities;

        dist = new double[size][size];
        next = new int[size][size];

        // inicializar con los valores de la matriz original
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                dist[i][j] = matrix[i][j];
                if (matrix[i][j] < INF && i != j) {
                    next[i][j] = j; // el siguiente paso directo es j
                } else {
                    next[i][j] = -1; // no hay camino
                }
            }
        }

        // triple loop de Floyd: probar si pasar por k mejora el camino i->j
        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (dist[i][k] < INF && dist[k][j] < INF) {
                        if (dist[i][k] + dist[k][j] < dist[i][j]) {
                            dist[i][j] = dist[i][k] + dist[k][j];
                            next[i][j] = next[i][k];
                        }
                    }
                }
            }
        }
    }

    // retorna la distancia minima entre los nodos i y j
    public double getDistance(int from, int to) {
        return dist[from][to];
    }

    // reconstruye y retorna la lista de ciudades en la ruta mas corta
    public ArrayList<String> getPath(int from, int to) {
        ArrayList<String> path = new ArrayList<>();

        if (next[from][to] == -1) {
            return path; // lista vacia = no hay camino
        }

        path.add(cities.get(from));
        int current = from;
        while (current != to) {
            current = next[current][to];
            path.add(cities.get(current));
        }
        return path;
    }

    /**
     * Calcula el centro del grafo.
     *
     * La excentricidad de un nodo v es el maximo de las distancias minimas
     * desde cualquier otro nodo hacia v (maximo por columna en la matriz APSP).
     * El centro es el nodo con menor excentricidad.
     */
    public String findCenter() {
        double minEccentricity = INF;
        int centerIndex = -1;

        for (int i = 0; i < size; i++) {
            double eccentricity = 0;
            boolean unreachable = false;

            for (int j = 0; j < size; j++) {
                if (j == i) continue;
                if (dist[j][i] >= INF) {
                    unreachable = true;
                    break;
                }
                if (dist[j][i] > eccentricity) {
                    eccentricity = dist[j][i];
                }
            }

            // si no se puede llegar desde algun nodo, la excentricidad es infinita
            if (unreachable) continue;

            if (eccentricity < minEccentricity) {
                minEccentricity = eccentricity;
                centerIndex = i;
            }
        }

        if (centerIndex == -1) {
            return "No se puede determinar (grafo desconectado)";
        }
        return cities.get(centerIndex);
    }

    // imprime la matriz de distancias minimas despues de correr Floyd
    public void printDistMatrix() {
        System.out.printf("%-18s", "");
        for (String city : cities) {
            System.out.printf("%-18s", city);
        }
        System.out.println();

        for (int i = 0; i < size; i++) {
            System.out.printf("%-18s", cities.get(i));
            for (int j = 0; j < size; j++) {
                if (dist[i][j] >= INF) {
                    System.out.printf("%-18s", "INF");
                } else {
                    System.out.printf("%-18.0f", dist[i][j]);
                }
            }
            System.out.println();
        }
    }

    public double[][] getDist() {
        return dist;
    }
}
