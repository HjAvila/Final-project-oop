package com.example.finaoop.controllers;

import com.example.finaoop.database.DataStore;
import com.example.finaoop.models.DisposalRequest;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DashboardController {

    @FXML private Label numReports;
    @FXML private Label numActive;
    @FXML private Label numCompleted;
    @FXML private TableView<DisposalRequest> tableMain;
    @FXML private TableColumn<DisposalRequest, String> colId;
    @FXML private TableColumn<DisposalRequest, String> colLocation;
    @FXML private TableColumn<DisposalRequest, String> colType;
    @FXML private TableColumn<DisposalRequest, String> colStatus;
    @FXML private TableColumn<DisposalRequest, String> colDate;
    @FXML private Label dashboardDetailsLabel;
    @FXML private Label dashboardStatusLabel;
    @FXML private ImageView dashboardPhotoPreview;
    @FXML
    private void initialize() {
        if (numReports != null) numReports.setText(String.valueOf(DataStore.totalReports()));
        if (numActive != null) numActive.setText(String.valueOf(DataStore.activeRequests()));
        if (numCompleted != null) numCompleted.setText(String.valueOf(DataStore.completedTasks()));

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        tableMain.setItems(DataStore.getAllRequests());

        tableMain.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> showRequestDetails(newSel));
        showRequestDetails(null);
    }

    private void showRequestDetails(DisposalRequest request) {
        if (dashboardDetailsLabel == null || dashboardStatusLabel == null) {
            return;
        }

        if (request == null) {
            dashboardStatusLabel.setText("Select a report");
            dashboardDetailsLabel.setText("Choose an item from the table to preview its location, waste type, and description.");
            if (dashboardPhotoPreview != null) {
                dashboardPhotoPreview.setImage(null);
            }
            return;
        }

        dashboardStatusLabel.setText("Status: " + request.getStatus());
        dashboardDetailsLabel.setText(
                "ID: " + request.getId() + "\n"
                        + "Location: " + request.getLocation() + "\n"
                        + "Type: " + request.getType() + "\n"
                        + "Reported by: " + request.getRequesterEmail() + "\n\n"
                        + request.getDescription()
        );

        if (dashboardPhotoPreview != null) {
            Image image = DataStore.loadImage(request.getImagePath());
            dashboardPhotoPreview.setImage(image);
        }
    }
}