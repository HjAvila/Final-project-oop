package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class HelloController {

    @FXML
    private TextField citizenEmailField;
    @FXML
    private PasswordField citizenPasswordField;
    @FXML
    private TextField adminEmailField;
    @FXML
    private PasswordField adminPasswordField;

    @FXML
    protected void onCitizenLoginClick() {
        System.out.println("Citizen logged in: " + citizenEmailField.getText());
    }

    @FXML
    protected void onAdminLoginClick() {
        System.out.println("Admin logged in: " + adminEmailField.getText());
    }
}
