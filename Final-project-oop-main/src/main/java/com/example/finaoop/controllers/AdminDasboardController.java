package com.example.finaoop.controllers;

import com.example.finaoop.database.DataStore;
import com.example.finaoop.models.DisposalRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory; // Correct import
import javafx.stage.Stage;
import java.io.IOException;

public class AdminDasboardController {

    @FXML private TableView<DisposalRequest> reportsTable;
    @FXML private TableColumn<DisposalRequest, String> colId;
    @FXML private TableColumn<DisposalRequest, String> colRequester;
    @FXML private TableColumn<DisposalRequest, String> colLocation;
    @FXML private TableColumn<DisposalRequest, String> colType;
    @FXML private TableColumn<DisposalRequest, String> colStatus;
    @FXML private TableColumn<DisposalRequest, String> colDate;
    @FXML private TableColumn<DisposalRequest, String> colImage;

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colRequester.setCellValueFactory(new PropertyValueFactory<>("requesterEmail"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colImage.setCellValueFactory(new PropertyValueFactory<>("imagePath"));

        reportsTable.setItems(DataStore.getAllRequests());
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        Stage stage = (Stage) reportsTable.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/citizen_login.fxml"));
        Scene scene = new Scene(loader.load(), 900, 600);
        stage.setScene(scene);
    }
}