package com.example.finaoop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class CitizenLoginController {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    @FXML
    protected void onSignInClick(ActionEvent event) throws IOException {
        String email = emailField.getText();
        String pass = passwordField.getText();

        // placeholder auth: accept if not empty (replace with real auth later)
        if (!email.isEmpty() && !pass.isEmpty()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/home.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 500);
            scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
            HomeController hc = loader.getController();
            hc.setUsername(email); // pass username/email

            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(scene);
        } else {
            messageLabel.setText("Enter email and password.");
        }
    }

    @FXML
    protected void onGoToSignup(ActionEvent event) throws IOException {
        Stage stage = (Stage) emailField.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/signup.fxml"));
        Scene scene = new Scene(loader.load(), 1000, 500);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        stage.setScene(scene);
    }

    @FXML
    protected void onSwitchToAdmin(ActionEvent event) throws IOException {
        Stage stage = (Stage) emailField.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/admin_login.fxml"));
        Scene scene = new Scene(loader.load(), 1000, 500);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        stage.setScene(scene);
    }
}
