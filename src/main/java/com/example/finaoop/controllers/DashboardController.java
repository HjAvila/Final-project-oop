package com.example.finaoop.controllers;

import com.example.finaoop.database.DataStore;
import com.example.finaoop.models.DisposalRequest;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DashboardController {

    // === FIX: Added all the missing @FXML tags ===
    // These lines connect your Java code to your FXML design.
    @FXML private Label numReports;
    @FXML private Label numActive;
    @FXML private Label numCompleted;
    @FXML private TableView<DisposalRequest> tableMain;
    @FXML private TableColumn<DisposalRequest, String> colId;
    @FXML private TableColumn<DisposalRequest, String> colLocation;
    @FXML private TableColumn<DisposalRequest, String> colType;
    @FXML private TableColumn<DisposalRequest, String> colStatus;
    @FXML private TableColumn<DisposalRequest, String> colDate;
    @FXML private Label lblSelected;
    // === END FIX ===


    @FXML
    private void initialize() {
        // 1. Populate the stat cards from the DataStore
        if (numReports != null) numReports.setText(String.valueOf(DataStore.totalReports()));
        if (numActive != null) numActive.setText(String.valueOf(DataStore.activeRequests()));
        if (numCompleted != null) numCompleted.setText(String.valueOf(DataStore.completedTasks()));

        // 2. Set up the table columns to match DisposalRequest.java
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        // 3. Load all requests from the DataStore into the table
        tableMain.setItems(DataStore.getAllRequests());

        // 4. (Optional) Show which item is selected
        tableMain.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                lblSelected.setText(newSel.getId() + ": " + newSel.getLocation());
            } else {
                lblSelected.setText("None");
            }
        });
    }
}