import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class GraphFloydTest {

    private Graph g;
    private Floyd floyd;

    @BeforeEach
    void setup() { g = new Graph(); floyd = new Floyd(g); }

    @Test void testAddNode() { g.addNode("Mixco"); assertEquals(1, g.size()); }

    @Test void testAddNodeDuplicate() { g.addNode("Mixco"); g.addNode("Mixco"); assertEquals(1, g.size()); }

    @Test void testAddArc() {
        g.addArc("Mixco", "Antigua", 30);
        assertEquals(30, g.getWeight("Mixco", "Antigua"), 0.001);
        assertEquals(Graph.INF, g.getWeight("Antigua", "Mixco"), 0.001);
    }

    @Test void testRemoveArc() {
        g.addArc("Mixco", "Antigua", 30);
        assertTrue(g.removeArc("Mixco", "Antigua"));
        assertEquals(Graph.INF, g.getWeight("Mixco", "Antigua"), 0.001);
    }

    @Test void testRemoveArcNonExistent() {
        g.addNode("Mixco"); g.addNode("Antigua");
        assertFalse(g.removeArc("Mixco", "Antigua"));
    }

    @Test void testFloydDirect() {
        g.addArc("A", "B", 30); floyd.run();
        assertEquals(30, floyd.shortestDistance("A", "B"), 0.001);
    }

    @Test void testFloydIndirect() {
        g.addArc("A", "B", 30); g.addArc("B", "C", 25); floyd.run();
        assertEquals(55, floyd.shortestDistance("A", "C"), 0.001);
    }

    @Test void testFloydINF() {
        g.addArc("A", "B", 30); g.addNode("C"); floyd.run();
        assertEquals(Graph.INF, floyd.shortestDistance("A", "C"), 0.001);
    }

    @Test void testFloydPath() {
        g.addArc("A", "B", 1); g.addArc("B", "C", 2); g.addArc("A", "C", 10); floyd.run();
        assertEquals(List.of("A", "B", "C"), floyd.shortestPath("A", "C"));
    }

    @Test void testFloydEmptyPath() {
        g.addArc("A", "B", 5); g.addNode("C"); floyd.run();
        assertTrue(floyd.shortestPath("A", "C").isEmpty());
    }

    @Test void testGraphCenter() {
        g.addArc("a", "b", 1); g.addArc("b", "c", 2); g.addArc("b", "d", 1);
        g.addArc("c", "d", 3); g.addArc("c", "e", 4); g.addArc("d", "e", 5);
        floyd.run();
        assertEquals("d", floyd.graphCenter());
    }
}