package co.edu.uptc.controller;

import co.edu.uptc.model.entities.Graph;
import co.edu.uptc.model.entities.Node;

public class GraphController {
    private Graph graph;

    public GraphController() {
        this.graph = new Graph();
    }

    public void addNode(String id, String label, String type) {
        Node node = new Node(id, label, type);
        graph.addNode(node);
    }

    public void addEdge(String sourceId, String targetId, double weight) {
        Node source = findNodeById(sourceId);
        Node target = findNodeById(targetId);

        if (source != null && target != null) {
            graph.addEdge(source, target, weight);
        } else {
            System.out.println("Error: Nodo fuente o destino no encontrado.");
        }
    }

    private Node findNodeById(String id) {
        return graph.getNodes().stream().filter(n -> n.getId().equals(id)).findFirst().orElse(null);
    }

    public Graph getGraph() {
        return graph;
    }
}
