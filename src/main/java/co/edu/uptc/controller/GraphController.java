package co.edu.uptc.controller;

import co.edu.uptc.model.entities.Graph;
import co.edu.uptc.model.entities.Node;
import co.edu.uptc.persistence.ManageFile;
import java.util.*;

public class GraphController {

    private static GraphController instance; 
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
    
    private void saveGraph() {
        String resourcePath = "src/main/resources/grafoCreado.csv"; 
        manageFile.saveGraphToCSV(graph, resourcePath);
    }

    
    public void loadGraphFromCSV(String filePath) {
        this.graph = manageFile.loadGraphFromCSV(filePath);
    }

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
        Map<Node, Double> centrality = new HashMap<>();
        for (Node node : getNodes()) {
            centrality.put(node, 0.0);
        }
    
        for (Node source : getNodes()) {
            for (Node target : getNodes()) {
                if (!source.equals(target)) {
                    List<List<Node>> paths = getAllShortestPaths(source, target);
                    if (paths != null && !paths.isEmpty()) {
                        int totalPaths = paths.size();
                        Map<Node, Integer> nodeCounts = new HashMap<>();
                        for (List<Node> path : paths) {
                            for (Node intermediate : path) {
                                if (!intermediate.equals(source) && !intermediate.equals(target)) {
                                    nodeCounts.put(intermediate, nodeCounts.getOrDefault(intermediate, 0) + 1);
                                }
                            }
                        }
                        for (Node intermediate : nodeCounts.keySet()) {
                            double score = centrality.get(intermediate);
                            score += (double) nodeCounts.get(intermediate) / totalPaths;
                            centrality.put(intermediate, score);
                        }
                    }
                }
            }
        }
    
        return centrality;
    }

    public Set<Node> getNodes() {
        return graph.getNodes(); // `graph` es la instancia de la clase Graph dentro de GraphController
    }
    
    public List<List<Node>> getAllShortestPaths(Node source, Node target) {
        return graph.getAllShortestPaths(source, target); // `graph` es la instancia de Graph en GraphController
    }
    
    
    
    public Map<Node, Integer> calculateDegreeCentrality() {
        Map<Node, Integer> degreeCentrality = new HashMap<>();
        for (Node node : graph.getNodes()) {
            // Conexiones salientes
            int outDegree = graph.getNeighbors(node).size();
            // Conexiones entrantes
            int inDegree = (int) graph.getEdges().stream()
                    .filter(edge -> edge.getTarget().equals(node))
                    .count();
            // Suma de conexiones entrantes y salientes
            degreeCentrality.put(node, outDegree + inDegree);
        }
        return degreeCentrality;
    }
    
    public Map<Node, Double> calculateClosenessCentrality() {
        Map<Node, Double> closenessCentrality = new HashMap<>();
    
        for (Node node : graph.getNodes()) {
            double totalDistance = 0.0;
            int reachableNodes = 0;
    
            for (Node otherNode : graph.getNodes()) {
                if (!node.equals(otherNode)) {
                    int distance = graph.getShortestPathLength(node, otherNode);
    
                    if (distance > 0) { // Ignoramos distancias negativas o nodos no alcanzables
                        totalDistance += distance;
                        reachableNodes++;
                    }
                }
            }
    
            // Calcular la centralidad si hay nodos alcanzables
            if (reachableNodes > 0 && totalDistance > 0) {
                closenessCentrality.put(node, (double) reachableNodes / totalDistance);
            } else {
                closenessCentrality.put(node, 0.0); // Nodo aislado
            }
        }
    
        return closenessCentrality;
    }
    
    
    
    
    
    
    
}
