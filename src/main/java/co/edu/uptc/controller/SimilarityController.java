package co.edu.uptc.controller;

import co.edu.uptc.model.entities.Edge;
import co.edu.uptc.model.entities.Graph;
import co.edu.uptc.model.entities.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimilarityController {

    public Map<Node, List<Node>> findSimilarEntities(Graph graph) {
        Map<Node, List<Node>> similarEntities = new HashMap<>();
        for (Node node : graph.getNodes()) {
            List<Node> neighbors = graph.getNeighbors(node);
            Map<Node, Integer> commonNeighborsCount = new HashMap<>();
            for (Node neighbor : neighbors) {
                for (Node sharedNeighbor : graph.getNeighbors(neighbor)) {
                    if (!sharedNeighbor.equals(node)) {
                        commonNeighborsCount.put(sharedNeighbor,
                                commonNeighborsCount.getOrDefault(sharedNeighbor, 0) + 1);
                    }
                }
            }
            List<Node> similarNodes = new ArrayList<>();
            for (Map.Entry<Node, Integer> entry : commonNeighborsCount.entrySet()) {
                if (entry.getValue() >= 2) {
                    similarNodes.add(entry.getKey());
                }
            }
            similarEntities.put(node, similarNodes);
        }

        return similarEntities;
    }

    public List<List<Node>> findFrequentProductBundles(Graph graph, double threshold) {
        List<List<Node>> bundles = new ArrayList<>();
        Map<Node, List<Node>> productPairs = new HashMap<>();

        for (Edge edge : graph.getEdges()) {
            Node source = edge.getSource();
            Node target = edge.getTarget();

            if (source.getType().equals("producto") && target.getType().equals("producto") && edge.getWeight() >= threshold) {
                productPairs.computeIfAbsent(source, k -> new ArrayList<>()).add(target);
            }
        }

        for (Map.Entry<Node, List<Node>> entry : productPairs.entrySet()) {
            List<Node> bundle = new ArrayList<>();
            bundle.add(entry.getKey());
            bundle.addAll(entry.getValue());
            bundles.add(bundle);
        }

        return bundles;
    }
}