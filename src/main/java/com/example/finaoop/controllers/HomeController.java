package com.example.finaoop.controllers;

import com.example.finaoop.database.DataStore;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL; // Import java.net.URL

public class HomeController {

    @FXML private BorderPane rootPane;
    @FXML private Label welcomeLabel;
    @FXML private Label numReports;
    @FXML private Label numActive;
    @FXML private Label numCompleted;

    public void setUsername(String username) {
        if (welcomeLabel != null) welcomeLabel.setText("Welcome, " + username);
    }

    @FXML
    private void initialize() {
        loadCenter("/views/dashboard.fxml");
        updateDashboardStats();
    }

    private void updateDashboardStats() {
        if (numReports != null) numReports.setText(String.valueOf(DataStore.totalReports()));
        if (numActive != null) numActive.setText(String.valueOf(DataStore.activeRequests()));
        if (numCompleted != null) numCompleted.setText(String.valueOf(DataStore.completedTasks()));
    }

    /**
     * [FIXED METHOD]
     * This method now checks if the FXML file exists (is not null)
     * before attempting to load it.
     */
    private void loadCenter(String fxmlPath) {
        try {
            // Get the resource URL
            URL fxmlUrl = getClass().getResource(fxmlPath);

            // **THE FIX**: Check if the file was found
            if (fxmlUrl == null) {
                System.err.println("Error: Cannot find FXML file at path: " + fxmlPath);
                // Display an error message to the user in the UI
                rootPane.setCenter(new Label("Error: Page not found (" + fxmlPath + ")"));
                return; // Exit the method
            }

            // Continue loading since the file was found
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Node center = loader.load();
            rootPane.setCenter(center);

        } catch (IOException e) {
            // This catch block will now handle errors inside the FXML file itself
            System.err.println("Error loading FXML from: " + fxmlPath);
            e.printStackTrace();
            // Display a different error for loading issues
            rootPane.setCenter(new Label("Error: Could not load page. Check FXML file for errors."));
        }
    }

    @FXML private void goToDashboard(ActionEvent event) {
        loadCenter("/views/dashboard.fxml");
        updateDashboardStats();
    }

    @FXML private void goToDisposalRequest(ActionEvent event) {
        loadCenter("/views/disposal_request_center.fxml");
        updateDashboardStats();
    }

    @FXML private void openReports(ActionEvent event) {
        loadCenter("/views/reports_center.fxml");
        updateDashboardStats();
    }

    @FXML private void goToProfile(ActionEvent event) {
        loadCenter("/views/profile.fxml");
    }

    @FXML private void onLogout(ActionEvent event) {
        try {
            Stage stage = (Stage) rootPane.getScene().getWindow();
            // Make sure this login FXML path is also correct
            URL loginUrl = getClass().getResource("/views/citizen_login.fxml");
            if (loginUrl == null) {
                System.err.println("Error: Cannot find login FXML file.");
                return;
            }
            FXMLLoader loader = new FXMLLoader(loginUrl);
            Scene scene = new Scene(loader.load(), 900, 600);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}