package com.example.finaoop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminLoginController {
    @FXML private TextField adminField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    @FXML
    private void onAdminSignIn(ActionEvent event) throws IOException {
        String admin = adminField.getText().trim();
        String pass = passwordField.getText();

        if ("admin".equals(admin) && "1234".equals(pass)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/admin_dashboard.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 700);
            scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
            Stage stage = (Stage) adminField.getScene().getWindow();
            stage.setScene(scene);
        } else {
            messageLabel.setText("Invalid credentials (use admin / 1234)");
        }
    }

    @FXML
    private void backToLogin(ActionEvent event) throws IOException {
        Stage stage = (Stage) adminField.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/citizen_login.fxml"));
        Scene scene = new Scene(loader.load(), 900, 600);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        stage.setScene(scene);
    }
}
