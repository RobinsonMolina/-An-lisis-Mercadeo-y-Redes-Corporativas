package co.edu.uptc.view;

import co.edu.uptc.controller.GraphController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import javafx.scene.control.TextField;

import java.io.File;

public class Dashboard {

    private GraphController graphController;
    private VBox option;
    private VBox menu;
    private HBox hIcon;
    private BorderPane root;
    private BorderPane principal;

    public Dashboard(GraphController graphController) {
        this.graphController = graphController;
    }

    public Scene createScene() {
        principal = new BorderPane();
        menu = new VBox();
        option = new VBox(10);
        hIcon = new HBox();
        root = new BorderPane();

        createMenu();
        createMenuToggleButton();

        menu.getChildren().addAll(hIcon, option);

        principal.setLeft(menu);
        return new Scene(principal, 800, 600);
    }

    private VBox createMenu() {
        option.setPadding(new Insets(10));
        option.setStyle("-fx-background-color: #f0f0f0;");
        option.setPrefWidth(200);

        Label menuTitle = new Label("Menú");
        menuTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button createGraphButton = new Button("Crear grafo");
        Button viewGraphButton = new Button("Ver grafo");
        Button loadGraphButton = new Button("Cargar grafo");
        Button centralidadButton = new Button("Centralidad");
        Button comunityButton = new Button("Comunidades");
        Button saleButton = new Button("Ventas");

        createGraphButton.setOnAction(e -> showCreateGraphMenu());
        viewGraphButton.setOnAction(e -> viewGraph());
        loadGraphButton.setOnAction(e -> loadGraph());
        centralidadButton.setOnAction(e -> centralidad());
        comunityButton.setOnAction(e -> comunity());
        saleButton.setOnAction(e -> sale());

        option.getChildren().addAll(menuTitle, new Separator(), createGraphButton, viewGraphButton, loadGraphButton);
        return option;
    }

    private HBox createMenuToggleButton() {
        hIcon.setPadding(new Insets(5));
        hIcon.setAlignment(Pos.CENTER_LEFT);

        ImageView menuIcon = new ImageView(getClass().getResource("/Icon.png").toExternalForm());

        menuIcon.setFitWidth(24);
        menuIcon.setFitHeight(24);

        menuIcon.setOnMouseClicked(e -> option.setVisible(!option.isVisible()));

        hIcon.getChildren().add(menuIcon);
        return hIcon;
    }

