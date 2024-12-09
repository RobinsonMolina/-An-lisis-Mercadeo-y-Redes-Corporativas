package co.edu.uptc.view;

import co.edu.uptc.model.entities.Graph;
import co.edu.uptc.model.entities.Node;
import co.edu.uptc.model.entities.Edge;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.control.ScrollPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class GraphView {

    private static Graph graph;

    Map<Node, Circle> nodeShapes = new HashMap<>();
    private Pane pane;

    public GraphView() {
        this.pane = new Pane();
    }

    public GraphView(Graph graph) {
        this.graph = graph;
        this.pane = new Pane();

    }

    public static void setGraph(Graph graphData) {
        graph = graphData;
    }

    private void positionNodesCircularly(double radius, double centerX, double centerY, Map<Node, Color> nodeColors) {
        List<Node> nodes = new ArrayList<>(graph.getNodes());
        int nodeCount = nodes.size();
        for (int i = 0; i < nodeCount; i++) {
            double angle = 2 * Math.PI * i / nodeCount;
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);

            Node node = nodes.get(i);
            Color color = nodeColors != null ? nodeColors.getOrDefault(node, Color.LIGHTBLUE) : Color.LIGHTBLUE;
            Circle circle = new Circle(x, y, 20, color);
            Text label = new Text(x - 10, y + 5, node.getName());

            nodeShapes.put(node, circle);
            pane.getChildren().addAll(circle, label);
        }
    }

    private void drawEdges(Map<Node, Integer> communityMap, Map<Integer, Circle> communityShapes) {
        for (Edge edge : graph.getEdges()) {
            Node source = edge.getSource();
            Node target = edge.getTarget();

            Circle sourceShape = nodeShapes.get(source);
            Circle targetShape = nodeShapes.get(target);

            if (communityMap != null && communityShapes != null) {
                Integer sourceCommunity = communityMap.get(source);
                Integer targetCommunity = communityMap.get(target);

                if (sourceCommunity != null && targetCommunity != null && !sourceCommunity.equals(targetCommunity)) {
                    sourceShape = communityShapes.get(sourceCommunity);
                    targetShape = communityShapes.get(targetCommunity);
                }
            }

            if (sourceShape != null && targetShape != null) {
                Line line = new Line(
                        sourceShape.getCenterX(), sourceShape.getCenterY(),
                        targetShape.getCenterX(), targetShape.getCenterY());
                line.setStrokeWidth(2);
                line.setStroke(Color.GRAY);
                pane.getChildren().add(line);
            }
        }
    }

    public ScrollPane getGraphContainer() {
        pane.getChildren().clear();
        double centerX = 400;
        double centerY = 300;
        double radius = Math.min(centerX, centerY) - 50;

        positionNodesCircularly(radius, centerX, centerY, null);
        drawEdges(null, null);

        return createScrollPane();
    }

    public ScrollPane getGraphCommunity(Map<Node, Color> nodeColors, List<Set<Node>> communities) {
        pane.getChildren().clear();

        Map<Integer, Circle> communityShapes = new HashMap<>();
        Map<Node, Integer> communityMap = new HashMap<>();
        Random random = new Random();

        for (int i = 0; i < communities.size(); i++) {
            double x = random.nextDouble() * 800;
            double y = random.nextDouble() * 600;

            Circle communityCircle = new Circle(x, y, 40, Color.LIGHTBLUE);
            Text label = new Text(x - 10, y + 5, "C" + i);

            communityShapes.put(i, communityCircle);
            for (Node node : communities.get(i)) {
                communityMap.put(node, i);
            }

            pane.getChildren().addAll(communityCircle, label);
        }

        drawEdges(communityMap, communityShapes);

        return createScrollPane();
    }

    public ScrollPane getGraphCommunity(Map<Node, Color> nodeColors) {
        pane.getChildren().clear();
        double centerX = 400;
        double centerY = 300;
        double radius = Math.min(centerX, centerY) - 50;

        positionNodesCircularly(radius, centerX, centerY, nodeColors);
        drawEdges(null, null);

        return createScrollPane();
    }

    private ScrollPane createScrollPane() {
        double paneWidth = 800;
        double paneHeight = 600;
        
        pane.setPrefSize(paneWidth, paneHeight);

        ScrollPane scrollPane = new ScrollPane(pane);
        scrollPane.setPannable(true);
        scrollPane.setFitToWidth(false);
        scrollPane.setFitToHeight(false);
        return scrollPane;
    }
}
