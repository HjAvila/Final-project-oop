package com.example.finaoop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/citizen_login.fxml"));
        Scene scene = new Scene(loader.load(), 1000, 500); // wide scene like Figma frames
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        stage.setTitle("Waste Disposal Assistant");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
