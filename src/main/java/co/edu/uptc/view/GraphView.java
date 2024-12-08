package co.edu.uptc.view;

import co.edu.uptc.model.entities.Graph;
import co.edu.uptc.model.entities.Node;
import co.edu.uptc.model.entities.Edge;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.control.ScrollPane;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GraphView {

    private static Graph graph;
    private static Map<Integer, List<Node>> communities;

    public static void setGraph(Graph graphData) {
        graph = graphData;
    }

    public static void setCommunities(Map<Integer, List<Node>> communityData) {
        communities = communityData;
    }

    public ScrollPane getGraphContainer() {
        if (graph == null || graph.getNodes().isEmpty()) {
            Pane emptyPane = new Pane();
            Label noDataLabel = new Label("No hay datos en el grafo.");
            noDataLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
            StackPane stackPane = new StackPane(noDataLabel);
            stackPane.setMinSize(800, 600);
            return new ScrollPane(stackPane);
        }

        Pane pane = new Pane();

        Map<Node, Circle> nodeShapes = new HashMap<>();
        double width = 800;
        double height = 600;
        double margin = 50;

        int nodeCount = graph.getNodes().size();
        Random random = new Random();
        Map<Integer, Color> communityColors = generateCommunityColors(communities);
        for (Node node : graph.getNodes()) {
            final double[] x = new double[1];
            final double[] y = new double[1];

            x[0] = random.nextInt((int) width);
            y[0] = random.nextInt((int) height);

            while (nodeShapes.values().stream()
                    .anyMatch(circle -> Math.hypot(circle.getCenterX() - x[0], circle.getCenterY() - y[0]) < 30)) {
                x[0] = random.nextInt((int) width);
                y[0] = random.nextInt((int) height);
            }

            Color color = getNodeCommunityColor(node, communityColors);

            Circle circle = new Circle(x[0], y[0], 20, color);
            Text label = new Text(x[0] - 10, y[0] + 5, node.getName());

            nodeShapes.put(node, circle);
            pane.getChildren().addAll(circle, label);
        }

        for (Edge edge : graph.getEdges()) {
            Circle sourceShape = nodeShapes.get(edge.getSource());
            Circle targetShape = nodeShapes.get(edge.getTarget());

            if (sourceShape != null && targetShape != null) {
                Line line = new Line(
                        sourceShape.getCenterX(), sourceShape.getCenterY(),
                        targetShape.getCenterX(), targetShape.getCenterY());
                line.setStrokeWidth(2);
                line.setStroke(Color.GRAY);

                Text weightLabel = new Text(
                        (sourceShape.getCenterX() + targetShape.getCenterX()) / 2,
                        (sourceShape.getCenterY() + targetShape.getCenterY()) / 2,
                        String.valueOf(edge.getWeight()));

                pane.getChildren().addAll(line, weightLabel);
            }
        }

        pane.setMinSize(width + 2 * margin, height + 2 * margin);

        ScrollPane scrollPane = new ScrollPane(pane);
        scrollPane.setPannable(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }

    private Color getNodeCommunityColor(Node node, Map<Integer, Color> communityColors) {
        if (communities == null) {
            return Color.LIGHTGRAY;
        }
        for (Map.Entry<Integer, List<Node>> entry : communities.entrySet()) {
            if (entry.getValue().contains(node)) {
                return communityColors.getOrDefault(entry.getKey(), Color.LIGHTGRAY);
            }
        }
        return Color.LIGHTGRAY;
    }



    private Map<Integer, Color> generateCommunityColors(Map<Integer, List<Node>> communities) {
        Map<Integer, Color> communityColors = new HashMap<>();

        if (communities == null || communities.isEmpty()) {
            return communityColors;
        }
        Random random = new Random();
        for (Integer communityId : communities.keySet()) {
            communityColors.put(communityId, Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble()));
        }
        return communityColors;
    }

    Map<Integer, String> communityColors = new HashMap<>();
    String neutralColor = "gray";

    public void assignCommunityColors(Map<Integer, List<Node>> communities) {
        int colorIndex = 0;
        String[] colors = {"#FF5733", "#33FF57", "#3357FF", "#FFFF33", "#FF33FF", "#33FFFF"};

        for (Map.Entry<Integer, List<Node>> entry : communities.entrySet()) {
            Integer communityId = entry.getKey();
            // Asignar un color único a esta comunidad
            communityColors.put(communityId, colors[colorIndex % colors.length]);
            colorIndex++;
        }

        for (Map.Entry<Integer, List<Node>> entry : communities.entrySet()) {
            String communityColor = communityColors.get(entry.getKey());
            for (Node node : entry.getValue()) {
                node.setColor(communityColor);
            }
        }
        for (Node node : graph.getNodes()) {
            if (!isNodeInAnyCommunity(node, communities)) {
                node.setColor(neutralColor);
            }
        }
    }

    private boolean isNodeInAnyCommunity(Node node, Map<Integer, List<Node>> communities) {
        for (List<Node> community : communities.values()) {
            if (community.contains(node)) {
                return true;
            }
        }
        return false;
    }

}

