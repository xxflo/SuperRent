package ca.ubc.cs304.controller;
import ca.ubc.cs304.model.Branch;
import ca.ubc.cs304.model.Customer;
import ca.ubc.cs304.model.Reservation;
import ca.ubc.cs304.util.SceneSwitchUtil;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class ConfirmationController implements Initializable {
    public Label confNum;
    public Label typeName;
    public Label fromTime;
    public Label toTime;
    public Label location;
    public Button btnGoBackHome;
    private SceneSwitchUtil sceneSwitchUtil = SceneSwitchUtil.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    void setReservation(Reservation reservation) {
        confNum.setText(String.valueOf(reservation.getConfNo()));
        typeName.setText(reservation.getVtname());
        location.setText(reservation.getBranch().toString());
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Date parsedStartDate = dateFormat.parse(reservation.getFromTime().toString());
            Date parsedEndDate = dateFormat.parse(reservation.getToTime().toString());
            fromTime.setText(parsedStartDate.toString());
            toTime.setText(parsedEndDate.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void handleGoBackMainPressed(ActionEvent actionEvent) throws IOException {
        sceneSwitchUtil.switchSceneTo(actionEvent, SceneSwitchUtil.loginFxml);
    }
}
