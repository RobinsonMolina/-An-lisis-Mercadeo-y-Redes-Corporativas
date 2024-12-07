package co.edu.uptc.view;

import co.edu.uptc.controller.GraphController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

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

        ImageView menuIcon = new ImageView(new Image("/Icon.png"));
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

        createGraphArea.getChildren().addAll(
                title,
                addNodeButton,
                addEdgeButton,
                removeNodeButton,
                removeEdgeButton,
                updateNodeButton,
                updateEdgeButton);

        root.setCenter(createGraphArea);
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
}
