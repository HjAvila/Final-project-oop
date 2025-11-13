package com.example.finaoop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

public class HomeController {
    @FXML private Label welcomeLabel;
    @FXML private BorderPane rootPane;

    private String username;
    private String email = "citizen@example.com";
    private String phone = "09123456789";
    private String userType = "Citizen";

    public void setUsername(String username) {
        this.username = username;
        if (welcomeLabel != null) {
            welcomeLabel.setText("Welcome, " + username);
        }
    }


    @FXML
    private void goToDisposalRequest(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/disposal_request_center.fxml"));
        Node center = loader.load();
        rootPane.setCenter(center);
    }

    @FXML
    private void openReports(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/reports_center.fxml"));
        Node center = loader.load();
        rootPane.setCenter(center);
    }

    @FXML
    private void goToDashboard(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/dashboard.fxml"));
        Node center = loader.load();
        rootPane.setCenter(center);
    }


    @FXML
    private void goToProfile(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/profile.fxml"));
        Scene scene = new Scene(loader.load(), 700, 500);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());


        ProfileController pc = loader.getController();
        pc.setProfileData(username, email, phone, userType);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }


    @FXML
    protected void onLogout(ActionEvent event) throws IOException {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/citizen_login.fxml"));
        Scene scene = new Scene(loader.load(), 900, 600);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        stage.setScene(scene);
    }
}
