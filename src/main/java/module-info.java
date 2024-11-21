module co.edu.uptc.proyactoprogramacioniii {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.edu.uptc to javafx.fxml;
    exports co.edu.uptc;
    exports co.edu.uptc.view;
    opens co.edu.uptc.view to javafx.fxml;
}