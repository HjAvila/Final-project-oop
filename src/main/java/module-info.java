module com.example.finaoop {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.finaoop.controllers to javafx.fxml;
    opens com.example.finaoop to javafx.fxml;

    exports com.example.finaoop;
}
