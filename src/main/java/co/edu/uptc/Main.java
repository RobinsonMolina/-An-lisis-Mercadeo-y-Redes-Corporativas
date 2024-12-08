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
        graphController.addNode("8", "Empresa2", "empresa");
        graphController.addNode("20", "Empresa3", "empresa");
        graphController.addNode("25", "Empresa4", "empresa");
        graphController.addNode("30", "Empresa5", "empresa");
        graphController.addNode("2", "Cliente1", "cliente");
        graphController.addNode("3", "Cliente2", "cliente");
        graphController.addNode("4", "Cliente3", "cliente");
        graphController.addNode("5", "Cliente4", "cliente");
        graphController.addNode("6", "Cliente5", "cliente");
        graphController.addNode("7", "Cliente6", "cliente");
        graphController.addNode("15", "Cliente7", "cliente");
        graphController.addNode("16", "Cliente8", "cliente");
        graphController.addNode("17", "Cliente9", "cliente");
        graphController.addNode("18", "Cliente10", "cliente");
        graphController.addNode("19", "Cliente11", "cliente");
        graphController.addNode("9", "Producto1", "producto");
        graphController.addNode("10", "Producto2", "producto");
        graphController.addNode("11", "Producto3", "producto");
        graphController.addNode("12", "Producto4", "producto");
        graphController.addNode("13", "Producto5", "producto");
        graphController.addNode("14", "Producto6", "producto");
        graphController.addNode("21", "Producto7", "producto");
        graphController.addNode("22", "Producto8", "producto");
        graphController.addNode("23", "Producto9", "producto");
        graphController.addNode("24", "Producto10", "producto");
        graphController.addEdge("1", "2", 100.0);
        graphController.addEdge("1", "3", 80.0);
        graphController.addEdge("1", "4", 90.0);
        graphController.addEdge("1", "5", 70.0);
        graphController.addEdge("8", "6", 60.0);
        graphController.addEdge("8", "7", 75.0);
        graphController.addEdge("20", "15", 50.0);
        graphController.addEdge("20", "16", 65.0);
        graphController.addEdge("25", "17", 85.0);
        graphController.addEdge("25", "18", 95.0);
        graphController.addEdge("30", "19", 100.0);
        graphController.addEdge("30", "15", 55.0);
        graphController.addEdge("2", "9", 30.0);
        graphController.addEdge("3", "10", 40.0);
        graphController.addEdge("4", "11", 50.0);
        graphController.addEdge("5", "12", 20.0);
        graphController.addEdge("6", "13", 25.0);
        graphController.addEdge("7", "14", 35.0);
        graphController.addEdge("15", "21", 45.0);
        graphController.addEdge("16", "22", 50.0);
        graphController.addEdge("17", "23", 30.0);
        graphController.addEdge("18", "24", 40.0);
        graphController.addEdge("1", "9", 15.0);
        graphController.addEdge("1", "10", 20.0);
        graphController.addEdge("8", "11", 25.0);
        graphController.addEdge("8", "12", 30.0);
        graphController.addEdge("20", "13", 40.0);
        graphController.addEdge("25", "14", 50.0);
        graphController.addEdge("30", "21", 60.0);
        graphController.addEdge("30", "22", 70.0);

        Dashboard dashboard = new Dashboard(graphController);

        primaryStage.setTitle("Gestión de Grafos");
        primaryStage.setScene(dashboard.createScene());
        primaryStage.show();
    }
}
