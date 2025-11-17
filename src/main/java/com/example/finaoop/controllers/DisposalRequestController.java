package com.example.finaoop.controllers;

import com.example.finaoop.database.DataStore;
import com.example.finaoop.models.DisposalRequest;
import com.example.finaoop.models.User;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;
import java.util.UUID;

public class DisposalRequestController {

    @FXML private TextField locationField;
    @FXML private ComboBox<String> wasteTypeBox;
    @FXML private TextArea descriptionArea;
    @FXML private Label statusLabel;
    @FXML private TableView<DisposalRequest> latestRequestsTable;
    @FXML private TableColumn<DisposalRequest, String> locationCol;
    @FXML private TableColumn<DisposalRequest, String> typeCol;
    @FXML private TableColumn<DisposalRequest, String> statusCol;

    @FXML private ImageView photoPreview;
    private String selectedImagePath = null;

    @FXML
    private void initialize() {
        wasteTypeBox.getItems().addAll("Plastic", "Metal", "Glass", "Organic", "Electronic", "Other");
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadTableData();
    }

    private void loadTableData() {
        ObservableList<DisposalRequest> userRequests = DataStore.getRequestsForCurrentUser();
        latestRequestsTable.setItems(userRequests);
    }

    @FXML
    private void choosePhoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Photo");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        Stage stage = (Stage) photoPreview.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            Image image = new Image(file.toURI().toString());
            photoPreview.setImage(image);
            selectedImagePath = file.toURI().toString();
            statusLabel.setText("Photo selected: " + file.getName());
        }
    }

    @FXML
    private void submitRequest() {
        String location = locationField.getText();
        String type = wasteTypeBox.getValue();
        String description = descriptionArea.getText();

        if (location.isEmpty() || type == null || description.isEmpty()) {
            statusLabel.setText("Location, Type, and Description are required!");
            return;
        }

        User currentUser = DataStore.getCurrentUser();
        if (currentUser == null) {
            statusLabel.setText("Error: Not logged in!");
            return;
        }

        String email = currentUser.getEmail();
        String id = UUID.randomUUID().toString().substring(0, 8);
        String date = LocalDate.now().toString();

        DisposalRequest newRequest = new DisposalRequest(
                id, email, location, type, description, date, "Pending", selectedImagePath
        );

        DataStore.addRequest(newRequest);

        loadTableData();
        latestRequestsTable.refresh();
        statusLabel.setText("Request submitted successfully!");
        clearForm();
    }

    private void clearForm() {
        locationField.clear();
        wasteTypeBox.getSelectionModel().clearSelection();
        descriptionArea.clear();
        photoPreview.setImage(null);
        selectedImagePath = null;
    }
}