package com.example.finaoop.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.File;
import java.io.IOException;

public class ProfileController {
    @FXML private Label nameLabel;
    @FXML private Label emailLabel;
    @FXML private Label phoneLabel;
    @FXML private Label typeLabel;
    @FXML private Circle profileCircle;
    @FXML private ImageView profileImage;

    private String name;
    private String email;
    private String phone;
    private String type;

    public void setProfileData(String name, String email, String phone, String type) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.type = type;

        nameLabel.setText(name);
        emailLabel.setText("Email: " + email);
        phoneLabel.setText("Phone: " + phone);
        typeLabel.setText("Account Type: " + type);
    }

    @FXML
    private void onChangePicture() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            profileImage.setImage(image);
            profileCircle.setFill(new ImagePattern(image));
        }
    }

    @FXML
    private void onBack() throws IOException {
        Stage stage = (Stage) nameLabel.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/home.fxml"));
        Scene scene = new Scene(loader.load(), 1000, 700);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        stage.setScene(scene);
    }
}
