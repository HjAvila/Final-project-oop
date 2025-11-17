package com.example.finaoop.controllers;

import com.example.finaoop.database.DataStore;
import com.example.finaoop.models.DisposalRequest;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory; // Correct import

public class ReportsController {

    @FXML private TableView<DisposalRequest> table;
    @FXML private TableColumn<DisposalRequest, String> colId;
    @FXML private TableColumn<DisposalRequest, String> colLocation;
    @FXML private TableColumn<DisposalRequest, String> colType;
    @FXML private TableColumn<DisposalRequest, String> colStatus;
    @FXML private TableColumn<DisposalRequest, String> colDate;

    @FXML
    private void initialize() {
        // Set up table columns to match DisposalRequest.java

        // !!! THIS IS THE FIX !!!
        // Corrected "PropertyValueFActory" to "PropertyValueFactory"
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        // Load requests from DataStore
        ObservableList<DisposalRequest> requests = DataStore.getAllRequests();
        table.setItems(requests);
    }
}