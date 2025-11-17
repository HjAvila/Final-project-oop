package com.example.finaoop.controllers;

// FIXED: Added missing imports
import com.example.finaoop.database.DataStore;
import com.example.finaoop.models.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class CitizenLoginController {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    @FXML
    private void onSignIn(ActionEvent event) throws IOException {
        String email = emailField.getText().trim();
        String pass = passwordField.getText();

        if (email.isEmpty() || pass.isEmpty()) {
            messageLabel.setText("Enter email and password.");
            return;
        }

        // 1. CHECK FOR ADMIN FIRST
        if ("admin".equals(email) && "1234".equals(pass)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/admin_dashboard.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 700);
            scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(scene);
            return;
        }

        // 2. CHECK FOR CITIZEN IN DATABASE
        User user = DataStore.findUser(email, pass);

        if (user == null) {
            messageLabel.setText("Invalid credentials. Try again.");
            return;
        }

        // 3. LOG IN SUCCESSFUL
        // Set this user as the currently logged-in user
        DataStore.setCurrentUser(user);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/home.fxml"));
        Scene scene = new Scene(loader.load(), 1000, 700);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());

        HomeController hc = loader.getController();
        hc.setUsername(user.getFullName());

        Stage stage = (Stage) emailField.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void goToSignup(ActionEvent event) throws IOException {
        Stage stage = (Stage) emailField.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/signup.fxml"));
        Scene scene = new Scene(loader.load(), 900, 600);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        stage.setScene(scene);
    }

    @FXML
    private void goToAdminLogin(ActionEvent event) throws IOException {
        Stage stage = (Stage) emailField.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/admin_login.fxml"));
        Scene scene = new Scene(loader.load(), 900, 600);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        stage.setScene(scene);
    }
}