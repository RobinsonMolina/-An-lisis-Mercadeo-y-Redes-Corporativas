package co.edu.uptc;

import co.edu.uptc.controller.GraphController;
import co.edu.uptc.view.Dashboard;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        GraphController graphController = new GraphController();

        /*
         * graphController.addNode("1", "Empresa1", "empresa");
         * graphController.addNode("2", "Cliente1", "cliente");
         * graphController.addEdge("1", "2", 100.0);
         * graphController.addNode("3", "Cliente2", "cliente");
         * graphController.addNode("4", "Cliente3", "cliente");
         * graphController.addNode("5", "Cliente4", "cliente");
         * graphController.addNode("6", "Cliente5", "cliente");
         * graphController.addNode("7", "Cliente6", "cliente");
         * graphController.addEdge("2", "1", 50.0);
         * graphController.addEdge("3", "1", 50.0);
         * graphController.addEdge("4", "1", 50.0);
         * graphController.addEdge("5", "1", 50.0);
         * graphController.addEdge("6", "1", 50.0);
         * graphController.addEdge("2", "1", 50.0);
         * graphController.addEdge("7", "6", 50.0);
         */

        // Comunidad 1
        graphController.addNode("1", "Empresa1", "empresa");
        graphController.addNode("2", "Cliente1", "cliente");
        graphController.addNode("3", "Cliente2", "cliente");
        graphController.addEdge("1", "2", 100.0);
        graphController.addEdge("1", "3", 100.0);
        graphController.addEdge("2", "3", 50.0);

        // Comunidad 2
        graphController.addNode("4", "Cliente3", "cliente");
        graphController.addNode("5", "Cliente4", "cliente");
        graphController.addNode("6", "Cliente5", "cliente");
        graphController.addEdge("4", "5", 75.0);
        graphController.addEdge("5", "6", 75.0);
        graphController.addEdge("4", "6", 75.0);

        // Comunidad 3
        graphController.addNode("7", "Cliente6", "cliente");
        graphController.addNode("8", "Cliente7", "cliente");
        graphController.addNode("9", "Cliente8", "cliente");
        graphController.addEdge("7", "8", 60.0);
        graphController.addEdge("8", "9", 60.0);
        graphController.addEdge("7", "9", 60.0);

        // Conexiones entre comunidades (menores)
        graphController.addEdge("3", "4", 10.0); // Poca conexión
        graphController.addEdge("6", "7", 5.0); // Poca conexión

        Dashboard dashboard = new Dashboard(graphController);

        primaryStage.setTitle("Gestión de Grafos");
        primaryStage.setScene(dashboard.createScene());
        primaryStage.show();
    }
}
