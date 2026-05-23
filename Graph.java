import java.util.*;

public class Graph {

    public static final double INF = Double.MAX_VALUE / 2;

    private List<String> nodes;
    private double[][] matrix;

    public Graph() {
        nodes = new ArrayList<>();
        matrix = new double[0][0];
    }

    public void addNode(String name) {
        if (nodes.contains(name)) return;
        nodes.add(name);
        int n = nodes.size();
        double[][] newMatrix = new double[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                newMatrix[i][j] = (i == j) ? 0 : INF;
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - 1; j++)
                newMatrix[i][j] = matrix[i][j];
        matrix = newMatrix;
    }

    public void addArc(String from, String to, double km) {
        addNode(from);
        addNode(to);
        matrix[indexOf(from)][indexOf(to)] = km;
    }

    public boolean removeArc(String from, String to) {
        int i = indexOf(from), j = indexOf(to);
        if (i == -1 || j == -1 || matrix[i][j] == INF) return false;
        matrix[i][j] = INF;
        return true;
    }

    public double getWeight(String from, String to) {
        int i = indexOf(from), j = indexOf(to);
        if (i == -1 || j == -1) return INF;
        return matrix[i][j];
    }

    public int indexOf(String name) { return nodes.indexOf(name); }
    public List<String> getNodes()  { return Collections.unmodifiableList(nodes); }
    public int size()               { return nodes.size(); }

    public double[][] getMatrixCopy() {
        int n = nodes.size();
        double[][] copy = new double[n][n];
        for (int i = 0; i < n; i++)
            copy[i] = Arrays.copyOf(matrix[i], n);
        return copy;
    }

    public void printMatrix() {
        System.out.printf("%-20s", "");
        for (String node : nodes) System.out.printf("%-15s", node);
        System.out.println();
        for (int i = 0; i < nodes.size(); i++) {
            System.out.printf("%-20s", nodes.get(i));
            for (int j = 0; j < nodes.size(); j++)
                System.out.printf("%-15s", matrix[i][j] == INF ? "INF" : (int) matrix[i][j]);
            System.out.println();
        }
    }
}