module co.edu.uptc.proyactoprogramacioniii {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;

    // Exportar los paquetes necesarios para ser utilizados por otros módulos
    exports co.edu.uptc.controller;
    exports co.edu.uptc.view;
    exports co.edu.uptc.model.entities;

    opens co.edu.uptc.view to javafx.fxml;
    exports co.edu.uptc;
    opens co.edu.uptc to javafx.fxml;
}