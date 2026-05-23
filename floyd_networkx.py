import math, sys
import networkx as nx

def load_graph(filename):
    G = nx.DiGraph()
    try:
        with open(filename) as f:
            for line in f:
                p = line.strip().split()
                if len(p) == 3:
                    G.add_edge(p[0], p[1], weight=float(p[2]))
        print("Archivo leido:", filename)
    except FileNotFoundError:
        print("Archivo no encontrado.")
    return G

def run_floyd(G):
    return dict(nx.floyd_warshall(G, weight="weight"))

def graph_center(G, dist):
    nodes, min_ecc, center = list(G.nodes()), math.inf, None
    for j in nodes:
        ecc = max((dist[i].get(j, math.inf) for i in nodes if i != j), default=0)
        if ecc < min_ecc:
            min_ecc, center = ecc, j
    return center

def shortest_path(G, src, dst):
    try:
        return nx.dijkstra_path_length(G, src, dst, weight="weight"), \
               nx.dijkstra_path(G, src, dst, weight="weight")
    except (nx.NetworkXNoPath, nx.NodeNotFound):
        return math.inf, []

def menu(G):
    dist = run_floyd(G)
    while True:
        print("\n1. Ruta mas corta\n2. Centro del grafo\n3. Modificar grafo\n4. Salir")
        op = input("Opcion: ").strip()

        if op == "1":
            src, dst = input("Origen:  ").strip(), input("Destino: ").strip()
            d, path = shortest_path(G, src, dst)
            if d == math.inf: print("No hay ruta.")
            else: print(f"Distancia: {d:.0f} KM\nRuta: {' -> '.join(path)}")

        elif op == "2":
            print("Centro:", graph_center(G, dist))

        elif op == "3":
            sub = input("a) Eliminar  b) Agregar: ").strip().lower()
            c1, c2 = input("Origen:  ").strip(), input("Destino: ").strip()
            if sub == "a":
                if G.has_edge(c1, c2):
                    G.remove_edge(c1, c2); dist = run_floyd(G)
                    print("Eliminada. Nuevo centro:", graph_center(G, dist))
                else: print("No existia esa conexion.")
            elif sub == "b":
                km = float(input("KM: ").strip())
                G.add_edge(c1, c2, weight=km); dist = run_floyd(G)
                print("Agregada. Nuevo centro:", graph_center(G, dist))

        elif op == "4":
            print("Hasta luego."); break

if __name__ == "__main__":
    G = load_graph(sys.argv[1] if len(sys.argv) > 1 else "guategrafo.txt")
    menu(G)