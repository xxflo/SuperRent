package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.Branch;
import ca.ubc.cs304.model.Customer;
import ca.ubc.cs304.model.Reservation;
import ca.ubc.cs304.model.VehicleType;
import ca.ubc.cs304.util.SceneSwitchUtil;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmationController implements Initializable {
    public Label confNum;
    public Label typeName;
    public Label date;
    private DatabaseConnectionHandler dbHandler =  DatabaseConnectionHandler.getInstance();
    private SceneSwitchUtil sceneSwitchUtil = SceneSwitchUtil.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setCustomer(Customer customer) {
        System.out.println(customer.getName());
    }

    void setReservation(Reservation reservation) {
        System.out.println(reservation.getConfNo());
        confNum.setText(String.valueOf(reservation.getConfNo()));
        typeName.setText(reservation.getVtname());
        //TODO: fix this to be a date
        date.setText("DD-MM-YYYY-TIME");
    }

    public void handleGoBackMainPressed(ActionEvent actionEvent) throws IOException {
        sceneSwitchUtil.switchSceneTo(actionEvent, SceneSwitchUtil.loginFxml);
    }
}
