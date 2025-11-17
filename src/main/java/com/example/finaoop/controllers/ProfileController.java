package com.example.finaoop.controllers;

import com.example.finaoop.database.DataStore;
import com.example.finaoop.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
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

    private User currentUser;

    @FXML
    private void initialize() {
        currentUser = DataStore.getCurrentUser();

        if (currentUser != null) {
            nameLabel.setText(currentUser.getFullName());
            emailLabel.setText(currentUser.getEmail());
            phoneLabel.setText(currentUser.getPhoneNumber());
            addressLabel.setText(currentUser.getAddress());

            ageLabel.setText("Age: 25");

            loadProfileImage(currentUser.getProfilePicturePath());

        } else {
            nameLabel.setText("Error: User Not Found");
            emailLabel.setText("error@example.com");
        }
    }

    private void loadProfileImage(String imagePath) {
        Image image = DataStore.loadImage(imagePath);
        if (image != null) {
            profileCircle.setFill(new ImagePattern(image));
        } else {
            profileCircle.setFill(Color.web("#cce5ff"));
        }
    }

    @FXML
    private void onChangePicture() {
        if (currentUser == null) return;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Picture");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File file = fileChooser.showOpenDialog(profileCircle.getScene().getWindow());

        if (file != null) {
            String newImagePath = file.toURI().toString();

            DataStore.updateUserProfilePicture(currentUser.getEmail(), newImagePath);

            currentUser.setProfilePicturePath(newImagePath);

            loadProfileImage(newImagePath);
        }
    }
}