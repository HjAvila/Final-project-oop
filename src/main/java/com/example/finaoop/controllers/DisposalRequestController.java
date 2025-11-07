package com.example.finaoop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.time.LocalDate;
import java.util.UUID;

public class DisposalRequestController {
    @FXML private TextField locationField;
    @FXML private ComboBox<String> wasteTypeBox;
    @FXML private TextArea descriptionArea;
    @FXML private Label statusLabel;

    @FXML
    public void initialize() {
        if (wasteTypeBox != null) {
            wasteTypeBox.getItems().addAll("Household", "Plastic", "Metal", "Electronic", "Organic", "Hazardous", "Other");
        }
    }

    @FXML
    private void uploadPhoto(ActionEvent event) {
        Window win = locationField.getScene().getWindow();
        FileChooser fc = new FileChooser();
        fc.setTitle("Select Photo");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File file = fc.showOpenDialog(win);
        if (file != null) {
            statusLabel.setText("Photo: " + file.getName());
        }
    }

    @FXML
    private void submitRequest(ActionEvent event) {
        String loc = locationField.getText().trim();
        String type = wasteTypeBox.getValue();
        String desc = descriptionArea.getText().trim();
        String date = LocalDate.now().toString();

        if (loc.isEmpty() || type == null || desc.isEmpty()) {
            statusLabel.setText("Please fill required fields.");
            return;
        }

        String id = UUID.randomUUID().toString();
        RequestsStore.Request r = new RequestsStore.Request(id, loc, type, desc, date, "Pending");
        RequestsStore.add(r);

        statusLabel.setText("Submitted ("+id.substring(0,8)+")");
        locationField.clear();
        wasteTypeBox.getSelectionModel().clearSelection();
        descriptionArea.clear();
    }

    @FXML
    protected void goHome(ActionEvent event) {
        // handled by HomeController via center swap — here we simply request parent to swap back
        statusLabel.getScene().getRoot(); // no-op fallback
    }
}
