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
        /*
         * graphController.addNode("1", "Empresa1", "empresa");
         * graphController.addNode("2", "Cliente1", "cliente");
         * graphController.addNode("3", "Cliente2", "cliente");
         * graphController.addEdge("1", "2", 100.0);
         * graphController.addEdge("1", "3", 100.0);
         * graphController.addEdge("2", "3", 50.0);
         * 
         * // Comunidad 2
         * graphController.addNode("4", "Cliente3", "cliente");
         * graphController.addNode("5", "Cliente4", "cliente");
         * graphController.addNode("6", "Cliente5", "cliente");
         * graphController.addEdge("4", "5", 75.0);
         * graphController.addEdge("5", "6", 75.0);
         * graphController.addEdge("4", "6", 75.0);
         * 
         * // Comunidad 3
         * graphController.addNode("7", "Cliente6", "cliente");
         * graphController.addNode("8", "Cliente7", "cliente");
         * graphController.addNode("9", "Cliente8", "cliente");
         * graphController.addEdge("7", "8", 60.0);
         * graphController.addEdge("8", "9", 60.0);
         * graphController.addEdge("7", "9", 60.0);
         * 
         * // Conexiones entre comunidades (menores)
         * graphController.addEdge("3", "4", 10.0); // Poca conexión
         * graphController.addEdge("6", "7", 5.0); // Poca conexión
         */

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

        graphController.addNode("8", "Cliente7", "cliente");
        graphController.addNode("9", "Cliente8", "cliente");
        graphController.addEdge("1", "8", 55.0);
        graphController.addEdge("8", "9", 60.0);
        graphController.addNode("10", "Cliente9", "cliente");
        graphController.addNode("11", "Cliente10", "cliente");
        graphController.addEdge("9", "10", 70.0);
        graphController.addEdge("10", "11", 80.0);
        graphController.addNode("12", "Cliente11", "cliente");
        graphController.addNode("13", "Cliente12", "cliente");
        graphController.addEdge("11", "12", 90.0);
        graphController.addEdge("12", "13", 85.0);
        graphController.addNode("14", "Cliente13", "cliente");
        graphController.addNode("15", "Cliente14", "cliente");
        graphController.addEdge("13", "14", 100.0);
        graphController.addEdge("14", "15", 110.0);
        graphController.addNode("16", "Cliente15", "cliente");
        graphController.addNode("17", "Cliente16", "cliente");
        graphController.addEdge("15", "16", 95.0);
        graphController.addEdge("16", "17", 90.0);
        graphController.addNode("18", "Cliente17", "cliente");
        graphController.addNode("19", "Cliente18", "cliente");
        graphController.addEdge("17", "18", 80.0);
        graphController.addEdge("18", "19", 75.0);
        graphController.addNode("20", "Cliente19", "cliente");
        graphController.addNode("21", "Cliente20", "cliente");
        graphController.addEdge("19", "20", 60.0);
        graphController.addEdge("20", "21", 55.0);
        graphController.addNode("22", "Cliente21", "cliente");
        graphController.addNode("23", "Cliente22", "cliente");
        graphController.addEdge("21", "22", 50.0);
        graphController.addEdge("22", "23", 45.0);
        graphController.addNode("24", "Cliente23", "cliente");
        graphController.addNode("25", "Cliente24", "cliente");
        graphController.addEdge("23", "24", 40.0);
        graphController.addEdge("24", "25", 35.0);
        graphController.addNode("26", "Cliente25", "cliente");
        graphController.addNode("27", "Cliente26", "cliente");
        graphController.addEdge("25", "26", 30.0);
        graphController.addEdge("26", "27", 25.0);
        graphController.addNode("28", "Cliente27", "cliente");
        graphController.addNode("29", "Cliente28", "cliente");
        graphController.addEdge("27", "28", 20.0);
        graphController.addEdge("28", "29", 15.0);
        graphController.addNode("30", "Cliente29", "cliente");
        graphController.addNode("31", "Cliente30", "cliente");
        graphController.addEdge("29", "30", 10.0);
        graphController.addEdge("30", "31", 5.0);
        graphController.addNode("32", "Cliente31", "cliente");
        graphController.addNode("33", "Cliente32", "cliente");
        graphController.addEdge("31", "32", 10.0);
        graphController.addEdge("32", "33", 15.0);
        graphController.addNode("34", "Cliente33", "cliente");
        graphController.addNode("35", "Cliente34", "cliente");
        graphController.addEdge("33", "34", 20.0);
        graphController.addEdge("34", "35", 25.0);
        graphController.addNode("36", "Cliente35", "cliente");
        graphController.addNode("37", "Cliente36", "cliente");
        graphController.addEdge("35", "36", 30.0);
        graphController.addEdge("36", "37", 35.0);
        graphController.addNode("38", "Cliente37", "cliente");
        graphController.addNode("39", "Cliente38", "cliente");
        graphController.addEdge("37", "38", 40.0);
        graphController.addEdge("38", "39", 45.0);
        graphController.addNode("40", "Cliente39", "cliente");
        graphController.addNode("41", "Cliente40", "cliente");
        graphController.addEdge("39", "40", 50.0);
        graphController.addEdge("40", "41", 55.0);
        graphController.addNode("42", "Cliente41", "cliente");
        graphController.addNode("43", "Cliente42", "cliente");
        graphController.addEdge("41", "42", 60.0);
        graphController.addEdge("42", "43", 65.0);
        graphController.addNode("44", "Cliente43", "cliente");
        graphController.addNode("45", "Cliente44", "cliente");
        graphController.addEdge("43", "44", 70.0);
        graphController.addEdge("44", "45", 75.0);
        graphController.addNode("46", "Cliente45", "cliente");
        graphController.addNode("47", "Cliente46", "cliente");
        graphController.addEdge("45", "46", 80.0);
        graphController.addEdge("46", "47", 85.0);
        graphController.addNode("48", "Cliente47", "cliente");
        graphController.addNode("49", "Cliente48", "cliente");
        graphController.addEdge("47", "48", 90.0);
        graphController.addEdge("48", "49", 95.0);
        graphController.addNode("50", "Cliente49", "cliente");
        graphController.addNode("51", "Cliente50", "cliente");
        graphController.addEdge("49", "50", 100.0);
        graphController.addEdge("50", "51", 105.0);

        graphController.addNode("52", "Cliente51", "cliente");
        graphController.addNode("53", "Cliente52", "cliente");
        graphController.addEdge("51", "52", 110.0);
        graphController.addEdge("52", "53", 115.0);
        graphController.addNode("54", "Cliente53", "cliente");
        graphController.addNode("55", "Cliente54", "cliente");
        graphController.addEdge("53", "54", 120.0);
        graphController.addEdge("54", "55", 125.0);
        graphController.addNode("56", "Cliente55", "cliente");
        graphController.addNode("57", "Cliente56", "cliente");
        graphController.addEdge("55", "56", 130.0);
        graphController.addEdge("56", "57", 135.0);
        graphController.addNode("58", "Cliente57", "cliente");
        graphController.addNode("59", "Cliente58", "cliente");
        graphController.addEdge("57", "58", 140.0);
        graphController.addEdge("58", "59", 145.0);
        graphController.addNode("60", "Cliente59", "cliente");
        graphController.addNode("61", "Cliente60", "cliente");
        graphController.addEdge("59", "60", 150.0);
        graphController.addEdge("60", "61", 155.0);
        graphController.addNode("62", "Cliente61", "cliente");
        graphController.addNode("63", "Cliente62", "cliente");
        graphController.addEdge("61", "62", 160.0);
        graphController.addEdge("62", "63", 165.0);
        graphController.addNode("64", "Cliente63", "cliente");
        graphController.addNode("65", "Cliente64", "cliente");
        graphController.addEdge("63", "64", 170.0);
        graphController.addEdge("64", "65", 175.0);
        graphController.addNode("66", "Cliente65", "cliente");
        graphController.addNode("67", "Cliente66", "cliente");
        graphController.addEdge("65", "66", 180.0);
        graphController.addEdge("66", "67", 185.0);
        graphController.addNode("68", "Cliente67", "cliente");
        graphController.addNode("69", "Cliente68", "cliente");
        graphController.addEdge("67", "68", 190.0);
        graphController.addEdge("68", "69", 195.0);
        graphController.addNode("70", "Cliente69", "cliente");
        graphController.addNode("71", "Cliente70", "cliente");
        graphController.addEdge("69", "70", 200.0);
        graphController.addEdge("70", "71", 205.0);

        // Continua con los mismos patrones hasta el nodo 200...

        Dashboard dashboard = new Dashboard(graphController);

        primaryStage.setTitle("Gestión de Grafos");
        primaryStage.setScene(dashboard.createScene());
        primaryStage.show();
    }
}
