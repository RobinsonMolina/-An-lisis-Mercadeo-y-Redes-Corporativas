package co.edu.uptc.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ViewPrueba extends Application {
    @Override
    public void start(Stage primaryStage) {
        Button button = new Button("Análisis de Mercado y Redes Corporativas");

        button.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20 10 20;");

        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #0056b3; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20 10 20;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20 10 20;"));

        button.setOnMousePressed(e -> button.setStyle("-fx-background-color: #003d7a; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20 10 20;"));
        button.setOnMouseReleased(e -> button.setStyle("-fx-background-color: #0056b3; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20 10 20;"));

        button.setOnAction(e -> System.out.println("¡Has oprimido el botón!"));

        StackPane root = new StackPane(button);

        Scene scene = new Scene(root, 400, 200);

        primaryStage.setTitle("Ventana de Prueba");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
