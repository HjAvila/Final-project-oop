package com.example.finaoop.controllers;

import com.example.finaoop.database.DataStore;
import com.example.finaoop.models.User;
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

    // NEW FXML FIELDS
    @FXML private TextField phoneField;
    @FXML private TextField addressField;

    @FXML
    protected void onCreateAccount(ActionEvent event) {
        String full = fullNameField.getText().trim();
        String email = emailField.getText().trim();
        String pass = passwordField.getText();
        String conf = confirmField.getText();

        // NEW FIELDS
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();

        // Update validation
        if (full.isEmpty() || email.isEmpty() || pass.isEmpty() || conf.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            msgLabel.setText("All fields are required.");
            return;
        }

        if (!pass.equals(conf)) {
            msgLabel.setText("Passwords do not match.");
            return;
        }

        if (DataStore.emailExists(email)) {
            msgLabel.setText("Email already registered.");
            return;
        }

        // Update User constructor with new fields
        User newUser = new User(full, email, pass, address, phone);
        DataStore.addUser(newUser);

        // Set the current user in DataStore
        DataStore.setCurrentUser(newUser);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/home.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 700);
            scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());

            HomeController hc = loader.getController();
            hc.setUsername(newUser.getFullName());

            Stage stage = (Stage) fullNameField.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void backToLogin(ActionEvent event) throws IOException {
        try {
            Stage stage = (Stage) fullNameField.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/citizen_login.fxml"));
            Scene scene = new Scene(loader.load(), 900, 600);
            scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}