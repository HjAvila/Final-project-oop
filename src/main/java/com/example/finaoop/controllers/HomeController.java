package com.example.finaoop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {
    @FXML private Label welcomeLabel;

    public void setUsername(String username) {
        if (welcomeLabel != null) welcomeLabel.setText("Welcome, " + username);
    }

    @FXML
    protected void onLogout(ActionEvent event) throws IOException {
        Stage stage = (Stage) welcomeLabel.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/citizen_login.fxml"));
        Scene scene = new Scene(loader.load(), 1000, 500);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        stage.setScene(scene);
    }
}
