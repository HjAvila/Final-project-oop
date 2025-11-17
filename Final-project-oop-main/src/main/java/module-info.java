module com.example.finaoop {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    // This opens your controllers so FXML can see them
    opens com.example.finaoop.controllers to javafx.fxml;

    // This "opens" your models package so the TableView (in javafx.base)
    // has permission to read your DisposalRequest and User classes.

    // This exports your Main class so the app can launch
    exports com.example.finaoop;
    opens com.example.finaoop.models to javafx.base, javafx.fxml;
}