package com.example.finaoop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;

public class DisposalRequestController {

    @FXML private TextField nameField;
    @FXML private TextField addressField;
    @FXML private ComboBox<String> wasteTypeBox;
    @FXML private TextField quantityField;
    @FXML private DatePicker pickupDatePicker;
    @FXML private TextArea notesArea;
    @FXML private Label statusLabel;

    @FXML
    private void handleSubmitRequest(ActionEvent event) {
        String name = nameField.getText();
        String address = addressField.getText();
        String type = wasteTypeBox.getValue();
        String qty = quantityField.getText();
        LocalDate date = pickupDatePicker.getValue();

        if (name.isEmpty() || address.isEmpty() || type == null || qty.isEmpty() || date == null) {
            statusLabel.setText("⚠️ Please fill out all required fields.");
            return;
        }

        statusLabel.setText("✅ Disposal request submitted successfully!");
        System.out.println("Request submitted: " + name + " | " + address + " | " + type + " | " + qty + "kg | " + date);
        clearForm();
    }

    private void clearForm() {
        nameField.clear();
        addressField.clear();
        wasteTypeBox.getSelectionModel().clearSelection();
        quantityField.clear();
        pickupDatePicker.setValue(null);
        notesArea.clear();
    }

    // Navigation
    @FXML
    private void goToHome(ActionEvent event) throws IOException {
        switchScene("/views/home.fxml", event);
    }

    @FXML
    private void goToDisposalView(ActionEvent event) throws IOException {
        switchScene("/views/disposal_view.fxml", event);
    }

    @FXML
    private void goToDisposalRequest(ActionEvent event) throws IOException {
        switchScene("/views/disposal_request.fxml", event);
    }

    @FXML
    private void onLogout(ActionEvent event) throws IOException {
        switchScene("/views/citizen_login.fxml", event);
    }

    private void switchScene(String fxmlPath, ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load(), 1000, 600);
        stage.setScene(scene);
    }
}
