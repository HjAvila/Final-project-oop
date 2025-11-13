package com.example.finaoop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class SignupController {
    @FXML private TextField fullNameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmField;
    @FXML private Label msgLabel;

    @FXML
    protected void onCreateAccount(ActionEvent event) throws IOException {
        String full = fullNameField.getText().trim();
        String email = emailField.getText().trim();
        String pass = passwordField.getText();
        String conf = confirmField.getText();

        if (full.isEmpty() || email.isEmpty() || pass.isEmpty() || conf.isEmpty()) {
            msgLabel.setText("All fields required.");
            return;
        }
        if (!pass.equals(conf)) {
            msgLabel.setText("Passwords do not match.");
            return;
        }


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/home.fxml"));
        Scene scene = new Scene(loader.load(), 1000, 700);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());

        HomeController hc = loader.getController();
        hc.setUsername(full);

        Stage stage = (Stage) fullNameField.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    protected void backToLogin(ActionEvent event) throws IOException {
        Stage stage = (Stage) fullNameField.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/citizen_login.fxml"));
        Scene scene = new Scene(loader.load(), 900, 600);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        stage.setScene(scene);
    }
}
