import networkx as nx

# lee el archivo del grafo y construye el DiGraph de NetworkX
def leer_grafo(archivo):
    G = nx.DiGraph()
    try:
        with open(archivo, 'r') as f:
            for linea in f:
                linea = linea.strip()
                if not linea:
                    continue
                partes = linea.split()
                if len(partes) == 3:
                    ciudad1 = partes[0]
                    ciudad2 = partes[1]
                    km = float(partes[2])
                    G.add_edge(ciudad1, ciudad2, weight=km)
    except FileNotFoundError:
        print(f"Error: no se encontro el archivo '{archivo}'")
    return G


# calcula la excentricidad de cada nodo y retorna el centro
# excentricidad(v) = max de las distancias minimas hacia v desde cualquier otro nodo
def encontrar_centro(G, distancias):
    nodos = list(G.nodes())
    min_exc = float('inf')
    centro = None

    for v in nodos:
        excentricidad = 0
        alcanzable = True

        for u in nodos:
            if u == v:
                continue
            d = distancias.get(u, {}).get(v, float('inf'))
            if d == float('inf'):
                alcanzable = False
                break
            if d > excentricidad:
                excentricidad = d

        if alcanzable and excentricidad < min_exc:
            min_exc = excentricidad
            centro = v

    return centro


def mostrar_menu():
    print("\n========== MENU ==========")
    print("1. Consultar ruta mas corta entre dos ciudades")
    print("2. Mostrar el centro del grafo")
    print("3. Modificar el grafo")
    print("4. Salir")


def main():
    print("Cargando grafo desde guategrafo.txt...")
    G = leer_grafo("guategrafo.txt")

    if G.number_of_nodes() == 0:
        print("El grafo esta vacio, revise el archivo.")
        return

    print(f"Grafo cargado: {G.number_of_nodes()} ciudades, {G.number_of_edges()} conexiones.\n")

    # calcular Floyd-Warshall con NetworkX
    distancias = dict(nx.floyd_warshall(G, weight='weight'))
    pred, _ = nx.floyd_warshall_predecessor_and_distance(G, weight='weight')

    opcion = 0
    while opcion != 4:
        mostrar_menu()
        try:
            opcion = int(input("Opcion: ").strip())
        except ValueError:
            print("Ingrese un numero valido.")
            continue

        if opcion == 1:
            origen = input("Ciudad de origen: ").strip()
            destino = input("Ciudad de destino: ").strip()

            if origen not in G:
                print(f"La ciudad '{origen}' no existe en el grafo.")
                continue
            if destino not in G:
                print(f"La ciudad '{destino}' no existe en el grafo.")
                continue

            d = distancias.get(origen, {}).get(destino, float('inf'))
            if d == float('inf'):
                print(f"No existe ruta de {origen} a {destino}.")
            else:
                ruta = nx.reconstruct_path(origen, destino, pred)
                print(f"Distancia minima: {int(d)} KM")
                print("Ruta: " + " -> ".join(ruta))

        elif opcion == 2:
            centro = encontrar_centro(G, distancias)
            if centro:
                print(f"\nEl centro del grafo es: {centro}")
            else:
                print("No se puede determinar el centro (grafo desconectado).")

        elif opcion == 3:
            print("\na) Interrumpir trafico entre dos ciudades (eliminar arco)")
            print("b) Establecer conexion entre dos ciudades (agregar arco)")
            opt = input("Opcion: ").strip().lower()

            if opt == 'a':
                origen = input("Ciudad origen: ").strip()
                destino = input("Ciudad destino: ").strip()
                if G.has_edge(origen, destino):
                    G.remove_edge(origen, destino)
                    print(f"Conexion de {origen} a {destino} eliminada.")
                else:
                    print("Ese arco no existe en el grafo.")
                    continue

            elif opt == 'b':
                origen = input("Ciudad origen: ").strip()
                destino = input("Ciudad destino: ").strip()
                try:
                    km = float(input("Distancia en KM: ").strip())
                    G.add_edge(origen, destino, weight=km)
                    print(f"Conexion de {origen} a {destino} ({int(km)} KM) agregada.")
                except ValueError:
                    print("Distancia invalida.")
                    continue
            else:
                print("Opcion no valida.")
                continue

            # recalcular Floyd despues de modificar el grafo
            distancias = dict(nx.floyd_warshall(G, weight='weight'))
            pred, _ = nx.floyd_warshall_predecessor_and_distance(G, weight='weight')

            nuevo_centro = encontrar_centro(G, distancias)
            print("Rutas recalculadas.")
            print(f"Nuevo centro del grafo: {nuevo_centro}")

        elif opcion == 4:
            print("Hasta luego!")
        else:
            print("Opcion no valida, intente de nuevo.")


if __name__ == "__main__":
    main()
