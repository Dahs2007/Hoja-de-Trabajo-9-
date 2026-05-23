import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Programa principal para el HDT 9 - Algoritmo de Floyd.
 * Lee un grafo de ciudades guatemaltecas desde archivo y permite
 * consultar rutas mas cortas, ver el centro y modificar el grafo.
 */
public class Main {

    public static void main(String[] args) {
        Graph grafo = new Graph();
        Floyd floyd = new Floyd();
        Scanner sc = new Scanner(System.in);

        // leer el grafo desde el archivo
        System.out.println("Cargando grafo desde guategrafo.txt...");
        try {
            Scanner fileScanner = new Scanner(new File("guategrafo.txt"));
            while (fileScanner.hasNextLine()) {
                String linea = fileScanner.nextLine().trim();
                if (linea.isEmpty()) continue;

                String[] partes = linea.split("\\s+");
                if (partes.length == 3) {
                    String ciudad1 = partes[0];
                    String ciudad2 = partes[1];
                    double km = Double.parseDouble(partes[2]);
                    grafo.addEdge(ciudad1, ciudad2, km);
                }
            }
            fileScanner.close();
            System.out.println("Grafo cargado con " + grafo.getSize() + " ciudades.\n");
        } catch (FileNotFoundException e) {
            System.out.println("Error: no se encontro el archivo guategrafo.txt");
            sc.close();
            return;
        }

        // mostrar la matriz de adyacencia
        System.out.println("=== Matriz de Adyacencia ===");
        grafo.printMatrix();
        System.out.println();

        // aplicar Floyd-Warshall por primera vez
        floyd.runFloyd(grafo.getMatrix(), grafo.getCities());

        System.out.println("=== Matriz de Distancias Minimas (Floyd) ===");
        floyd.printDistMatrix();
        System.out.println();

        int opcion = 0;
        while (opcion != 4) {
            System.out.println("========== MENU ==========");
            System.out.println("1. Consultar ruta mas corta entre dos ciudades");
            System.out.println("2. Mostrar el centro del grafo");
            System.out.println("3. Modificar el grafo");
            System.out.println("4. Salir");
            System.out.print("Opcion: ");

            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un numero valido.\n");
                continue;
            }

            switch (opcion) {
                case 1:
                    consultarRuta(sc, grafo, floyd);
                    break;
                case 2:
                    System.out.println("\nEl centro del grafo es: " + floyd.findCenter() + "\n");
                    break;
                case 3:
                    modificarGrafo(sc, grafo, floyd);
                    break;
                case 4:
                    System.out.println("Saliendo... hasta luego!");
                    break;
                default:
                    System.out.println("Opcion no valida, intente de nuevo.\n");
            }
        }

        sc.close();
    }

    // muestra la ruta mas corta entre dos ciudades
    private static void consultarRuta(Scanner sc, Graph grafo, Floyd floyd) {
        System.out.print("\nCiudad de origen: ");
        String origen = sc.nextLine().trim();
        System.out.print("Ciudad de destino: ");
        String destino = sc.nextLine().trim();

        if (!grafo.getCityIndex().containsKey(origen)) {
            System.out.println("La ciudad '" + origen + "' no existe en el grafo.\n");
            return;
        }
        if (!grafo.getCityIndex().containsKey(destino)) {
            System.out.println("La ciudad '" + destino + "' no existe en el grafo.\n");
            return;
        }
        if (origen.equals(destino)) {
            System.out.println("El origen y destino son la misma ciudad.\n");
            return;
        }

        int i = grafo.getCityIndex().get(origen);
        int j = grafo.getCityIndex().get(destino);
        double distancia = floyd.getDistance(i, j);

        if (distancia >= Graph.INF) {
            System.out.println("No existe ruta de " + origen + " a " + destino + "\n");
        } else {
            ArrayList<String> ruta = floyd.getPath(i, j);
            System.out.println("Distancia minima: " + (int) distancia + " KM");
            System.out.println("Ruta: " + String.join(" -> ", ruta) + "\n");
        }
    }

    // permite agregar o eliminar arcos y recalcula Floyd
    private static void modificarGrafo(Scanner sc, Graph grafo, Floyd floyd) {
        System.out.println("\na) Interrumpir trafico entre dos ciudades (eliminar arco)");
        System.out.println("b) Establecer conexion entre dos ciudades (agregar/actualizar arco)");
        System.out.print("Opcion: ");
        String opt = sc.nextLine().trim().toLowerCase();

        if (opt.equals("a")) {
            System.out.print("Ciudad origen: ");
            String origen = sc.nextLine().trim();
            System.out.print("Ciudad destino: ");
            String destino = sc.nextLine().trim();

            if (!grafo.getCityIndex().containsKey(origen) || !grafo.getCityIndex().containsKey(destino)) {
                System.out.println("Una de las ciudades no existe en el grafo.\n");
                return;
            }
            grafo.removeEdge(origen, destino);
            System.out.println("Conexion de " + origen + " a " + destino + " eliminada.");

        } else if (opt.equals("b")) {
            System.out.print("Ciudad origen: ");
            String origen = sc.nextLine().trim();
            System.out.print("Ciudad destino: ");
            String destino = sc.nextLine().trim();
            System.out.print("Distancia en KM: ");

            try {
                double km = Double.parseDouble(sc.nextLine().trim());
                grafo.addEdge(origen, destino, km);
                System.out.println("Conexion de " + origen + " a " + destino + " (" + (int)km + " KM) agregada.");
            } catch (NumberFormatException e) {
                System.out.println("Distancia invalida.\n");
                return;
            }
        } else {
            System.out.println("Opcion no valida.\n");
            return;
        }

        // recalcular Floyd despues de la modificacion
        floyd.runFloyd(grafo.getMatrix(), grafo.getCities());
        System.out.println("Rutas recalculadas exitosamente.");
        System.out.println("Nuevo centro del grafo: " + floyd.findCenter() + "\n");
    }
}
