import java.util.ArrayList;
import java.util.HashMap;

/**
 * Clase que representa un grafo dirigido con pesos usando matriz de adyacencia.
 * Los nodos son ciudades de Guatemala.
 */
public class Graph {

    // usamos un valor muy grande para representar que no hay conexion directa
    public static final double INF = Double.MAX_VALUE / 2;

    private double[][] matrix;
    private HashMap<String, Integer> cityIndex; // mapea nombre -> indice en la matriz
    private ArrayList<String> cities;           // lista de ciudades en orden
    private int size;

    public Graph() {
        cityIndex = new HashMap<>();
        cities = new ArrayList<>();
        size = 0;
        matrix = new double[0][0];
    }

    // agrega una ciudad al grafo si no existe todavia
    public void addVertex(String city) {
        if (!cityIndex.containsKey(city)) {
            cityIndex.put(city, size);
            cities.add(city);
            size++;
            resizeMatrix();
        }
    }

    // cuando se agrega un nuevo nodo hay que expandir la matriz
    private void resizeMatrix() {
        double[][] newMatrix = new double[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    newMatrix[i][j] = 0;
                } else if (i < size - 1 && j < size - 1) {
                    // copiar los valores que ya existian
                    newMatrix[i][j] = matrix[i][j];
                } else {
                    newMatrix[i][j] = INF;
                }
            }
        }
        matrix = newMatrix;
    }

    // agrega un arco dirigido de 'from' hacia 'to' con peso km
    public void addEdge(String from, String to, double km) {
        addVertex(from);
        addVertex(to);
        int i = cityIndex.get(from);
        int j = cityIndex.get(to);
        matrix[i][j] = km;
    }

    // elimina un arco entre dos ciudades (lo pone en INF)
    public void removeEdge(String from, String to) {
        if (cityIndex.containsKey(from) && cityIndex.containsKey(to)) {
            int i = cityIndex.get(from);
            int j = cityIndex.get(to);
            matrix[i][j] = INF;
        }
    }

    // imprime la matriz de adyacencia de forma legible
    public void printMatrix() {
        System.out.printf("%-18s", "");
        for (String city : cities) {
            System.out.printf("%-18s", city);
        }
        System.out.println();

        for (int i = 0; i < size; i++) {
            System.out.printf("%-18s", cities.get(i));
            for (int j = 0; j < size; j++) {
                if (matrix[i][j] >= INF) {
                    System.out.printf("%-18s", "INF");
                } else {
                    System.out.printf("%-18.0f", matrix[i][j]);
                }
            }
            System.out.println();
        }
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public ArrayList<String> getCities() {
        return cities;
    }

    public HashMap<String, Integer> getCityIndex() {
        return cityIndex;
    }

    public int getSize() {
        return size;
    }
}
