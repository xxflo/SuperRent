package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.Branch;
import ca.ubc.cs304.model.Customer;
import ca.ubc.cs304.model.Rental;
import ca.ubc.cs304.model.VehicleTypeName;
import ca.ubc.cs304.util.BranchUtil;
import ca.ubc.cs304.util.TimeSpinnerUtil;
import ca.ubc.cs304.util.TimeUtil;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.ResourceBundle;

public class RentController implements Initializable {
    public Pane withReservationInputPane;
    public Pane withoutReservationInputPane;

    public CheckBox reservationCheckbox;

    public ComboBox<String> vehicleTypePicker;
    public ComboBox<String> locationPicker;
    public TextField reservationDriverLicense;
    public TextField noReservationDriverLicense;
    public TextField noReservationCreditCardName;
    public TextField noReservationCreditCardNumber;
    public TextField noReservationExpiryDate;

    public DatePicker startDate;
    public DatePicker endDate;
    public Spinner startTime;
    public Spinner endTime;

    private Customer customer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reservationCheckbox.setSelected(false);
        withoutReservationInputPane.setVisible(true);
        withReservationInputPane.setVisible(false);
        Arrays.asList(VehicleTypeName.values()).forEach(item -> vehicleTypePicker.getItems().add(item.getName()));
        locationPicker.getItems().addAll(BranchUtil.branchesToStringArray());
        startTime.setValueFactory(TimeSpinnerUtil.getSpinnerFactory());
        endTime.setValueFactory(TimeSpinnerUtil.getSpinnerFactory());
    }

    public void onCheck(ActionEvent event) {
        if (event.getSource() == reservationCheckbox) {
            if (reservationCheckbox.isSelected()) {
                withReservationInputPane.setVisible(true);
                withoutReservationInputPane.setVisible(false);
            } else {
                withReservationInputPane.setVisible(false);
                withoutReservationInputPane.setVisible(true);
            }
        }
    }

    public void onSubmit(ActionEvent event) {
        if (reservationCheckbox.isSelected()) {
            return;
        } else {
            Branch location = BranchUtil.decodeBranchFromString(locationPicker.getValue());
            VehicleTypeName vehicleTypeName = VehicleTypeName.getVehicleTypeName(vehicleTypePicker.getValue());
            Timestamp startTimestamp = TimeUtil.getTimeStamp(startDate, startTime);
            Timestamp endTimeStamp = TimeUtil.getTimeStamp(endDate, endTime);
            String creditCardName = noReservationCreditCardName.getText();
            String creditCardNumber = noReservationCreditCardNumber.getText();
            String expiryDate = noReservationExpiryDate.getText();

            Rental r = DatabaseConnectionHandler.getInstance().createRental(
                    location,
                    vehicleTypeName,
                    startTimestamp,
                    endTimeStamp,
                    customer,
                    creditCardName,
                    creditCardNumber,
                    expiryDate
            );

            System.out.println(r);
        }
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        reservationDriverLicense.setText(customer.getLicense());
        noReservationDriverLicense.setText(customer.getLicense());
    }
}
