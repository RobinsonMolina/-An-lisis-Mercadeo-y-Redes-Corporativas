package co.edu.uptc.controller;

import co.edu.uptc.model.entities.Graph;
import co.edu.uptc.model.entities.Node;

import java.util.*;

public class CommunityDetection {

    public Map<Integer, List<Node>> detectCommunitiesBySimilarity(Graph graph) {
        Map<Integer, List<Node>> communities = new HashMap<>();
        Set<Node> visitedNodes = new HashSet<>();
        int communityId = 1;

        for (Node node : graph.getNodes()) {
            if (!visitedNodes.contains(node)) {
                // Crear una nueva comunidad para el nodo actual
                List<Node> community = new ArrayList<>();
                findSimilarNodes(node, graph, visitedNodes, community);
                communities.put(communityId++, community);
            }
        }
        return communities;
    }

    private void findSimilarNodes(Node node, Graph graph, Set<Node> visitedNodes, List<Node> community) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        visitedNodes.add(node);

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            community.add(currentNode);

            for (Node neighbor : graph.getNeighbors(currentNode)) {
                if (!visitedNodes.contains(neighbor) && areNodesSimilar(currentNode, neighbor, graph)) {
                    visitedNodes.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
    }

    private boolean areNodesSimilar(Node node1, Node node2, Graph graph) {
        // Criterio de similitud: compartir al menos un vecino
        Set<Node> neighbors1 = new HashSet<>(graph.getNeighbors(node1));
        Set<Node> neighbors2 = new HashSet<>(graph.getNeighbors(node2));

        neighbors1.retainAll(neighbors2); // Intersección de vecinos
        return !neighbors1.isEmpty();
    }
}
