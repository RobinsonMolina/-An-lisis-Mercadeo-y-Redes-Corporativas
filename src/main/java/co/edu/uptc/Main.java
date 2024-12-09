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
        graphController.addNode("8", "Empresa2", "empresa");
        graphController.addNode("9", "Producto1", "producto");
        graphController.addNode("10", "Producto2", "producto");
        graphController.addNode("11", "Producto3", "producto");
        graphController.addEdge("9", "2", 30.0);
        graphController.addEdge("10", "3", 40.0);
        graphController.addEdge("11", "4", 20.0);
        graphController.addEdge("2", "8", 60.0);
        graphController.addEdge("3", "8", 70.0);
        graphController.addEdge("4", "8", 80.0);
        graphController.addEdge("1", "8", 200.0);

        Dashboard dashboard = new Dashboard(graphController);

        primaryStage.setTitle("Gestión de Grafos");
        primaryStage.setScene(dashboard.createScene());
        primaryStage.show();
    }
}
