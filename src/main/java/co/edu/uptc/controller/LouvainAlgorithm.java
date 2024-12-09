package co.edu.uptc.controller;

import java.util.*;

public class LouvainAlgorithm {

    private final Map<Integer, Set<Integer>> graph;
    private final Map<Integer, Integer> communities;
    private final Map<Integer, Double> weights;

    public LouvainAlgorithm(Map<Integer, Set<Integer>> graph, Map<Integer, Double> weights) {
        this.graph = graph;
        this.communities = new HashMap<>();
        this.weights = weights;

        // Inicializar comunidades
        for (Integer node : graph.keySet()) {
            communities.put(node, node);
        }
    }

    public Map<Integer, Integer> detectCommunities() {
        boolean improvement = true;
        while (improvement) {
            improvement = false;
            for (Integer node : graph.keySet()) {
                Map<Integer, Double> communityWeights = calculateCommunityWeights(node);
                Integer bestCommunity = findBestCommunity(communityWeights);
                if (!bestCommunity.equals(communities.get(node))) {
                    communities.put(node, bestCommunity);
                    improvement = true;
                }
            }
        }
        return communities;
    }

    private Map<Integer, Double> calculateCommunityWeights(Integer node) {
        Map<Integer, Double> communityWeights = new HashMap<>();
        for (Integer neighbor : graph.get(node)) {
            Integer community = communities.get(neighbor);
            communityWeights.put(community,
                    communityWeights.getOrDefault(community, 0.0) + weights.getOrDefault(neighbor, 1.0));
        }
        return communityWeights;
    }

    private Integer findBestCommunity(Map<Integer, Double> communityWeights) {
        return communityWeights.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow();
    }
}
