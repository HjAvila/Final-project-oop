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
        // !!! THIS IS THE FIX !!!
        // Load the dashboard by default when the home page opens
        loadCenter("/views/dashboard.fxml");
        updateDashboardStats();
    }

    private void updateDashboardStats() {
        if (numReports != null) numReports.setText(String.valueOf(DataStore.totalReports()));
        if (numActive != null) numActive.setText(String.valueOf(DataStore.activeRequests()));
        if (numCompleted != null) numCompleted.setText(String.valueOf(DataStore.completedTasks()));
    }

    // This is the main navigation logic. It loads a page into the center.
    private void loadCenter(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node center = loader.load();
            rootPane.setCenter(center);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --- These are the methods your FXML buttons MUST call ---

    @FXML private void goToDashboard(ActionEvent event) {
        loadCenter("/views/dashboard.fxml");
        updateDashboardStats(); // Refresh stats when going to dashboard
    }

    @FXML private void goToDisposalRequest(ActionEvent event) {
        loadCenter("/views/disposal_request_center.fxml");
        updateDashboardStats(); // Refresh stats in case a request was added
    }

    @FXML private void openReports(ActionEvent event) {
        loadCenter("/views/reports_center.fxml");
        updateDashboardStats(); // Refresh stats
    }

    @FXML private void goToProfile(ActionEvent event) {
        loadCenter("/views/profile.fxml");
    }

    @FXML private void onLogout(ActionEvent event) {
        try {
            Stage stage = (Stage) rootPane.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/citizen_login.fxml"));
            Scene scene = new Scene(loader.load(), 900, 600);
            stage.setScene(scene);
        } catch (IOException e) { e.printStackTrace(); }
    }
}