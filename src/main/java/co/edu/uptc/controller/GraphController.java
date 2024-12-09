package co.edu.uptc.controller;

import co.edu.uptc.model.entities.Edge;
import co.edu.uptc.model.entities.Graph;
import co.edu.uptc.model.entities.Node;
import co.edu.uptc.persistence.ManageFile;
import java.util.*;

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

    public void loadGraphFromCSV(String filePath) {
        this.graph = manageFile.loadGraphFromCSV(filePath);
    }

    public void addNode(String id, String name, String type) {
        Node node = new Node(id, name, type);
        graph.addNode(node);
        saveGraph();
    }

    // Método para agregar una arista
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


    private Node findNodeById(String id) {
        return graph.getNodes().stream().filter(n -> n.getId().equals(id)).findFirst().orElse(null);
    }
    
    public boolean isNodePresentById(String id) {
        return graph.getNodes().stream().anyMatch(n -> n.getId().equals(id));
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
    public boolean removeNode(String id) {
        Node node = findNodeById(id);
        if (node != null) {
            graph.removeNode(node);
            saveGraph();
            return true;
        } else {
            System.out.println("Error: Nodo no encontrado.");
        }
        return false;
    }

    // Eliminar una arista
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

    public String generateCommunityReport() {
        StringBuilder report = new StringBuilder();

        List<Set<Node>> communities = detectCommunities();

        double maxCommunitySales = 0;
        double minCommunitySales = Double.MAX_VALUE;
        double maxSales = 0;
        double minSales = Double.MAX_VALUE;

        String mostSalesCommunity = "";
        String leastSalesCommunity = "";
        String mostSalesCompany = "";
        String leastSalesCompany = "";


        for (Set<Node> community : communities) {
            double totalCommunitySales = 0; 
            String communityName = "Comunidad " + communities.indexOf(community);

            for (Node node : community) {
                double companySales = getCompanySales(node); 
                totalCommunitySales += companySales;
                String companyName = node.getName();

                // Verificar la empresa con más ventas
                if (companySales > maxSales) {
                    mostSalesCompany = companyName;
                    maxSales = companySales;
                }

                // Verificar la empresa con menos ventas
                if (companySales < minSales) {
                    leastSalesCompany = companyName;
                    minSales = companySales;
                }
            }

            // Verificar la comunidad con más ventas
            if (totalCommunitySales > maxCommunitySales) {
                mostSalesCommunity = communityName;
                maxCommunitySales = totalCommunitySales;
            }

            // Verificar la comunidad con menos ventas
            if (totalCommunitySales < minCommunitySales) {
                leastSalesCommunity = communityName;
                minCommunitySales = totalCommunitySales;
            }
        }

        report.append("\n                                                              Informe\n\n");

        // Comunidades
        report.append("\nComunidades\n\n");
        report.append("La comunidad con más ventas es ").append(mostSalesCommunity)
                .append(" con ventas totales de ").append(maxCommunitySales).append(".\n");
        report.append("La comunidad con menos ventas es ").append(leastSalesCommunity)
                .append(" con ventas totales de ").append(minCommunitySales).append(".\n\n");

        // Empresas
        report.append("\nEmpresas\n\n");
        report.append("La empresa que más vendió es ").append(mostSalesCompany)
                .append(" con ventas de ").append(maxSales).append(".\n");
        report.append("La empresa que menos vendió es ").append(leastSalesCompany)
                .append(" con ventas de ").append(minSales).append(".\n");

        // Análisis
        report.append("\nAnálisis:\n");
        report.append(getRandomAnalysis(mostSalesCommunity, maxCommunitySales, leastSalesCommunity, minCommunitySales,
                mostSalesCompany, maxSales, leastSalesCompany, minSales));

        // Recomendación aleatoria
        report.append("\nRecomendaciones:\n");
        report.append(getRandomRecommendation());

        return report.toString();
    }

    private String getRandomAnalysis(String mostSalesCommunity, double maxCommunitySales, String leastSalesCommunity,
            double minCommunitySales, String mostSalesCompany, double maxSales, String leastSalesCompany,
            double minSales) {
        String[] analysis = {
                "Análisis: La comunidad con más ventas, " + mostSalesCommunity + " (Ventas: " + maxCommunitySales
                        + "), tiene un rendimiento destacado debido a su sólida estrategia de marketing y el alto nivel de cooperación entre sus empresas. Por otro lado, "
                        + leastSalesCommunity + " (Ventas: " + minCommunitySales
                        + ") podría estar experimentando bajas ventas debido a la falta de innovación o el desinterés del mercado. En cuanto a las empresas, "
                        + mostSalesCompany + " ha destacado con ventas de " + maxSales
                        + " gracias a su enfoque centrado en las necesidades del cliente, mientras que "
                        + leastSalesCompany + " (Ventas: " + minSales
                        + ") podría necesitar revisar sus productos o canales de distribución.",
                "Análisis: La comunidad " + mostSalesCommunity + " (Ventas: " + maxCommunitySales
                        + ") ha mostrado un crecimiento sostenido debido a la adopción de nuevas tecnologías o alianzas estratégicas entre sus empresas. En contraste, "
                        + leastSalesCommunity + " (Ventas: " + minCommunitySales
                        + ") parece estar estancada, lo que podría ser el resultado de una competencia agresiva en el mercado o una falta de actualización en sus estrategias. "
                        + mostSalesCompany + " ha logrado posicionarse en el mercado con ventas de " + maxSales
                        + " debido a su diferenciación, mientras que " + leastSalesCompany + " (Ventas: " + minSales
                        + ") necesita reevaluar su propuesta de valor.",
                "Análisis: A pesar de que " + mostSalesCommunity + " (Ventas: " + maxCommunitySales
                        + ") ha logrado resultados excepcionales, " + leastSalesCommunity + " (Ventas: "
                        + minCommunitySales
                        + ") está mostrando signos de debilidad. Esto podría deberse a factores externos como cambios en el mercado o una falta de recursos adecuados. "
                        + mostSalesCompany + " (Ventas: " + maxSales
                        + ") se ha beneficiado de una expansión eficiente, mientras que " + leastSalesCompany
                        + " (Ventas: " + minSales
                        + ") puede estar enfrentando problemas internos que afectan su capacidad para generar ingresos.",
                "Análisis: " + mostSalesCommunity + " (Ventas: " + maxCommunitySales
                        + ") ha logrado grandes ventas debido a su capacidad para adaptarse rápidamente a las demandas del mercado. Por otro lado, "
                        + leastSalesCommunity + " (Ventas: " + minCommunitySales
                        + ") podría estar en declive por una posible saturación del mercado o un enfoque deficiente en la fidelización de clientes. "
                        + mostSalesCompany + " (Ventas: " + maxSales
                        + ") es líder en su sector gracias a su enfoque constante en la calidad, mientras que "
                        + leastSalesCompany + " (Ventas: " + minSales
                        + ") necesita implementar cambios drásticos para evitar una caída prolongada.",
                "Análisis: " + mostSalesCommunity + " (Ventas: " + maxCommunitySales
                        + ") ha sido exitosa debido a su enfoque integral, mientras que " + leastSalesCommunity
                        + " (Ventas: " + minCommunitySales
                        + ") podría beneficiarse de una mayor integración entre sus miembros. En cuanto a las empresas, "
                        + mostSalesCompany + " (Ventas: " + maxSales
                        + ") ha logrado maximizar su rendimiento, mientras que " + leastSalesCompany + " (Ventas: "
                        + minSales
                        + ") enfrenta dificultades que podrían ser resueltas con un cambio en sus tácticas de ventas o en su estrategia de producto."
        };
        Random rand = new Random();
        int randomIndex = rand.nextInt(analysis.length);

        return analysis[randomIndex] + "\n\n";
    }

    private String getRandomRecommendation() {
        String[] recommendations = {
                "Recomendación: Enfoque en la comunidad con menos ventas. Investigar las razones detrás de sus bajas ventas y mejorar la estrategia de marketing en esa comunidad.",
                "Recomendación: Las comunidades con ventas equilibradas pueden beneficiarse de un análisis más profundo sobre el comportamiento de los clientes. Se recomienda optimizar las operaciones de ventas de manera uniforme.",
                "Recomendación: Fomentar la colaboración entre las empresas de la comunidad que más vendió. Crear estrategias de alianzas para maximizar el impacto en el mercado.",
                "Recomendación: La empresa con menos ventas debería ser objetivo de un análisis más detallado sobre sus productos y mercados, y se le debería dar soporte adicional en términos de recursos de ventas.",
                "Recomendación: Se sugiere realizar promociones para las comunidades con menos ventas, con el fin de aumentar la visibilidad y el interés en los productos que ofrecen."
        };

        Random rand = new Random();
        int randomIndex = rand.nextInt(recommendations.length);

        return recommendations[randomIndex];
    }

    private double getCompanySales(Node node) {
        double totalSales = 0;

        for (Edge edge : graph.getEdgesFromNode(node)) {
            totalSales += edge.getWeight();
        }
        return totalSales;
    }

}
