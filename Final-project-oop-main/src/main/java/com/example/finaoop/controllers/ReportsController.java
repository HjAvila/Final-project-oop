package com.example.finaoop.controllers;

import com.example.finaoop.database.DataStore;
import com.example.finaoop.models.DisposalRequest;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ReportsController {

    // --- Table Fields ---
    @FXML private TableView<DisposalRequest> table;
    @FXML private TableColumn<DisposalRequest, String> colId;
    @FXML private TableColumn<DisposalRequest, String> colLocation;
    @FXML private TableColumn<DisposalRequest, String> colType;
    @FXML private TableColumn<DisposalRequest, String> colStatus;
    @FXML private TableColumn<DisposalRequest, String> colDate;

    // --- NEW PREVIEW FIELDS ---
    // These link to the new <ImageView> and <Label> in your FXML
    @FXML private ImageView reportPhotoPreview;
    @FXML private Label reportStatusLabel;

    @FXML
    private void initialize() {
        // --- Set up table columns ---
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        // --- Load requests from DataStore ---
        ObservableList<DisposalRequest> requests = DataStore.getAllRequests();
        table.setItems(requests);

        // --- NEW: Add listener to show preview ---
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Load the image
                Image image = DataStore.loadImage(newSelection.getImagePath());
                reportPhotoPreview.setImage(image);

                // Set the text
                reportStatusLabel.setText("Status: " + newSelection.getStatus() + "\n\n" + newSelection.getDescription());
            } else {
                // Clear preview if no selection
                reportPhotoPreview.setImage(null);
                reportStatusLabel.setText("Select a report to see details.");
            }
        });
    }
}