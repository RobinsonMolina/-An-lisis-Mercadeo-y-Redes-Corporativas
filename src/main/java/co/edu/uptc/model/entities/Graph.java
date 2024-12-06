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

    // Obtener vecinos de un nodo
    public List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        List<Edge> edges = adjacencyMap.getOrDefault(node, Collections.emptyList());
        for (Edge edge : edges) {
            neighbors.add(edge.getTarget());
        }
        return neighbors;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Node node : adjacencyMap.keySet()) {
            builder.append(node).append(" -> ").append(adjacencyMap.get(node)).append("\n");
        }
        return builder.toString();
    }
}

