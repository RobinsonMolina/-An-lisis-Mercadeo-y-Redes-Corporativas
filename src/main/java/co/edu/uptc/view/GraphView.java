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
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class GraphView {

    private static Graph graph;
    Map<Node, Circle> nodeShapes = new HashMap<>();
    public GraphView(Graph graph) {
        this.graph = graph;
    }

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

            //Evita la posición de los nodos en la misma posición
            while (nodeShapes.values().stream()
                    .anyMatch(circle -> Math.hypot(circle.getCenterX() - x[0], circle.getCenterY() - y[0]) < 60)) {
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

    public ScrollPane getGraphCommunity(Map<Node, Color> nodeColors, List<Set<Node>> communities) {
        Pane pane = new Pane();
        double width = 800;
        double height = 600;
        double margin = 50;
        Random random = new Random();
        nodeShapes.clear();

        // Crear supernodos (un solo nodo por comunidad)
        int communityId = 0;
        Map<Integer, Circle> communityShapes = new HashMap<>();
        Map<Node, Integer> communityMap = new HashMap<>();
        Map<Integer, Color> communityColors = new HashMap<>();

        // Crear los supernodos y asignar los nodos a sus comunidades
        for (Set<Node> community : communities) {
            final double[] x = new double[1];
            final double[] y = new double[1];

            // Asignar una posición aleatoria para cada supernodo (comunidad)
            x[0] = random.nextInt((int) width);
            y[0] = random.nextInt((int) height);

            // Asegurarse de que los supernodos no se solapen
            while (communityShapes.values().stream()
                    .anyMatch(circle -> Math.hypot(circle.getCenterX() - x[0], circle.getCenterY() - y[0]) < 60)) {
                x[0] = random.nextInt((int) width);
                y[0] = random.nextInt((int) height);
            }

            // Generar un color aleatorio para cada comunidad
            Color communityColor = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            communityColors.put(communityId, communityColor); // Asignar el color a la comunidad

            // Crear un círculo para representar el supernodo
            Circle superNodeCircle = new Circle(x[0], y[0], 40, communityColor);
            Text label = new Text(x[0] - 10, y[0] + 5, "C" + communityId); // Etiqueta con el ID de la comunidad

            communityShapes.put(communityId, superNodeCircle); // Guarda el supernodo con su id de comunidad

            // Asignar los nodos a la comunidad en el mapa
            for (Node node : community) {
                communityMap.put(node, communityId); // Asocia el nodo con el ID de la comunidad
            }

            pane.getChildren().addAll(superNodeCircle, label);
            communityId++; // Incrementar el ID de la comunidad
        }

        // Mapa para almacenar las aristas entre comunidades
        Map<String, Double> communityEdges = new HashMap<>();

        // Recorrer las aristas del grafo original para calcular las aristas entre
        // comunidades
        for (Edge edge : graph.getEdges()) {
            Node sourceNode = edge.getSource();
            Node targetNode = edge.getTarget();

            // Buscar las comunidades a las que pertenecen los nodos
            Integer sourceCommunityId = communityMap.get(sourceNode);
            Integer targetCommunityId = communityMap.get(targetNode);

            // Verificar que ambos nodos pertenezcan a comunidades diferentes
            if (sourceCommunityId != null && targetCommunityId != null
                    && !sourceCommunityId.equals(targetCommunityId)) {
                // Crear una clave para identificar el par de comunidades (sin importar el
                // orden)
                String communityKey = sourceCommunityId < targetCommunityId
                        ? sourceCommunityId + "-" + targetCommunityId
                        : targetCommunityId + "-" + sourceCommunityId;

                // Acumular el peso de la arista entre las dos comunidades
                double currentWeight = communityEdges.getOrDefault(communityKey, 0.0);
                communityEdges.put(communityKey, currentWeight + edge.getWeight());
            }
        }

        // Dibujar las aristas entre las comunidades (supernodos)
        for (Map.Entry<String, Double> entry : communityEdges.entrySet()) {
            String[] communityIds = entry.getKey().split("-");
            int sourceCommunityId = Integer.parseInt(communityIds[0]);
            int targetCommunityId = Integer.parseInt(communityIds[1]);

            // Obtener los círculos (supernodos) correspondientes a las comunidades
            Circle sourceSuperNode = communityShapes.get(sourceCommunityId);
            Circle targetSuperNode = communityShapes.get(targetCommunityId);

            // Verificar que los supernodos no sean null
            if (sourceSuperNode != null && targetSuperNode != null) {
                Line line = new Line(
                        sourceSuperNode.getCenterX(), sourceSuperNode.getCenterY(),
                        targetSuperNode.getCenterX(), targetSuperNode.getCenterY());
                line.setStrokeWidth(2);
                line.setStroke(Color.GRAY);

                // Mostrar el peso de la arista (la suma de los pesos entre los nodos de las
                // comunidades)
                /*
                 * Text weightLabel = new Text(
                 * (sourceSuperNode.getCenterX() + targetSuperNode.getCenterX()) / 2,
                 * (sourceSuperNode.getCenterY() + targetSuperNode.getCenterY()) / 2,
                 * String.valueOf(entry.getValue()));
                 */

                pane.getChildren().addAll(line);
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
