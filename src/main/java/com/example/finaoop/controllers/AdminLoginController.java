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

public class AdminLoginController {
    @FXML private TextField adminField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    @FXML
    protected void onAdminSignIn(ActionEvent event) throws IOException {
        String admin = adminField.getText();
        String pass = passwordField.getText();

        // placeholder admin check
        if ("admin".equals(admin) && "admin".equals(pass)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/home.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 500);
            scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
            HomeController hc = loader.getController();
            hc.setUsername("Admin: " + admin);

            Stage stage = (Stage) adminField.getScene().getWindow();
            stage.setScene(scene);
        } else {
            messageLabel.setText("Invalid admin credentials.");
        }
    }

    @FXML
    protected void onSwitchToCitizen(ActionEvent event) throws IOException {
        Stage stage = (Stage) adminField.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/citizen_login.fxml"));
        Scene scene = new Scene(loader.load(), 1000, 500);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        stage.setScene(scene);
    }
}
