package ca.ubc.cs304.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    public ComboBox tableViewBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewBox.getItems().addAll("Reservations", "Rentals", "Vehicles", "VehicleTypes", "Customers", "Returns");
    }

    public void handleTableViewSelection(ActionEvent actionEvent) {
        System.out.println(actionEvent);
    }
}
