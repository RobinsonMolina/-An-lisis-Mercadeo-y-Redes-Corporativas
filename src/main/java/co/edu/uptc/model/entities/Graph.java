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

    public void removeNode(Node node) {
        if (adjacencyMap.containsKey(node)) {
            // Remover todas las aristas hacia el nodo
            adjacencyMap.values().forEach(edges -> edges.removeIf(edge -> edge.getTarget().equals(node)));
            // Remover el nodo del mapa de adyacencia
            adjacencyMap.remove(node);
            System.out.println("Nodo eliminado exitosamente.");
        } else {
            System.out.println("Error: Nodo no encontrado en el grafo.");
        }
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

    public void updateNode(Node node, String newName, String newType) {
        node.setName(newName);
        node.setType(newType);
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
    
    public Node getNodeById(String id) {
        for (Node node : adjacencyMap.keySet()) {
            if (node.getId().equals(id)) {
                return node;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Node node : adjacencyMap.keySet()) {
            builder.append(node).append(" -> ").append(adjacencyMap.get(node)).append("\n");
        }
        return builder.toString();
    }

    public int getShortestPathLength(Node source, Node target) {
        if (source.equals(target)) {
            return 0; 
        }
    
        Queue<Node> queue = new LinkedList<>();
        Map<Node, Integer> distances = new HashMap<>();
    
      
        for (Node node : getNodes()) {
            distances.put(node, -1);
        }
    
        queue.add(source);
        distances.put(source, 0);
    
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            int currentDistance = distances.get(current);
    
           
            for (Node neighbor : getNeighbors(current)) {
                if (distances.get(neighbor) == -1) { 
                    distances.put(neighbor, currentDistance + 1);
                    queue.add(neighbor);
    
                    
                    if (neighbor.equals(target)) {
                        return distances.get(neighbor);
                    }
                }
            }
        }
    
     
        return -1;
    }
    
    
    public List<List<Node>> getAllShortestPaths(Node source, Node target) {
        List<List<Node>> allPaths = new ArrayList<>();
        Queue<List<Node>> queue = new LinkedList<>();
        Map<Node, Integer> shortestPathLengths = new HashMap<>();
        
        // Inicializa la cola con el nodo de origen
        List<Node> startPath = new ArrayList<>();
        startPath.add(source);
        queue.add(startPath);
        shortestPathLengths.put(source, 0);
    
        while (!queue.isEmpty()) {
            List<Node> path = queue.poll();
            Node lastNode = path.get(path.size() - 1);
    
            // Si llegamos al nodo objetivo, agrega la ruta
            if (lastNode.equals(target)) {
                allPaths.add(new ArrayList<>(path));
                continue; // Sigue explorando otros caminos de igual longitud
            }
    
            // Explora los vecinos del nodo actual
            for (Node neighbor : getNeighbors(lastNode)) {
                int currentPathLength = path.size();
    
                // Asegura que solo se exploren caminos más cortos o igual de largos
                if (!shortestPathLengths.containsKey(neighbor) || 
                    currentPathLength < shortestPathLengths.get(neighbor)) {
                    
                    shortestPathLengths.put(neighbor, currentPathLength);
                    List<Node> newPath = new ArrayList<>(path);
                    newPath.add(neighbor);
                    queue.add(newPath);
                }
            }
        }
    
        return allPaths;
    }
    
    
    
}
