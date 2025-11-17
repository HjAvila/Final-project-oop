package com.example.finaoop.controllers;

import com.example.finaoop.database.DataStore;
import com.example.finaoop.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;

public class ProfileController {

    @FXML private Label nameLabel;
    @FXML private Label emailLabel;
    @FXML private Label phoneLabel;
    @FXML private Label ageLabel;
    @FXML private Label addressLabel;
    @FXML private Circle profileCircle;

    @FXML
    private void initialize() {
        // Load the currently logged-in user
        User currentUser = DataStore.getCurrentUser();

        if (currentUser != null) {
            // Set data from the User object
            nameLabel.setText(currentUser.getFullName());
            emailLabel.setText(currentUser.getEmail());
            phoneLabel.setText(currentUser.getPhoneNumber());
            addressLabel.setText(currentUser.getAddress());

            // This field is not in your User model, so we'll set static text
            ageLabel.setText("Age: 25");

        } else {
            // Failsafe in case user is not found
            nameLabel.setText("Error: User Not Found");
            emailLabel.setText("error@example.com");
        }
    }

    @FXML
    private void onChangePicture() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Picture");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            // Apply to Circle
            if (profileCircle != null) {
                profileCircle.setFill(new ImagePattern(image));
            }
        }
    }
}