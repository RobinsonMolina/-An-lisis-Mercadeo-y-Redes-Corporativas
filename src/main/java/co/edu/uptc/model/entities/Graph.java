package co.edu.uptc.model.entities;

import java.util.*;

public class Graph {
    private Map<Node, List<Edge>> adjacencyMap;

    public Graph() {
        this.adjacencyMap = new HashMap<>();
    }

    // Agregar un nodo al grafo
    public void addNode(Node node) {
        adjacencyMap.putIfAbsent(node, new ArrayList<>());
    }

    // Agregar una arista al grafo
    public void addEdge(Node source, Node target, double weight) {
        Edge edge = new Edge(source, target, weight);
        adjacencyMap.putIfAbsent(source, new ArrayList<>());
        adjacencyMap.putIfAbsent(target, new ArrayList<>());
        adjacencyMap.get(source).add(edge);
    }

    // Obtener nodos
    public Set<Node> getNodes() {
        return adjacencyMap.keySet();
    }

    // Obtener aristas salientes de un nodo
    public List<Edge> getEdgesFromNode(Node node) {
        return adjacencyMap.getOrDefault(node, Collections.emptyList());
    }

    // Obtener todas las aristas del grafo
    public List<Edge> getEdges() {
        List<Edge> edges = new ArrayList<>();
        for (List<Edge> edgeList : adjacencyMap.values()) {
            edges.addAll(edgeList);
        }
        return edges;
    }

    // Obtener vecinos de un nodo
    public List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        List<Edge> edges = adjacencyMap.getOrDefault(node, Collections.emptyList());
        for (Edge edge : edges) {
            neighbors.add(edge.getTarget());
        }
        return neighbors;
    }

    public void removeNode(Node node) {
        if (adjacencyMap.containsKey(node)) {
            // Remover todas las aristas hacia el nodo
            adjacencyMap.values().forEach(edges -> edges.removeIf(edge -> edge.getTarget().equals(node)));
            // Remover el nodo del mapa de adyacencia
            adjacencyMap.remove(node);
            System.out.println("Nodo eliminado exitosamente.");
        } else {
            System.out.println("Error: Nodo no encontrado en el grafo.");
        }
    }

    public void removeEdge(Node source, Node target) {
        if (adjacencyMap.containsKey(source)) {
            List<Edge> edges = adjacencyMap.get(source);
            edges.removeIf(edge -> edge.getTarget().equals(target));
            System.out.println("Arista eliminada exitosamente.");
        } else {
            System.out.println("No se encontraron aristas para el nodo fuente.");
        }
    }

    public void updateNode(Node node, String newName, String newType) {
        node.setName(newName);
        node.setType(newType);
    }

    public boolean updateEdge(Node source, Node target, double newWeight) {
        if (adjacencyMap.containsKey(source)) {
            List<Edge> edges = adjacencyMap.get(source);
            for (Edge edge : edges) {
                if (edge.getTarget().equals(target)) {
                    edge.setWeight(newWeight);
                    return true;
                }
            }
        }
        return false;
    }
    
    public Node getNodeById(String id) {
        for (Node node : adjacencyMap.keySet()) {
            if (node.getId().equals(id)) {
                return node;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Node node : adjacencyMap.keySet()) {
            builder.append(node).append(" -> ").append(adjacencyMap.get(node)).append("\n");
        }
        return builder.toString();
    }

    public int getShortestPathLength(Node source, Node target) {
        if (source.equals(target)) {
            return 0;
        }
    
        Queue<Node> queue = new LinkedList<>();
        Map<Node, Integer> distances = new HashMap<>();
        Set<Node> visited = new HashSet<>();
    
        // Inicializar distancias
        for (Node node : getNodes()) {
            distances.put(node, Integer.MAX_VALUE);
        }
    
        queue.add(source);
        distances.put(source, 0);
        visited.add(source);
    
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            int currentDistance = distances.get(current);
    
            for (Node neighbor : getNeighbors(current)) {
                if (!visited.contains(neighbor)) {
                    distances.put(neighbor, currentDistance + 1);
                    visited.add(neighbor);
                    queue.add(neighbor);
    
                    if (neighbor.equals(target)) {
                        return distances.get(target); // Camino encontrado
                    }
                }
            }
        }
    
        return Integer.MAX_VALUE; // No hay camino entre los nodos
    }
    public List<List<Node>> getAllShortestPaths(Node source, Node target) {
        List<List<Node>> paths = new ArrayList<>();
        if (source.equals(target)) {
            List<Node> selfPath = new ArrayList<>();
            selfPath.add(source);
            paths.add(selfPath);
            return paths;
        }
    
        Queue<List<Node>> queue = new LinkedList<>();
        List<Node> initialPath = new ArrayList<>();
        initialPath.add(source);
        queue.add(initialPath);
    
        int shortestDistance = Integer.MAX_VALUE;
    
        while (!queue.isEmpty()) {
            List<Node> currentPath = queue.poll();
            Node lastNode = currentPath.get(currentPath.size() - 1);
    
            if (currentPath.size() > shortestDistance) {
                continue; // Solo mantenemos los caminos más cortos
            }
    
            for (Node neighbor : getNeighbors(lastNode)) {
                if (!currentPath.contains(neighbor)) {
                    List<Node> newPath = new ArrayList<>(currentPath);
                    newPath.add(neighbor);
    
                    if (neighbor.equals(target)) {
                        paths.add(newPath);
                        shortestDistance = newPath.size();
                    } else {
                        queue.add(newPath);
                    }
                }
            }
        }
    
        return paths;
    }
    
}
