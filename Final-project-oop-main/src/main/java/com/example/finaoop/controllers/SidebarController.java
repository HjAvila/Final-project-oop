package com.example.finaoop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.IOException;

public class SidebarController {
    @FXML
    protected void onDashboard(ActionEvent event) {
        // stub: load dashboard content or switch center content
        System.out.println("Open Dashboard");
    }

    @FXML
    protected void onDisposalRequest(ActionEvent event) {
        System.out.println("Open Disposal Request");
    }

    @FXML
    protected void onReports(ActionEvent event) {
        System.out.println("Open Reports");
    }

    @FXML
    protected void onProfile(ActionEvent event) {
        System.out.println("Open Profile");
    }

    @FXML
    protected void onAbout(ActionEvent event) {
        System.out.println("Open About");
    }

    @FXML
    protected void onLogout(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/citizen_login.fxml"));
        Scene scene = new Scene(loader.load(), 1000, 500);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        stage.setScene(scene);
    }
}
