package cpsc304.controller;
import cpsc304.model.*;
import cpsc304.util.SceneSwitchUtil;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.ResourceBundle;

public class ConfirmationController implements Initializable {
    public Label reservationConfNum;
    public Label reservationTypeName;
    public Label reservationFromTime;
    public Label reservationToTime;
    public Label reservationLocation;

    public Text rentalConfNo;
    public Text rentalVtName;
    public Text rentalStartTime;
    public Text rentalLocation;
    public Text rentalDuration;
    public Text rentalCustomerName;
    public Text rentalVehicleLicense;
    public Text rentalCustomerDriverLicense;
    public Text rentalCustomerAddress;
    public Text rentalCustomerPhoneNumber;

    public Text returnConfNo;
    public Text returnVtName;
    public Text returnReturnTime;
    public Text returnStartDate;
    public Text returnDuration;
    public Text returnCustomerName;
    public Text returnCustomerDriverLicense;
    public Text returnCustomerAddress;
    public Text returnCustomerPhoneNumber;
    public Text returnReceipt;
    public Text returnVehicleLicense;

    public Button btnGoBackHome;

    public Pane reservationPane;
    public Pane rentPane;
    public Pane returnPane;
    private SceneSwitchUtil sceneSwitchUtil = SceneSwitchUtil.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    void setReservation(Reservation reservation) {
        System.out.println(reservation.getConfNo());
        hidePanes();
        reservationPane.setVisible(true);
        reservationConfNum.setText(String.valueOf(reservation.getConfNo()));
        reservationTypeName.setText(reservation.getVtname());
        reservationLocation.setText(reservation.getBranch().toString());
        reservationFromTime.setText(getFormattedDate(reservation.getStartTime()));
        reservationToTime.setText(getFormattedDate(reservation.getEndTime()));
    }

    void setRental(Rental rental, VehicleTypeName vtname, Branch b, Customer c) {
        hidePanes();
        rentPane.setVisible(true);
        if (rental.getConfNo() != null) {
            rentalConfNo.setText(rental.getConfNo());
        } else {
            rentalConfNo.setText("No prior reservation made");
        }

        rentalVtName.setText(vtname.getName());
        rentalStartTime.setText(getFormattedDate(rental.getStartTime()));
        rentalLocation.setText(b.getLocation() + " , " + b.getCity());
        rentalDuration.setText(getDaysBetween(rental.getStartTime(), rental.getEndTime()) + " days");
        rentalVehicleLicense.setText(rental.getVlicense());

        rentalCustomerAddress.setText(c.getAddress());
        rentalCustomerName.setText(c.getName());
        rentalCustomerPhoneNumber.setText(c.getPhoneNum());
        rentalCustomerDriverLicense.setText(c.getLicense());
    }

    void setReturn(Return r, String receipt, Customer c, VehicleTypeName vtname, Rental rent) {
        hidePanes();
        returnPane.setVisible(true);
        if (rent.getConfNo() != null) {
            returnConfNo.setText(rent.getConfNo());
        } else {
            returnConfNo.setText("No reservation made");
        }
        returnVtName.setText(vtname.getName());
        returnStartDate.setText(getFormattedDate(rent.getStartTime()));
        returnReturnTime.setText(getFormattedDate(r.getReturnTime()));
        returnDuration.setText(getDaysBetween(rent.getStartTime(), r.getReturnTime()) + " days");
        returnVehicleLicense.setText(rent.getVlicense());

        returnCustomerAddress.setText(c.getAddress());
        returnCustomerName.setText(c.getName());
        returnCustomerPhoneNumber.setText(c.getPhoneNum());
        returnCustomerDriverLicense.setText(c.getLicense());

        returnReceipt.setText(receipt);
    }

    private long getDaysBetween(Timestamp start, Timestamp end) {
        Instant startInstant = Instant.ofEpochMilli(start.getTime());
        Instant endInstant = Instant.ofEpochMilli(end.getTime());

        return ChronoUnit.DAYS.between(startInstant, endInstant);
    }

    private void hidePanes() {
        reservationPane.setVisible(false);
        rentPane.setVisible(false);
        returnPane.setVisible(false);
    }

    public void handleGoBackMainPressed(ActionEvent actionEvent) throws IOException {
        sceneSwitchUtil.switchSceneTo(actionEvent, SceneSwitchUtil.loginFxml);
    }

    private String getFormattedDate(Timestamp timestamp){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Date parsedDate = dateFormat.parse(timestamp.toString());
            return parsedDate.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
