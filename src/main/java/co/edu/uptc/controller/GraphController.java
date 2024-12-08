package co.edu.uptc.controller;

import co.edu.uptc.model.entities.Graph;
import co.edu.uptc.model.entities.Node;
import co.edu.uptc.persistence.ManageFile;

public class GraphController {

    private static GraphController instance; // Single instance of the class
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

    public void addNode(String id, String name, String type) {
        Node node = new Node(id, name, type);
        graph.addNode(node);
        saveGraph();
    }

    public void addEdge(String sourceId, String targetId, double weight) {
        if (!verifyIdNode(sourceId, targetId)) {
            System.out.println("Error: Nodo fuente o destino no válido.");
            return;
        }
        
        Node source = findNodeById(sourceId);
        Node target = findNodeById(targetId);

        if (source != null && target != null) {
            graph.addEdge(source, target, weight);
            saveGraph();
        } else {
            System.out.println("Error: Nodo fuente o destino no encontrado.");
        }
    }

    private boolean verifyIdNode(String sourceId, String targetId) {
        return isNodePresentById(sourceId) && isNodePresentById(targetId);
    }

    private boolean isNodePresentById(String id) {
        return graph.getNodes().stream().anyMatch(n -> n.getId().equals(id));
    }

    public boolean isNodeIdAlreadyRegistered(String id) {
        return isNodePresentById(id);
    }
    
    private boolean isNodePresentByName(String name) {
        return graph.getNodes().stream().anyMatch(n -> n.getName().equals(name));
    }

    public boolean isNodeNameAlreadyRegistered(String name) {
        return isNodePresentByName(name);
    }


    private Node findNodeById(String id) {
        return graph.getNodes().stream().filter(n -> n.getId().equals(id)).findFirst().orElse(null);
    }
    
   

    public void loadGraphFromCSV(String filePath) {
        this.graph = manageFile.loadGraphFromCSV(filePath);
    }

    public boolean removeNode(String nodeId) {
        Node node = findNodeById(nodeId);
        if (node != null) {
            graph.removeNode(node);
            saveGraph();
            return true;
        }
        return false;
    }

    public void removeEdge(String sourceId, String targetId) {
        Node source = findNodeById(sourceId);
        Node target = findNodeById(targetId);

        if (source != null && target != null) {
            graph.removeEdge(source, target);
            saveGraph();
        } else {
            System.out.println("Error: Nodo fuente o destino no encontrado.");
        }
    }

    public boolean updateNode(String nodeId, String newName, String newType) {
        Node node = findNodeById(nodeId);
        if (node != null) {
            graph.updateNode(node, newName, newType);
            System.out.println("Nodo actualizado exitosamente.");
            return true;
        }
        System.out.println("Error: Nodo no encontrado.");
        return false;
    }

    public boolean updateEdge(String sourceId, String targetId, double newWeight) {
        Node source = findNodeById(sourceId);
        Node target = findNodeById(targetId);

        if (source != null && target != null) {
            return graph.updateEdge(source, target, newWeight);
        }

        return false;
    }

    public Graph getGraph() {
        return graph;
    }
}
