package co.edu.uptc;

import co.edu.uptc.controller.GraphController;
import co.edu.uptc.view.Dashboard;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static final String APP_TITLE = "Gestión de Grafos";

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Obtiene la instancia del controlador
            GraphController graphController = GraphController.getInstance();

            // Inicializa los datos del grafo
            initializeGraph(graphController);

            // Crea el dashboard
            Dashboard dashboard = new Dashboard(graphController);

            // Configura y muestra la ventana principal
            primaryStage.setTitle(APP_TITLE);
            primaryStage.setScene(dashboard.createScene());
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al iniciar la aplicación: " + e.getMessage());
        }
    }

    /**
     * Inicializa los nodos y aristas del grafo.
     * @param graphController Controlador del grafo.
     */
    private void initializeGraph(GraphController graphController) {
        graphController.addNode("1", "Empresa1", "empresa");
        graphController.addNode("2", "Cliente1", "cliente");
        graphController.addEdge("1", "2", 100.0);
        graphController.addNode("3", "Cliente2", "cliente");
        graphController.addNode("4", "Cliente3", "cliente");
        graphController.addNode("5", "Cliente4", "cliente");
        graphController.addNode("6", "Cliente5", "cliente");
        graphController.addNode("7", "Cliente6", "cliente");
        graphController.addEdge("3", "1", 50.0);
        graphController.addEdge("4", "1", 50.0);
        graphController.addEdge("5", "1", 50.0);
        graphController.addEdge("6", "1", 50.0);
        graphController.addEdge("7", "6", 50.0); 
    }
    
}
