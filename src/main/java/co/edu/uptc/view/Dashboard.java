package co.edu.uptc.view;

import co.edu.uptc.controller.GraphController;
import co.edu.uptc.controller.SimilarityController;
import co.edu.uptc.model.TableRowData;
import co.edu.uptc.model.entities.Graph;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import co.edu.uptc.model.entities.Node;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import co.edu.uptc.model.entities.Node;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Dashboard {

    private GraphController graphController;
    private VBox option;
    private VBox menu;
    private HBox hIcon;
    private VBox communityOptions;
    private BorderPane root;
    private BorderPane principal;
    private Button comunityButton;

    public Dashboard(GraphController graphController) {
        this.graphController = graphController;
    }

    public Scene createScene() {
        principal = new BorderPane();
        menu = new VBox();
        option = new VBox(10);
        hIcon = new HBox();
        communityOptions = new VBox();
        root = new BorderPane();

        createMenu();
        createMenuToggleButton();

        menu.getChildren().addAll(hIcon, option);

        principal.setLeft(menu);
        return new Scene(principal, 800, 600);
    }

    private VBox createMenu() {
        option.setPadding(new Insets(10));
        option.setStyle("-fx-background-color: #f0f8ff;");
        option.setPrefWidth(200);
        option.setPrefHeight(600);
        option.setAlignment(Pos.CENTER);

        Label menuTitle = new Label("Menú");
        menuTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button createGraphButton = createStyledButton("Crear grafo");
        Button viewGraphButton = createStyledButton("Ver grafo");
        Button loadGraphButton = createStyledButton("Cargar grafo");
        Button centralidadButton = createStyledButton("Centralidad");
        comunityButton = createStyledButton("Comunidades");
        Button saleButton = createStyledButton("Similitud de Empresas");
        Button productBundleButton = createStyledButton("Similitud de Productos");
        Button report = createStyledButton("Informe");

        Button communityUnoButton = new Button("Comunidades de Grafo");
        Button superCommunityButton = new Button("Comunidades con supernodos");
        styleButton(communityUnoButton);
        styleButton(superCommunityButton);

        comunityButton.setOnAction(e -> toggleCommunityOptions());
        // Crear contenedor y ocultarlo inicialmente
        communityOptions = new VBox(5, communityUnoButton, superCommunityButton);
        communityOptions.setPadding(new Insets(10));
        communityOptions.setAlignment(Pos.CENTER_LEFT);
        communityOptions.setVisible(false);
        communityOptions.setManaged(false); // Ocultar completamente del layout
        comunityButton.setOnAction(e -> toggleCommunityOptions());

        communityUnoButton.setOnAction(e -> comunity(1));
        superCommunityButton.setOnAction(e -> comunity(2));
        createGraphButton.setOnAction(e -> showCreateGraphMenu());
        viewGraphButton.setOnAction(e -> viewGraph());
        loadGraphButton.setOnAction(e -> loadGraph());
        centralidadButton.setOnAction(e -> centralidad());
        // comunityButton.setOnAction(e -> comunity());
        saleButton.setOnAction(e -> sale());
        productBundleButton.setOnAction(e -> showFrequentProductBundles());
        report.setOnAction(e -> showReport());

        option.getChildren().addAll(
                menuTitle,
                new Separator(),
                createGraphButton,
                viewGraphButton,
                loadGraphButton,
                centralidadButton,
                comunityButton,
                communityOptions,
                saleButton,
                productBundleButton,
                report);
        return option;
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-font-size: 12px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-color: #4CAF50; " +
                        "-fx-text-fill: white; " +
                        "-fx-background-radius: 5px; " +
                        "-fx-padding: 10;");
        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-font-size: 12px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-color: #45a049; " +
                        "-fx-text-fill: white; " +
                        "-fx-background-radius: 5px; " +
                        "-fx-padding: 10;"));
        button.setOnMouseExited(e -> button.setStyle(
                "-fx-font-size: 12px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-color: #4CAF50; " +
                        "-fx-text-fill: white; " +
                        "-fx-background-radius: 5px; " +
                        "-fx-padding: 10;"));
        return button;
    }

    private HBox createMenuToggleButton() {
        hIcon.setPadding(new Insets(5));
        hIcon.setAlignment(Pos.CENTER_LEFT);
        hIcon.setStyle("-fx-background-color: #f0f8ff;");

        // ImageView menuIcon = new
        // ImageView(getClass().getResource("/Icon.png").toExternalForm());
        ImageView menuIcon = new ImageView(getClass().getResource("/Icon.png").toExternalForm());

        menuIcon.setFitWidth(24);
        menuIcon.setFitHeight(24);

        menuIcon.setOnMouseClicked(e -> option.setVisible(!option.isVisible()));

        hIcon.getChildren().add(menuIcon);
        return hIcon;
    }

    private void toggleCommunityOptions() {
        boolean isVisible = communityOptions.isVisible();
        communityOptions.setVisible(!isVisible);
        communityOptions.setManaged(!isVisible);
    }

    private void showCreateGraphMenu() {
        BorderPane root = new BorderPane();
        VBox createGraphArea = new VBox(10);
        createGraphArea.setPadding(new Insets(20));
        createGraphArea.setAlignment(Pos.CENTER);

        createGraphArea.setStyle("-fx-background-color: #f0f8ff;");

        Label title = new Label("Opciones de creación");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button addNodeButton = createStyledButton("Añadir Nodo", "#4CAF50");
        Button addEdgeButton = createStyledButton("Añadir Arista", "#2196F3");
        Button removeNodeButton = createStyledButton("Eliminar Nodo", "#FF5722");
        Button removeEdgeButton = createStyledButton("Eliminar Arista", "#FFC107");
        Button updateNodeButton = createStyledButton("Actualizar Nodo", "#9C27B0");
        Button updateEdgeButton = createStyledButton("Actualizar Arista", "#3F51B5");

        addNodeButton.setOnAction(e -> addNode());
        addEdgeButton.setOnAction(e -> addEdge());
        removeNodeButton.setOnAction(e -> removeNode());
        removeEdgeButton.setOnAction(e -> removeEdge());
        updateNodeButton.setOnAction(e -> updateNode());
        updateEdgeButton.setOnAction(e -> updateEdge());

        createGraphArea.getChildren().addAll(
                title,
                addNodeButton,
                addEdgeButton,
                removeNodeButton,
                removeEdgeButton,
                updateNodeButton,
                updateEdgeButton);

        principal.setCenter(createGraphArea);
    }

    private Button createStyledButton(String text, String color) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-color: " + color + ";" +
                        "-fx-background-radius: 5;" +
                        "-fx-border-radius: 5;" +
                        "-fx-padding: 10;");
        button.setPrefWidth(200); // Establecer un ancho fijo para los botones
        return button;
    }

    private void viewGraph() {
        System.out.println("viewGraph");
        BorderPane root = new BorderPane();
        if (graphController.getGraph() != null && !graphController.getGraph().getNodes().isEmpty()) {
            GraphView graphView = new GraphView(graphController.getGraph());
            graphView.getGraphContainer().setStyle("-fx-background-color: #f0f8ff;");
            GraphController.getInstance().getGraph();
            principal.setCenter(graphView.getGraphContainer());
        } else {
            Label noDataLabel = new Label("No hay datos en el grafo.");
            noDataLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
            VBox noDataArea = new VBox(noDataLabel);
            noDataArea.setAlignment(Pos.CENTER);
            noDataArea.setPadding(new Insets(20));
            noDataArea.setStyle("-fx-background-color: #f0f8ff;");
            principal.setCenter(noDataArea);
        }
    }

    private void loadGraph() {
        System.out.println("Entro al método");
        BorderPane root = new BorderPane();
        Label loadGraphLabel = new Label("Cargar Grafo");
        loadGraphLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        Button loadButton = new Button("Seleccionar archivo");
        styleButton(loadButton);
        loadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar archivo CSV");
            System.out.println("Antes de elegir el archivo");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos CSV", "*.csv"));
            File file = fileChooser.showOpenDialog(null);

            if (file != null && file.exists()) {
                graphController.loadGraphFromCSV(file.getAbsolutePath());
                if (graphController.getGraph() != null && !graphController.getGraph().getNodes().isEmpty()) {
                    viewGraph();
                } else {
                    System.out.println("El grafo cargado está vacío o no es válido.");
                }
            } else {
                System.out.println("No se seleccionó un archivo o el archivo no existe.");
            }
        });
        VBox loadGraphArea = new VBox(10, loadGraphLabel, loadButton);
        loadGraphArea.setAlignment(Pos.CENTER);
        loadGraphArea.setPadding(new Insets(20));
        loadGraphArea.setStyle("-fx-background-color: #f0f8ff;");
        root.setCenter(loadGraphArea);
        principal.setCenter(root);
    }

    public void addNode() {
        System.out.println("Cargando la vista para agregar nodo...");
        BorderPane addNodePane = new BorderPane();
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);
        content.setStyle("-fx-background-color: #f0f8ff;");

        Label title = new Label("Agregar Nodo");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField idField = new TextField();
        idField.setPromptText("ID del nodo");

        TextField nameField = new TextField();
        nameField.setPromptText("Nombre del nodo");

        TextField typeField = new TextField();
        typeField.setPromptText("Tipo del nodo");

        Button submitButton = new Button("Agregar Nodo");
        styleButton(submitButton);
        submitButton.setOnAction(e -> {
            String id = idField.getText();
            String name = nameField.getText();
            String type = typeField.getText();

            if (id.isEmpty() || name.isEmpty() || type.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Todos los campos deben ser completados.");
            } else if (graphController.isNodeIdAlreadyRegistered(id)) {
                showAlert(Alert.AlertType.ERROR, "Error", "El ID del nodo ya ha sido registrado.");
            } else if (graphController.isNodeNameAlreadyRegistered(name)) {
                showAlert(Alert.AlertType.ERROR, "Error", "El nombre del nodo ya ha sido registrado.");
            } else {
                graphController.addNode(id, name, type);
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Nodo agregado correctamente.");
            }
        });

        content.getChildren().addAll(title, idField, nameField, typeField, submitButton);
        addNodePane.setCenter(content);
        principal.setCenter(addNodePane);
    }

    private void styleButton(Button button) {
        button.setStyle(
                "-fx-background-color: #2196F3; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 5;");
    }

    private void styleButtonDelete(Button button) {
        button.setStyle(
                "-fx-background-color: #FF0000; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 5;");
    }

    public void addEdge() {
        System.out.println("Cargando la vista para agregar arista...");
        BorderPane addEdgePane = new BorderPane();
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);
        content.setStyle("-fx-background-color: #f0f8ff;");

        Label title = new Label("Agregar Arista");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField sourceField = new TextField();
        sourceField.setPromptText("ID nodo fuente");

        TextField targetField = new TextField();
        targetField.setPromptText("ID nodo destino");

        TextField weightField = new TextField();
        weightField.setPromptText("Peso de la arista");

        Button submitButton = new Button("Agregar Arista");
        styleButton(submitButton);
        submitButton.setOnAction(e -> {
            String sourceId = sourceField.getText();
            String targetId = targetField.getText();
            String weightText = weightField.getText();

            if (sourceId.isEmpty() || targetId.isEmpty() || weightText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Todos los campos deben ser completados.");
                return;
            } else if (sourceId.equalsIgnoreCase(targetId)) {
                showAlert(Alert.AlertType.ERROR, "Error", "No se puede crear una relacion con la misma entidad");
                return;
            }

            try {
                double weight = Double.parseDouble(weightText);
                graphController.addEdge(sourceId, targetId, weight);
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Arista agregada correctamente.");
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "El peso debe ser un número válido.");
            }
        });

        content.getChildren().addAll(title, sourceField, targetField, weightField, submitButton);
        addEdgePane.setCenter(content);
        principal.setCenter(addEdgePane);
    }

    public void removeNode() {
        BorderPane removeNodePane = new BorderPane();
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);
        content.setStyle("-fx-background-color: #f0f8ff;");

        Label title = new Label("Eliminar Nodo");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField idField = new TextField();
        idField.setPromptText("ID del nodo a eliminar");

        Button submitButton = new Button("Eliminar Nodo");
        styleButtonDelete(submitButton);
        submitButton.setOnAction(e -> {
            String nodeId = idField.getText();

            if (nodeId.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "El ID del nodo no puede estar vacío.");
                return;
            }

            boolean result = GraphController.getInstance().removeNode(nodeId);

            if (result) {
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Nodo eliminado correctamente.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "No se encontró el nodo con el ID especificado.");
            }
        });

        content.getChildren().addAll(title, idField, submitButton);
        removeNodePane.setCenter(content);
        principal.setCenter(removeNodePane);
    }

    public void removeEdge() {
        System.out.println("Cargando la vista para eliminar arista...");

        BorderPane removeEdgePane = new BorderPane();
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);
        content.setStyle("-fx-background-color: #f0f8ff;");

        Label title = new Label("Eliminar Arista");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField sourceField = new TextField();
        sourceField.setPromptText("ID nodo fuente");

        TextField targetField = new TextField();
        targetField.setPromptText("ID nodo destino");

        Button submitButton = new Button("Eliminar Arista");
        styleButtonDelete(submitButton);
        submitButton.setOnAction(e -> {
            String sourceId = sourceField.getText();
            String targetId = targetField.getText();

            if (sourceId.isEmpty() || targetId.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Todos los campos deben ser completados.");
                return;
            } else if (sourceId.equalsIgnoreCase(targetId)) {
                showAlert(Alert.AlertType.ERROR, "Error", "Los ID son los mismos.");
                return;
            } else if (!graphController.isNodeIdAlreadyRegistered(sourceId)) {
                showAlert(Alert.AlertType.ERROR, "Error", "El nodo fuente no existe.");
                return;
            } else if (!graphController.isNodeIdAlreadyRegistered(targetId)) {
                showAlert(Alert.AlertType.ERROR, "Error", "El nodo destino no existe.");
                return;
            }

            graphController.removeEdge(sourceId, targetId);
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Arista eliminada correctamente.");
        });

        content.getChildren().addAll(title, sourceField, targetField, submitButton);
        removeEdgePane.setCenter(content);

        principal.setCenter(removeEdgePane);
    }

    public void updateNode() {
        BorderPane updateNodePane = new BorderPane();
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);
        content.setStyle("-fx-background-color: #f0f8ff;");

        Label title = new Label("Actualizar Nodo");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField idField = new TextField();
        idField.setPromptText("ID del nodo a actualizar");

        TextField nameField = new TextField();
        nameField.setPromptText("Nuevo nombre del nodo");

        TextField typeField = new TextField();
        typeField.setPromptText("Nuevo tipo del nodo");

        Button submitButton = new Button("Actualizar Nodo");
        styleButton(submitButton);
        submitButton.setOnAction(e -> {
            String nodeId = idField.getText();
            String name = nameField.getText();
            String type = typeField.getText();

            if (nodeId.isEmpty() || name.isEmpty() || type.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Todos los campos deben ser completados.");
                return;
            }

            boolean result = graphController.updateNode(nodeId, name, type);

            if (result) {
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Nodo actualizado correctamente.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "No se encontró el nodo con el ID especificado.");
            }
        });

        content.getChildren().addAll(title, idField, nameField, typeField, submitButton);
        updateNodePane.setCenter(content);
        principal.setCenter(updateNodePane);
    }

    public void updateEdge() {
        System.out.println("Cargando la vista para actualizar arista...");

        BorderPane updateEdgePane = new BorderPane();
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);
        content.setStyle("-fx-background-color: #f0f8ff;");

        Label title = new Label("Actualizar Arista");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField sourceField = new TextField();
        sourceField.setPromptText("ID nodo fuente");

        TextField targetField = new TextField();
        targetField.setPromptText("ID nodo destino");

        TextField weightField = new TextField();
        weightField.setPromptText("Nuevo peso de la arista");

        Button submitButton = new Button("Actualizar Arista");
        styleButton(submitButton);
        submitButton.setOnAction(e -> {
            String sourceId = sourceField.getText();
            String targetId = targetField.getText();
            String weightText = weightField.getText();

            if (sourceId.isEmpty() || targetId.isEmpty() || weightText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Todos los campos deben ser completados.");
                return;
            }

            try {
                double newWeight = Double.parseDouble(weightText);

                boolean result = graphController.updateEdge(sourceId, targetId, newWeight);

                if (result) {
                    showAlert(Alert.AlertType.INFORMATION, "Éxito", "Arista actualizada correctamente.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "No se encontró la arista para actualizar.");
                }
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "El peso debe ser un número válido.");
            }
        });

        content.getChildren().addAll(title, sourceField, targetField, weightField, submitButton);
        updateEdgePane.setCenter(content);

        principal.setCenter(updateEdgePane);
    }

    private void centralidad() {
        VBox centralityMenu = new VBox(10);
        centralityMenu.setPadding(new Insets(20));
        centralityMenu.setAlignment(Pos.CENTER);
        centralityMenu.setStyle("-fx-background-color: #f0f8ff;");

        Label title = new Label("Análisis de Centralidad");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button degreeCentralityButton = new Button("Centralidad de Grado");
        Button betweennessCentralityButton = new Button("Centralidad de Intermediación");
        Button closenessCentralityButton = new Button("Centralidad de Cercanía");
        styleButton(degreeCentralityButton);
        styleButton(betweennessCentralityButton);
        styleButton(closenessCentralityButton);

        TextArea resultsArea = new TextArea();
        resultsArea.setEditable(false);
        resultsArea.setPromptText("Resultados se mostrarán aquí...");

        degreeCentralityButton.setOnAction(e -> {
            Map<Node, Integer> degreeResults = graphController.calculateDegreeCentrality();
            StringBuilder results = new StringBuilder("Centralidad de Grado:\n");
            degreeResults
                    .forEach((node, degree) -> results.append(node.getName()).append(": ").append(degree).append("\n"));
            resultsArea.setText(results.toString());
        });

        betweennessCentralityButton.setOnAction(e -> {
            Map<Node, Double> betweennessResults = graphController.calculateBetweennessCentrality();
            StringBuilder results = new StringBuilder("Centralidad de Intermediación:\n");
            betweennessResults.forEach((node, betweenness) -> results.append(node.getName()).append(": ")
                    .append(betweenness).append("\n"));
            resultsArea.setText(results.toString());
        });

        closenessCentralityButton.setOnAction(e -> {
            Map<Node, Double> closenessResults = graphController.calculateClosenessCentrality();
            StringBuilder results = new StringBuilder("Centralidad de Cercanía:\n");
            closenessResults.forEach(
                    (node, closeness) -> results.append(node.getName()).append(": ").append(closeness).append("\n"));
            resultsArea.setText(results.toString());
        });

        centralityMenu.getChildren().addAll(title, degreeCentralityButton, betweennessCentralityButton,
                closenessCentralityButton, resultsArea);
        principal.setCenter(centralityMenu);
    }

    private void comunity(int option) {
        // Detectar comunidades
        List<Set<Node>> communities = graphController.detectCommunities();
        Map<Node, Color> nodeColors = new HashMap<>();
        Random rand = new Random();

        // Asignar colores únicos a cada comunidad
        for (Set<Node> community : communities) {
            Color color = Color.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            for (Node node : community) {
                nodeColors.put(node, color);
            }
        }

        // Crear la vista del grafo con comunidades (supernodos)
        GraphView graphView = new GraphView();
        graphView.setGraph(graphController.getGraph());

        System.out.println("Opcion: " + option);
        if (option == 1) {
            System.out.println("Comunidades de grafo");
            principal.setCenter(graphView.getGraphCommunity(nodeColors));
        } else if (option == 2) {
            System.out.println("Comunidades con supernodos");
            principal.setCenter(graphView.getGraphCommunity(nodeColors, communities));
        }
    }

    private void sale() {
        Graph graph = graphController.getGraph();

        if (graph == null || graph.getNodes().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "No hay datos en el grafo");
            return;
        }

        SimilarityController analysis = new SimilarityController();
        Map<Node, List<Node>> similarEntities = analysis.findSimilarEntities(graph);

        TableView<TableRowData> tableView = new TableView<>();
        TableColumn<TableRowData, String> entityColumn = new TableColumn<>("Entidad");
        TableColumn<TableRowData, String> similarColumn = new TableColumn<>("Entidades Similares");

        entityColumn.setCellValueFactory(data -> data.getValue().entityProperty());
        similarColumn.setCellValueFactory(data -> data.getValue().similarEntitiesProperty());

        tableView.getColumns().addAll(entityColumn, similarColumn);

        ObservableList<TableRowData> rows = FXCollections.observableArrayList();
        for (Map.Entry<Node, List<Node>> entry : similarEntities.entrySet()) {
            String entity = entry.getKey().getName();
            String similar = entry.getValue().stream().map(Node::getName).reduce("", (a, b) -> a + ", " + b);
            rows.add(new TableRowData(entity, similar));
        }

        tableView.setItems(rows);

        VBox tableContainer = new VBox(10, new Label("Similitud de Entidades"), tableView);
        tableContainer.setAlignment(Pos.CENTER);
        tableContainer.setPadding(new Insets(20));
        tableContainer.setStyle("-fx-background-color: #f0f8ff; -fx-font-size: 16px; -fx-font-weight: bold;");

        principal.setCenter(tableContainer);
    }

    private void showFrequentProductBundles() {
        Graph graph = graphController.getGraph();

        if (graph == null || graph.getNodes().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "No hay datos en el grafo");
            return;
        }

        SimilarityController analysis = new SimilarityController();
        List<List<Node>> bundles = analysis.findFrequentProductBundles(graph, 20.0);

        TableView<TableRowData> tableView = new TableView<>();
        TableColumn<TableRowData, String> bundleColumn = new TableColumn<>("Productos Frecuentes");

        bundleColumn.setCellValueFactory(data -> data.getValue().bundleProperty());
        tableView.getColumns().add(bundleColumn);

        ObservableList<TableRowData> rows = FXCollections.observableArrayList();
        for (List<Node> bundle : bundles) {
            String products = bundle.stream().map(Node::getName).reduce("", (a, b) -> a + ", " + b);
            rows.add(new TableRowData(products));
        }

        tableView.setItems(rows);

        VBox tableContainer = new VBox(10, new Label("Paquetes de Productos Frecuentes"), tableView);
        tableContainer.setAlignment(Pos.CENTER);
        tableContainer.setPadding(new Insets(20));
        tableContainer.setStyle("-fx-background-color: #f0f8ff; -fx-font-size: 16px; -fx-font-weight: bold;");

        principal.setCenter(tableContainer);
    }

    private void showReport() {
        // Obtener el informe generado por el controlador
        String report = graphController.generateCommunityReport();
    
        // Crear un TextArea para mostrar el informe
        TextArea textArea = new TextArea(report);
        textArea.setEditable(false); // Hacer que el texto no sea editable
        textArea.setWrapText(true); // Ajustar el texto automáticamente a nuevas líneas
        textArea.setStyle("-fx-font-size: 14px; -fx-background-color: #f0f8ff; -fx-padding: 10;");
    
        // Colocar el TextArea dentro de un ScrollPane para permitir desplazamiento
        ScrollPane scrollPane = new ScrollPane(textArea);
        scrollPane.setFitToWidth(true); // Ajustar el ancho al tamaño de la ventana
        scrollPane.setFitToHeight(true); // Ajustar la altura al tamaño de la ventana
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Mostrar barra vertical solo si es necesario
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Mostrar barra horizontal solo si es necesario
    
        // Crear una nueva escena o layout para mostrar el informe
        BorderPane reportLayout = new BorderPane();
        reportLayout.setCenter(scrollPane);
    
        // Establecer el nuevo layout como el centro de la vista principal
        principal.setCenter(reportLayout);
    
        // Asegúrate de que el layout se ajuste al tamaño disponible
        BorderPane.setMargin(scrollPane, new Insets(10)); // Agregar margen si lo deseas
    }
    

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}