package com.example.finaoop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class HomeController {

    @FXML private Label welcomeLabel;
    @FXML private BorderPane mainLayout; // the root BorderPane in home.fxml

    public void setUsername(String username) {
        if (welcomeLabel != null) {
            welcomeLabel.setText("Welcome, " + username);
        }
    }

    // 🔹 Called when Dashboard button is clicked
    @FXML
    private void goToDashboard(ActionEvent event) {
        loadContent("/views/dashboard.fxml");
        System.out.println("Dashboard button clicked!");
    }

    // 🔹 Called when Disposal Request button is clicked
    @FXML
    private void goToDisposalRequest(ActionEvent event) {
        loadContent("/views/disposal_request.fxml");
        System.out.println("Disposal Request button clicked!");
    }

    // 🔹 Loads FXML into the center of BorderPane (so sidebar remains visible)
    private void loadContent(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Pane content = loader.load();
            mainLayout.setCenter(content); // replace only the main area
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 🔹 Logout logic (returns user to login)
    @FXML
    protected void onLogout(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/citizen_login.fxml"));
        Scene scene = new Scene(loader.load(), 1000, 500);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
