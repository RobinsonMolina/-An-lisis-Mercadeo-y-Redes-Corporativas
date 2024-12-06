package co.edu.uptc.view;

import co.edu.uptc.controller.GraphController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Dashboard extends Application {

    private GraphController graphController;

    public Dashboard() {
        // Crear el controlador del grafo dentro del constructor
        this.graphController = new GraphController();

        // Añadir nodos y aristas de ejemplo
        graphController.addNode("1", "Empresa1", "empresa");
        graphController.addNode("2", "Cliente1", "cliente");
        graphController.addEdge("1", "2", 100.0);
        graphController.addNode("3", "Cliente2", "cliente");
        graphController.addNode("4", "Cliente3", "cliente");
        graphController.addNode("5", "Cliente4", "cliente");
        graphController.addNode("6", "Cliente5", "cliente");
        graphController.addNode("7", "Cliente6", "cliente");
        graphController.addEdge("2", "1", 50.0);
        graphController.addEdge("3", "1", 50.0);
        graphController.addEdge("4", "1", 50.0);
        graphController.addEdge("5", "1", 50.0);
        graphController.addEdge("6", "1", 50.0);
        graphController.addEdge("2", "1", 50.0);
        graphController.addEdge("7", "6", 50.0);

    }

    @Override
    public void start(Stage primaryStage) {
        // Vista principal de la aplicación
        BorderPane root = new BorderPane();
        MenuBar menuBar = new MenuBar();

        // Menú principal
        Menu menu = new Menu("Opciones");
        MenuItem viewGraph = new MenuItem("Visualizar Grafo");
        MenuItem exitApp = new MenuItem("Salir");

        menu.getItems().addAll(viewGraph, exitApp);
        menuBar.getMenus().add(menu);

        root.setTop(menuBar);

        // Dentro del método para mostrar el grafo
        viewGraph.setOnAction(e -> {
            // Antes de crear la vista, aseguramos que el grafo está configurado
            GraphView.setGraph(graphController.getGraph());

            // Crear y mostrar la ventana con la escena del grafo
            Stage graphStage = new Stage();
            GraphView graphView = new GraphView();
            graphStage.setScene(graphView.getGraphScene()); // Pasar la escena del grafo
            graphStage.setTitle("Visualización del Grafo");
            graphStage.show();
        });

        // Acción para salir
        exitApp.setOnAction(e -> primaryStage.close());

        // Configuración de la escena
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Crear el controlador del grafo
        GraphController graphController = new GraphController();
        launch(args); // Llamamos a launch para iniciar la aplicación
    }
}
