package com.example.finaoop.controllers;

import com.example.finaoop.database.DataStore;
import com.example.finaoop.models.DisposalRequest;
import com.example.finaoop.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class DisposalRequestController {

    @FXML private TextField locationField;
    @FXML private ComboBox<String> wasteTypeBox;
    @FXML private TextArea descriptionArea;
    @FXML private ImageView photoPreview;
    @FXML private Label statusLabel;

    @FXML private TableView<DisposalRequest> latestRequestsTable;
    @FXML private TableColumn<DisposalRequest, String> locationCol;
    @FXML private TableColumn<DisposalRequest, String> typeCol;
    @FXML private TableColumn<DisposalRequest, String> statusCol;

    private String selectedPhotoPath;
    private final Path uploadsDir = Paths.get(System.getProperty("user.home"), "waste-reports");

    @FXML
    private void initialize() {
        initializeWasteTypeBox();
        initializeTable();
        refreshLatestRequests();
    }

    @FXML
    private void choosePhoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select evidence photo");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image files", "*.png", "*.jpg", "*.jpeg", "*.bmp")
        );

        Window owner = getWindow();
        if (owner == null) {
            showStatus("Cannot open file picker right now.", false);
            return;
        }

        File chosen = fileChooser.showOpenDialog(owner);
        if (chosen == null) {
            return;
        }

        try {
            selectedPhotoPath = storePhoto(chosen);
            Image image = new Image(new File(selectedPhotoPath).toURI().toString());
            photoPreview.setImage(image);
            showStatus("Photo attached.", true);
        } catch (IOException ex) {
            showStatus("Couldn't save photo. Try again.", false);
            ex.printStackTrace();
        }
    }

    @FXML
    private void uploadPhoto(ActionEvent event) {
        choosePhoto(event);
    }

    @FXML
    private void submitRequest(ActionEvent event) {
        String location = locationField.getText() != null ? locationField.getText().trim() : "";
        String wasteType = wasteTypeBox.getValue();
        String description = descriptionArea.getText() != null ? descriptionArea.getText().trim() : "";

        if (location.isEmpty() || wasteType == null || wasteType.isEmpty() || description.isEmpty()) {
            showStatus("Please complete all required fields.", false);
            return;
        }

        User user = DataStore.getCurrentUser();
        if (user == null) {
            showStatus("You must be signed in to submit a report.", false);
            return;
        }

        DisposalRequest request = new DisposalRequest(
                generateRequestId(),
                user.getEmail(),
                location,
                wasteType,
                description,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                "Pending",
                selectedPhotoPath
        );

        DataStore.addRequest(request);
        showStatus("Report submitted successfully!", true);
        clearForm();
        refreshLatestRequests();
    }

    private void initializeWasteTypeBox() {
        List<String> wasteTypes = Arrays.asList(
                "Household Waste",
                "Recyclables",
                "Organic Waste",
                "Hazardous Materials",
                "Electronic Waste",
                "Other"
        );
        wasteTypeBox.setItems(FXCollections.observableArrayList(wasteTypes));
    }

    private void initializeTable() {
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void refreshLatestRequests() {
        ObservableList<DisposalRequest> data = DataStore.getRequestsForCurrentUser();
        latestRequestsTable.setItems(data);
    }

    private void clearForm() {
        locationField.clear();
        wasteTypeBox.getSelectionModel().clearSelection();
        descriptionArea.clear();
        selectedPhotoPath = null;
        photoPreview.setImage(null);
    }

    private void showStatus(String message, boolean success) {
        statusLabel.setText(message);
        statusLabel.setStyle(success ? "-fx-text-fill: #1f8a42;" : "-fx-text-fill: #c15c3c;");
    }

    private String storePhoto(File original) throws IOException {
        if (!Files.exists(uploadsDir)) {
            Files.createDirectories(uploadsDir);
        }
        String extension = extractExtension(original.getName());
        String fileName = "report-" + UUID.randomUUID().toString().replace("-", "").substring(0, 10) + extension;
        Path destination = uploadsDir.resolve(fileName);
        Files.copy(original.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
        return destination.toAbsolutePath().toString();
    }

    private String extractExtension(String fileName) {
        int idx = fileName.lastIndexOf('.');
        if (idx == -1) return ".png";
        return fileName.substring(idx);
    }

    private Window getWindow() {
        if (locationField != null && locationField.getScene() != null) {
            return locationField.getScene().getWindow();
        }
        if (statusLabel != null && statusLabel.getScene() != null) {
            return statusLabel.getScene().getWindow();
        }
        return null;
    }

    private String generateRequestId() {
        return "REQ-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}

