package com.example.finaoop.controllers;

import com.example.finaoop.database.DataStore;
import com.example.finaoop.models.DisposalRequest;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
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

    @FXML private Label detailsLabel;
    @FXML private ImageView photoPreview;
    @FXML private ComboBox<String> statusComboBox;
    @FXML private Button updateButton;
    @FXML private Label adminStatusLabel;

    private DisposalRequest selectedRequest;

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colRequester.setCellValueFactory(new PropertyValueFactory<>("requesterEmail"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        reportsTable.setItems(DataStore.getAllRequests());

        statusComboBox.setItems(FXCollections.observableArrayList("Pending", "In Progress", "Completed", "Action Taken"));
        updateButton.setDisable(true);

        reportsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedRequest = newSelection;
                detailsLabel.setText(
                        "ID: " + newSelection.getId() + "\n"
                                + "Requester: " + newSelection.getRequesterEmail() + "\n"
                                + "Location: " + newSelection.getLocation() + "\n"
                                + "Type: " + newSelection.getType() + "\n\n"
                                + newSelection.getDescription()
                );

                Image image = DataStore.loadImage(newSelection.getImagePath());
                photoPreview.setImage(image);

                statusComboBox.setValue(newSelection.getStatus());
                updateButton.setDisable(false);
                adminStatusLabel.setText("");
            } else {
                selectedRequest = null;
                detailsLabel.setText("Select a report from the table.");
                photoPreview.setImage(null);
                statusComboBox.setValue(null);
                updateButton.setDisable(true);
            }
        });
    }

    @FXML
    private void updateStatus() {
        if (selectedRequest == null) {
            adminStatusLabel.setText("No request selected.");
            adminStatusLabel.setTextFill(Color.RED);
            return;
        }

        String newStatus = statusComboBox.getValue();
        if (newStatus == null || newStatus.isEmpty()) {
            adminStatusLabel.setText("Please select a status.");
            adminStatusLabel.setTextFill(Color.RED);
            return;
        }

        DataStore.updateRequestStatus(selectedRequest.getId(), newStatus);

        adminStatusLabel.setText("Status updated successfully!");
        adminStatusLabel.setTextFill(Color.GREEN);

        reportsTable.refresh();
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        Stage stage = (Stage) reportsTable.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/citizen_login.fxml"));
        Scene scene = new Scene(loader.load(), 900, 600);
        stage.setScene(scene);
    }
}