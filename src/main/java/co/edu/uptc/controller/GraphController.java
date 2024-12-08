package co.edu.uptc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

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

    public void addNode(String id, String name, String type) {
        Node node = new Node(id, name, type);
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

    public void loadGraphFromCSV(String filePath) {
        this.graph = manageFile.loadGraphFromCSV(filePath);
    }

    public boolean removeNode(String nodeId) {
        Node node = findNodeById(nodeId);
        if (node != null) {
            graph.removeNode(node);
            return true;
        }
        return false;
    }

    public void removeEdge(String sourceId, String targetId) {
        Node source = findNodeById(sourceId);
        Node target = findNodeById(targetId);

        if (source != null && target != null) {
            graph.removeEdge(source, target);
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

    public List<Set<Node>> detectCommunities() {
        Map<Node, Integer> communityMap = new HashMap<>();
        int communityId = 0;

        for (Node node : graph.getNodes()) {
            if (!communityMap.containsKey(node)) {
                Set<Node> community = new HashSet<>();
                exploreCommunity(node, community, communityMap, communityId++);
            }
        }

        // Agrupar comunidades
        Map<Integer, Set<Node>> communities = new HashMap<>();
        for (Map.Entry<Node, Integer> entry : communityMap.entrySet()) {
            communities.computeIfAbsent(entry.getValue(), k -> new HashSet<>()).add(entry.getKey());
        }

        return new ArrayList<>(communities.values());
    }

    private void exploreCommunity(Node node, Set<Node> community, Map<Node, Integer> communityMap, int communityId) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (!communityMap.containsKey(current)) {
                community.add(current);
                communityMap.put(current, communityId);

                for (Node neighbor : graph.getNeighbors(current)) {
                    if (!communityMap.containsKey(neighbor)) {
                        queue.add(neighbor);
                    }
                }
            }
        }
    }
}
