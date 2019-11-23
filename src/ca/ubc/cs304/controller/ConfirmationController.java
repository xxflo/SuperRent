package ca.ubc.cs304.controller;
import ca.ubc.cs304.model.Customer;
import ca.ubc.cs304.model.Reservation;
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
        date.setText(reservation.getFromTime().toString() + reservation.getToTime().toString());
    }

    public void handleGoBackMainPressed(ActionEvent actionEvent) throws IOException {
        sceneSwitchUtil.switchSceneTo(actionEvent, SceneSwitchUtil.loginFxml);
    }
}
