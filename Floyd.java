import java.util.*;

public class Floyd {

    private Graph graph;
    private double[][] dist;
    private int[][] next;

    public Floyd(Graph graph) { this.graph = graph; }

    public void run() {
        int n = graph.size();
        dist = graph.getMatrixCopy();
        next = new int[n][n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                next[i][j] = (dist[i][j] < Graph.INF && i != j) ? j : -1;

        for (int k = 0; k < n; k++)
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    if (dist[i][k] < Graph.INF && dist[k][j] < Graph.INF)
                        if (dist[i][k] + dist[k][j] < dist[i][j]) {
                            dist[i][j] = dist[i][k] + dist[k][j];
                            next[i][j] = next[i][k];
                        }
    }

    public double shortestDistance(String from, String to) {
        int i = graph.indexOf(from), j = graph.indexOf(to);
        if (i == -1 || j == -1) return Graph.INF;
        return dist[i][j];
    }

    public List<String> shortestPath(String from, String to) {
        int i = graph.indexOf(from), j = graph.indexOf(to);
        if (i == -1 || j == -1 || dist[i][j] >= Graph.INF) return Collections.emptyList();

        List<String> path = new ArrayList<>();
        List<String> nodes = graph.getNodes();
        int cur = i;
        path.add(nodes.get(cur));
        while (cur != j) {
            cur = next[cur][j];
            if (cur == -1) return Collections.emptyList();
            path.add(nodes.get(cur));
        }
        return path;
    }

    // Eccentricity of vertex j = max dist[i][j] for all i.
    // Center = vertex with minimum eccentricity.
    public String graphCenter() {
        if (graph.size() == 0) return null;
        List<String> nodes = graph.getNodes();
        double minEcc = Graph.INF;
        String center = null;

        for (int j = 0; j < nodes.size(); j++) {
            double ecc = 0;
            for (int i = 0; i < nodes.size(); i++) {
                if (i == j) continue;
                if (dist[i][j] >= Graph.INF) { ecc = Graph.INF; break; }
                ecc = Math.max(ecc, dist[i][j]);
            }
            if (ecc < minEcc) { minEcc = ecc; center = nodes.get(j); }
        }
        return center;
    }

    public void printDistMatrix() {
        List<String> nodes = graph.getNodes();
        System.out.printf("%-20s", "APSP");
        for (String node : nodes) System.out.printf("%-15s", node);
        System.out.println();
        for (int i = 0; i < nodes.size(); i++) {
            System.out.printf("%-20s", nodes.get(i));
            for (int j = 0; j < nodes.size(); j++)
                System.out.printf("%-15s", dist[i][j] >= Graph.INF ? "INF" : (int) dist[i][j]);
            System.out.println();
        }
    }
}