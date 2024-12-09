package co.edu.uptc.controller;

import co.edu.uptc.model.entities.Graph;
import co.edu.uptc.model.entities.Node;
import co.edu.uptc.persistence.ManageFile;
import java.util.*;

public class GraphController {

    private static GraphController instance; // Singleton
    private Graph graph;
    private ManageFile manageFile;

    public GraphController() {
        this.graph = new Graph();
        this.manageFile = new ManageFile();
    }

    public static GraphController getInstance() {
        if (instance == null) {
            instance = new GraphController();
        }
        return instance;
    }

    // Método para agregar un nodo
    public void addNode(String id, String name, String type) {
        Node node = new Node(id, name, type);
        graph.addNode(node);
    }

    // Método para agregar una arista
    public void addEdge(String sourceId, String targetId, double weight) {
        Node source = findNodeById(sourceId);
        Node target = findNodeById(targetId);

        if (source != null && target != null) {
            graph.addEdge(source, target, weight);
        } else {
            System.out.println("Error: Nodo fuente o destino no encontrado.");
        }
    }

    // Método para encontrar un nodo por su ID
    private Node findNodeById(String id) {
        return graph.getNodes().stream().filter(n -> n.getId().equals(id)).findFirst().orElse(null);
    }

    // --- Métodos agregados sin eliminar el trabajo de tus compañeros ---

    // Cargar un grafo desde un archivo CSV
    public void loadGraphFromCSV(String filePath) {
        this.graph = manageFile.loadGraphFromCSV(filePath);
    }

    // Verificar si un ID de nodo ya está registrado
    public boolean isNodeIdAlreadyRegistered(String id) {
        return graph.getNodes().stream().anyMatch(node -> node.getId().equals(id));
    }

    // Verificar si un nombre de nodo ya está registrado
    public boolean isNodeNameAlreadyRegistered(String name) {
        return graph.getNodes().stream().anyMatch(node -> node.getName().equals(name));
    }

    // Eliminar un nodo
    public void removeNode(String id) {
        Node node = findNodeById(id);
        if (node != null) {
            graph.removeNode(node);
        } else {
            System.out.println("Error: Nodo no encontrado.");
        }
    }

    // Eliminar una arista
    public void removeEdge(String sourceId, String targetId) {
        Node source = findNodeById(sourceId);
        Node target = findNodeById(targetId);

        if (source != null && target != null) {
            graph.removeEdge(source, target);
        } else {
            System.out.println("Error: Nodo fuente o destino no encontrado.");
        }
    }

    // Actualizar un nodo
    public boolean updateNode(String id, String newName, String newType) {
        Node node = findNodeById(id);
        if (node != null) {
            graph.updateNode(node, newName, newType);
        } else {
            System.out.println("Error: Nodo no encontrado.");
        }
                return false;
    }

    // Actualizar una arista
    public boolean updateEdge(String sourceId, String targetId, double newWeight) {
        Node source = findNodeById(sourceId);
        Node target = findNodeById(targetId);

        if (source != null && target != null) {
            return graph.updateEdge(source, target, newWeight);
        }
        return false;
    }

    // Obtener el grafo actual
    public Graph getGraph() {
        return graph;
    }

	public Map<Node, Double> calculateBetweennessCentrality() {
        Map<Node, Double> betweennessCentrality = new HashMap<>();
        graph.getNodes().forEach(node -> betweennessCentrality.put(node, 0.0));
    
        for (Node source : graph.getNodes()) {
            for (Node target : graph.getNodes()) {
                if (!source.equals(target)) {
                    List<List<Node>> shortestPaths = graph.getAllShortestPaths(source, target);
    
                    for (Node node : graph.getNodes()) {
                        if (!node.equals(source) && !node.equals(target)) {
                            long countPathsThroughNode = shortestPaths.stream()
                                    .filter(path -> path.contains(node))
                                    .count();
                            long totalPaths = shortestPaths.size();
    
                            double currentBetweenness = betweennessCentrality.get(node);
                            betweennessCentrality.put(node, currentBetweenness + ((double) countPathsThroughNode / totalPaths));
                        }
                    }
                }
            }
        }
    
        return betweennessCentrality;
    }
    
    
    public Map<Node, Integer> calculateDegreeCentrality() {
        Map<Node, Integer> degreeCentrality = new HashMap<>();
    
        graph.getNodes().forEach(node -> {
            int degree = graph.getNeighbors(node).size();
            degreeCentrality.put(node, degree);
        });
    
        return degreeCentrality;
    }
    
    public Map<Node, Double> calculateClosenessCentrality() {
        Map<Node, Double> closenessCentrality = new HashMap<>();
    
        for (Node node : graph.getNodes()) {
            double totalDistance = 0.0;
    
            for (Node otherNode : graph.getNodes()) {
                if (!node.equals(otherNode)) {
                    int distance = graph.getShortestPathLength(node, otherNode);
                    totalDistance += distance;
                }
            }
    
            // La centralidad es inversa a la distancia total
            if (totalDistance > 0) {
                closenessCentrality.put(node, 1.0 / totalDistance);
            } else {
                closenessCentrality.put(node, 0.0); // Nodo aislado
            }
        }
    
        return closenessCentrality;
    }
    
    
    
    
}
