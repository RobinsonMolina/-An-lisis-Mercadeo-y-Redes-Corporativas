module co.edu.uptc.proyactoprogramacioniii {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens co.edu.uptc.controller to javafx.fxml;
    opens co.edu.uptc.view to javafx.fxml;
    opens co.edu.uptc.model.entities to javafx.fxml;
    opens co.edu.uptc to javafx.graphics; // Abre el paquete principal para JavaFX

    exports co.edu.uptc.controller;
    exports co.edu.uptc.view;
    exports co.edu.uptc.model.entities;
    exports co.edu.uptc; // Exporta el paquete principal para uso externo
}
