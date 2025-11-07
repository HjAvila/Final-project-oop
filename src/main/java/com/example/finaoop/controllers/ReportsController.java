package com.example.finaoop.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ReportsController {
    @FXML private TableView<RequestsStore.Request> table;
    @FXML private TableColumn<RequestsStore.Request, String> colId;
    @FXML private TableColumn<RequestsStore.Request, String> colLocation;
    @FXML private TableColumn<RequestsStore.Request, String> colType;
    @FXML private TableColumn<RequestsStore.Request, String> colStatus;
    @FXML private TableColumn<RequestsStore.Request, String> colDate;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        table.getItems().setAll(RequestsStore.all());
    }
}
