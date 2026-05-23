import java.io.*;
import java.util.*;

public class Main {

    private static Graph graph = new Graph();
    private static Floyd floyd = new Floyd(graph);
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String file = args.length > 0 ? args[0] : "guategrafo.txt";
        loadGraph(file);

        System.out.println("\n=== Grafo cargado ===");
        graph.printMatrix();
        floyd.run();
        System.out.println("\n=== Matriz APSP ===");
        floyd.printDistMatrix();

        menu();
    }

    private static void loadGraph(String file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.trim().split("\\s+");
                if (p.length < 3) continue;
                graph.addArc(p[0], p[1], Double.parseDouble(p[2]));
            }
            System.out.println("Archivo leido: " + file);
        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo.");
        }
    }

    private static void menu() {
        int op = 0;
        do {
            System.out.println("\n1. Ruta mas corta");
            System.out.println("2. Centro del grafo");
            System.out.println("3. Modificar grafo");
            System.out.println("4. Salir");
            System.out.print("Opcion: ");
            try { op = Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) { op = -1; }

            if (op == 1) shortestPathQuery();
            else if (op == 2) System.out.println("Centro: " + floyd.graphCenter());
            else if (op == 3) modifyGraph();
            else if (op == 4) System.out.println("Hasta luego.");
            else System.out.println("Opcion invalida.");
        } while (op != 4);
    }

    private static void shortestPathQuery() {
        System.out.print("Origen:  "); String from = sc.nextLine().trim();
        System.out.print("Destino: "); String to   = sc.nextLine().trim();

        if (graph.indexOf(from) == -1 || graph.indexOf(to) == -1) {
            System.out.println("Ciudad no encontrada."); return;
        }
        double d = floyd.shortestDistance(from, to);
        if (d >= Graph.INF) { System.out.println("No hay ruta."); return; }

        System.out.println("Distancia: " + (int) d + " KM");
        System.out.println("Ruta: " + String.join(" -> ", floyd.shortestPath(from, to)));
    }

    private static void modifyGraph() {
        System.out.println("a) Eliminar conexion  b) Agregar conexion");
        System.out.print("Sub-opcion: ");
        String sub = sc.nextLine().trim().toLowerCase();

        System.out.print("Origen:  "); String c1 = sc.nextLine().trim();
        System.out.print("Destino: "); String c2 = sc.nextLine().trim();

        if (sub.equals("a")) {
            if (graph.removeArc(c1, c2)) {
                floyd.run();
                System.out.println("Eliminada. Nuevo centro: " + floyd.graphCenter());
            } else System.out.println("No existia esa conexion.");

        } else if (sub.equals("b")) {
            System.out.print("KM: ");
            try {
                graph.addArc(c1, c2, Double.parseDouble(sc.nextLine().trim()));
                floyd.run();
                System.out.println("Agregada. Nuevo centro: " + floyd.graphCenter());
            } catch (NumberFormatException e) { System.out.println("KM invalido."); }
        }
    }
}