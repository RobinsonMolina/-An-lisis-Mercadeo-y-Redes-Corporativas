package co.edu.uptc.model.entities;

import java.util.*;

public class Graph {
    private Map<Node, List<Edge>> adjacencyMap;
    private Map<Integer, List<Node>> communities;

    public Graph() {
        this.adjacencyMap = new HashMap<>();
    }

    public void addNode(Node node) {
        adjacencyMap.putIfAbsent(node, new ArrayList<>());
    }

    public void addEdge(Node source, Node target, double weight) {
        Edge edge = new Edge(source, target, weight);
        adjacencyMap.putIfAbsent(source, new ArrayList<>());
        adjacencyMap.putIfAbsent(target, new ArrayList<>());
        adjacencyMap.get(source).add(edge);
    }

    public Set<Node> getNodes() {
        return adjacencyMap.keySet();
    }

    public List<Edge> getEdgesFromNode(Node node) {
        return adjacencyMap.getOrDefault(node, Collections.emptyList());
    }

    public List<Edge> getEdges() {
        List<Edge> edges = new ArrayList<>();
        for (List<Edge> edgeList : adjacencyMap.values()) {
            edges.addAll(edgeList);
        }
        return edges;
    }


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
    public void setCommunities(Map<Integer, List<Node>> communities) {
        this.communities = communities;
    }

    public Map<Integer, List<Node>> getCommunities() {
        return communities;
    }
}
