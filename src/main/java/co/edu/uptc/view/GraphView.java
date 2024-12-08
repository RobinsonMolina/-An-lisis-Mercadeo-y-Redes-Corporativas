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

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GraphView {

    private static Graph graph;
    Map<Node, Circle> nodeShapes = new HashMap<>();

    public static void setGraph(Graph graphData) {
        graph = graphData;
    }

    public Circle getNodeShape(Node node) {
        return nodeShapes.get(node);
    }

    public ScrollPane getGraphContainer() {
        Pane pane = new Pane();
        double width = 800;
        double height = 600;
        double margin = 50;

        int nodeCount = graph.getNodes().size();
        Random random = new Random();

        // Dibujar nodos
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

            Circle circle = new Circle(x[0], y[0], 20, Color.LIGHTBLUE);
            Text label = new Text(x[0] - 10, y[0] + 5, node.getName());

            nodeShapes.put(node, circle);
            pane.getChildren().addAll(circle, label);
        }

        // Dibujar aristas
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

    public ScrollPane getGraphCommunity(Map<Node, Color> nodeColors) {
        Pane pane = new Pane();
        double width = 800;
        double height = 600;
        double margin = 50;
        Random random = new Random();
        nodeShapes.clear();

        // Dibujar los nodos del grafo
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

            // Obtener el color asignado a la comunidad
            Color color = nodeColors.getOrDefault(node, Color.LIGHTBLUE);
            Circle circle = new Circle(x[0], y[0], 20, color);
            Text label = new Text(x[0] - 10, y[0] + 5, node.getName());

            nodeShapes.put(node, circle);
            pane.getChildren().addAll(circle, label);
        }

        // Dibujar las aristas del grafo
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

}
