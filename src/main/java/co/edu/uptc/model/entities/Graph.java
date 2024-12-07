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
    
    public void removeEdge(Node source, Node target) {
        if (adjacencyMap.containsKey(source)) {
            List<Edge> edges = adjacencyMap.get(source);
            edges.removeIf(edge -> edge.getTarget().equals(target));
            System.out.println("Arista eliminada exitosamente.");
        } else {
            System.out.println("No se encontraron aristas para el nodo fuente.");
        }
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

    
    
    

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Node node : adjacencyMap.keySet()) {
            builder.append(node).append(" -> ").append(adjacencyMap.get(node)).append("\n");
        }
        return builder.toString();
    }
}
