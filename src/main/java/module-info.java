module com.example.finaoop {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics; // You need this for layout and styling

    // This opens your controllers so FXML can see them
    opens com.example.finaoop.controllers to javafx.fxml;

    // !!! THIS IS THE FIX !!!
    // This "opens" your models package so the TableView (in javafx.base)
    // has permission to read your DisposalRequest and User classes.
    opens com.example.finaoop.models to javafx.base;

    // This exports your Main class so the app can launch
    exports com.example.finaoop;
}