    private void showCreateGraphMenu() {
        BorderPane root = new BorderPane();
        VBox createGraphArea = new VBox(10);
        createGraphArea.setPadding(new Insets(20));
        createGraphArea.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Opciones de creación");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button addNodeButton = new Button("Añadir Nodo");
        Button addEdgeButton = new Button("Añadir Arista");
        Button removeNodeButton = new Button("Eliminar Nodo");
        Button removeEdgeButton = new Button("Eliminar Arista");
        Button updateNodeButton = new Button("Actualizar Nodo");
        Button updateEdgeButton = new Button("Actualizar Arista");

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

    private void viewGraph() {
        System.out.println("viewGraph");
        BorderPane root = new BorderPane();
        if (graphController.getGraph() != null && !graphController.getGraph().getNodes().isEmpty()) {
            GraphView graphView = new GraphView();
            graphView.setGraph(graphController.getGraph());
            principal.setCenter(graphView.getGraphContainer());
        } else {
            Label noDataLabel = new Label("No hay datos en el grafo.");
            noDataLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
            VBox noDataArea = new VBox(noDataLabel);
            noDataArea.setAlignment(Pos.CENTER);
            noDataArea.setPadding(new Insets(20));
            principal.setCenter(noDataArea);
        }
    }

    private void loadGraph() {
        System.out.println("Entro al metodo");
        BorderPane root = new BorderPane();
        Label loadGraphLabel = new Label("Cargar Grafo");
        loadGraphLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Button loadButton = new Button("Seleccionar archivo");
        loadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar archivo CSV");
            System.out.println("Antes de elegir el archivo");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos CSV", "*.csv"));
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                graphController.loadGraphFromCSV(file.getAbsolutePath());
                viewGraph(); // Actualizar la vista con el grafo cargado
            }
        });
        VBox loadGraphArea = new VBox(10, loadGraphLabel, loadButton);
        loadGraphArea.setAlignment(Pos.CENTER);
        loadGraphArea.setPadding(new Insets(20));
        root.setCenter(loadGraphArea);
        principal.setCenter(root);
    }

    public void addNode() {
        System.out.println("Cargando la vista para agregar nodo...");
        BorderPane addNodePane = new BorderPane();
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);

        Label title = new Label("Agregar Nodo");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField idField = new TextField();
        idField.setPromptText("ID del nodo");

        TextField nameField = new TextField();
        nameField.setPromptText("Nombre del nodo");

        TextField typeField = new TextField();
        typeField.setPromptText("Tipo del nodo");

        Button submitButton = new Button("Agregar Nodo");
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
            } 
            else {
                graphController.addNode(id, name, type);
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Nodo agregado correctamente.");
            }
        });

        content.getChildren().addAll(title, idField, nameField, typeField, submitButton);
        addNodePane.setCenter(content);
        principal.setCenter(addNodePane);
    }


    public void addEdge() {
        System.out.println("Cargando la vista para agregar arista...");
        BorderPane addEdgePane = new BorderPane();
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);

        Label title = new Label("Agregar Arista");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField sourceField = new TextField();
        sourceField.setPromptText("ID nodo fuente");

        TextField targetField = new TextField();
        targetField.setPromptText("ID nodo destino");

        TextField weightField = new TextField();
        weightField.setPromptText("Peso de la arista");

        Button submitButton = new Button("Agregar Arista");
        submitButton.setOnAction(e -> {
            String sourceId = sourceField.getText();
            String targetId = targetField.getText();
            String weightText = weightField.getText();

            if (sourceId.isEmpty() || targetId.isEmpty() || weightText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Todos los campos deben ser completados.");
                return;
            }if (sourceId.equalsIgnoreCase(targetId)) {
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

        Label title = new Label("Eliminar Nodo");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField idField = new TextField();
        idField.setPromptText("ID del nodo a eliminar");

        Button submitButton = new Button("Eliminar Nodo");
        submitButton.setOnAction(e -> {
            String nodeId = idField.getText();

            if (nodeId.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "El ID del nodo no puede estar vacío.");
                return;
            }

            boolean result = graphController.removeNode(nodeId);

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

        Label title = new Label("Eliminar Arista");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField sourceField = new TextField();
        sourceField.setPromptText("ID nodo fuente");

        TextField targetField = new TextField();
        targetField.setPromptText("ID nodo destino");

        Button submitButton = new Button("Eliminar Arista");
        submitButton.setOnAction(e -> {
            String sourceId = sourceField.getText();
            String targetId = targetField.getText();

            if (sourceId.isEmpty() || targetId.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Todos los campos deben ser completados.");
                return;
            }else if (sourceId.equalsIgnoreCase(targetId)) {
                showAlert(Alert.AlertType.ERROR, "Error", "Los ID son los mismos.");
                return;
            }else if (!graphController.isNodeIdAlreadyRegistered(sourceId)) {
                showAlert(Alert.AlertType.ERROR, "Error", "El nodo fuente no existe.");
                return;
            }else if (!graphController.isNodeIdAlreadyRegistered(targetId)) {
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

        Label title = new Label("Actualizar Nodo");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField idField = new TextField();
        idField.setPromptText("ID del nodo a actualizar");

        TextField nameField = new TextField();
        nameField.setPromptText("Nuevo nombre del nodo");

        TextField typeField = new TextField();
        typeField.setPromptText("Nuevo tipo del nodo");

        Button submitButton = new Button("Actualizar Nodo");
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

        Label title = new Label("Actualizar Arista");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField sourceField = new TextField();
        sourceField.setPromptText("ID nodo fuente");

        TextField targetField = new TextField();
        targetField.setPromptText("ID nodo destino");

        TextField weightField = new TextField();
        weightField.setPromptText("Nuevo peso de la arista");

        Button submitButton = new Button("Actualizar Arista");
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
        // Vista para centralidad
    }

    private void comunity() {
        // Vista para comunidades
    }

    private void sale() {
        // Vista para los productos más vendidos
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}