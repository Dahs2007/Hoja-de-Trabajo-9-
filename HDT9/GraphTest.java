import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para Graph y Floyd con JUnit 5.
 */
public class GraphTest {

    private Graph grafo;
    private Floyd floyd;

    @BeforeEach
    public void setUp() {
        grafo = new Graph();
        floyd = new Floyd();
    }

    // ----- pruebas de Graph -----

    @Test
    public void testAddVertex() {
        grafo.addVertex("Guatemala");
        assertTrue(grafo.getCities().contains("Guatemala"));
        assertEquals(1, grafo.getSize());
    }

    @Test
    public void testAddVertexNoDuplicado() {
        // agregar la misma ciudad dos veces no debe crear duplicado
        grafo.addVertex("Mixco");
        grafo.addVertex("Mixco");
        assertEquals(1, grafo.getSize());
    }

    @Test
    public void testAddEdge() {
        grafo.addEdge("Mixco", "Antigua", 30);
        int i = grafo.getCityIndex().get("Mixco");
        int j = grafo.getCityIndex().get("Antigua");
        assertEquals(30, grafo.getMatrix()[i][j]);
    }

    @Test
    public void testAddEdgeCreaVertices() {
        // al agregar un arco se deben crear los nodos si no existen
        grafo.addEdge("Escuintla", "SantaLucia", 15);
        assertEquals(2, grafo.getSize());
        assertTrue(grafo.getCities().contains("Escuintla"));
        assertTrue(grafo.getCities().contains("SantaLucia"));
    }

    @Test
    public void testRemoveEdge() {
        grafo.addEdge("Mixco", "Antigua", 30);
        grafo.removeEdge("Mixco", "Antigua");
        int i = grafo.getCityIndex().get("Mixco");
        int j = grafo.getCityIndex().get("Antigua");
        // despues de eliminar debe quedar en INF
        assertTrue(grafo.getMatrix()[i][j] >= Graph.INF);
    }

    @Test
    public void testMatrizDiagonalCero() {
        // la diagonal de la matriz siempre debe ser 0 (distancia de un nodo a si mismo)
        grafo.addEdge("Guatemala", "Mixco", 20);
        grafo.addEdge("Mixco", "Antigua", 40);
        for (int i = 0; i < grafo.getSize(); i++) {
            assertEquals(0, grafo.getMatrix()[i][i]);
        }
    }

    // ----- pruebas de Floyd -----

    @Test
    public void testFloydCaminoDirecto() {
        grafo.addEdge("Mixco", "Antigua", 30);
        grafo.addEdge("Antigua", "Escuintla", 25);
        floyd.runFloyd(grafo.getMatrix(), grafo.getCities());

        int i = grafo.getCityIndex().get("Mixco");
        int j = grafo.getCityIndex().get("Escuintla");
        // deberia ser 30 + 25 = 55
        assertEquals(55, floyd.getDistance(i, j));
    }

    @Test
    public void testFloydSinCamino() {
        // no hay camino de Mixco a SantaLucia en este grafo
        grafo.addEdge("Mixco", "Antigua", 30);
        grafo.addEdge("Escuintla", "SantaLucia", 15);
        floyd.runFloyd(grafo.getMatrix(), grafo.getCities());

        int i = grafo.getCityIndex().get("Mixco");
        int j = grafo.getCityIndex().get("SantaLucia");
        assertTrue(floyd.getDistance(i, j) >= Graph.INF);
    }

    @Test
    public void testFloydCaminoMasCorto() {
        // camino directo vs camino alternativo mas corto
        grafo.addEdge("A", "B", 100);
        grafo.addEdge("A", "C", 10);
        grafo.addEdge("C", "B", 20);
        floyd.runFloyd(grafo.getMatrix(), grafo.getCities());

        int i = grafo.getCityIndex().get("A");
        int j = grafo.getCityIndex().get("B");
        // el camino A->C->B = 30 es mas corto que A->B = 100
        assertEquals(30, floyd.getDistance(i, j));
    }

    @Test
    public void testGetPath() {
        grafo.addEdge("Mixco", "Antigua", 30);
        grafo.addEdge("Antigua", "Escuintla", 25);
        grafo.addEdge("Escuintla", "SantaLucia", 15);
        floyd.runFloyd(grafo.getMatrix(), grafo.getCities());

        int i = grafo.getCityIndex().get("Mixco");
        int j = grafo.getCityIndex().get("SantaLucia");
        ArrayList<String> path = floyd.getPath(i, j);

        assertFalse(path.isEmpty());
        assertEquals("Mixco", path.get(0));
        assertEquals("SantaLucia", path.get(path.size() - 1));
    }

    @Test
    public void testGetPathSinCamino() {
        grafo.addEdge("Mixco", "Antigua", 30);
        grafo.addEdge("Escuintla", "SantaLucia", 15);
        floyd.runFloyd(grafo.getMatrix(), grafo.getCities());

        int i = grafo.getCityIndex().get("Mixco");
        int j = grafo.getCityIndex().get("SantaLucia");
        ArrayList<String> path = floyd.getPath(i, j);
        // si no hay camino el path debe estar vacio
        assertTrue(path.isEmpty());
    }

    @Test
    public void testFindCenter() {
        // grafo con ciclo para que todos sean alcanzables
        grafo.addEdge("Mixco", "Antigua", 30);
        grafo.addEdge("Antigua", "Escuintla", 25);
        grafo.addEdge("Escuintla", "Mixco", 20);
        floyd.runFloyd(grafo.getMatrix(), grafo.getCities());

        String centro = floyd.findCenter();
        assertNotNull(centro);
        assertTrue(grafo.getCities().contains(centro));
    }
}
