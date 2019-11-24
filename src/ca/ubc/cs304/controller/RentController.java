package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.*;
import ca.ubc.cs304.util.BranchUtil;
import ca.ubc.cs304.util.TimeSpinnerUtil;
import ca.ubc.cs304.util.TimeUtil;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Pair;

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
    public TextField confirmationNumber;
    public TextField reservationCreditCardName;
    public TextField reservationCreditCardNumber;
    public TextField reservationExpiryDate;

    public TextField returnVehicleLicense;
    public TextField returnOdometer;
    public DatePicker returnDate;
    public Spinner returnTime;
    public CheckBox gasFull;

    public DatePicker startDate;
    public DatePicker endDate;
    public Spinner startTime;
    public Spinner endTime;

    public Text rentError;
    public Text returnError;

    private Customer customer;

    private static double maxKmRental = 2000;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reservationCheckbox.setSelected(false);
        withoutReservationInputPane.setVisible(true);
        withReservationInputPane.setVisible(false);
        Arrays.asList(VehicleTypeName.values()).forEach(item -> vehicleTypePicker.getItems().add(item.getName()));
        locationPicker.getItems().addAll(BranchUtil.branchesToStringArray());
        startTime.setValueFactory(TimeSpinnerUtil.getSpinnerFactory());
        endTime.setValueFactory(TimeSpinnerUtil.getSpinnerFactory());
        returnTime.setValueFactory(TimeSpinnerUtil.getSpinnerFactory());
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
    public void onSubmitReturn(ActionEvent event) {
        returnError.setVisible(false);
        String vehicleLicense = returnVehicleLicense.getText();
        if (vehicleLicense == null || vehicleLicense.isEmpty()) {
            showReturnError("You must enter the vehicle license");
            return;
        }

        int odometer = Integer.valueOf(returnOdometer.getText());
        if (odometer == 0) {
            showReturnError("You must enter the return odometer");
            return;
        }

        Timestamp returnTimestamp = TimeUtil.getTimeStamp(returnDate, returnTime);
        if (returnTimestamp == null) {
            showReturnError("You must enter the return time");
            return;
        }

        Rental existingRental = DatabaseConnectionHandler.getInstance().getRental(vehicleLicense, customer.getLicense());
        if (existingRental == null) {
            showReturnError("There is no existing rental for the specified vehicle");
            return;
        } else if (odometer < existingRental.getOdometer()) {
            showReturnError(String.format("The end odometer value must be greater than the starting: %s", existingRental.getOdometer()));
            return;
        } else if (returnTimestamp.getTime() < existingRental.getStartTime().getTime()) {
            showReturnError(String.format("The return time must be greater than the rental start time: %s", existingRental.getStartTime().toString()));
            return;
        }

        Pair<CostSummary, VehicleType> cost = DatabaseConnectionHandler.getInstance().getCostSummary(existingRental.getRid(), odometer, returnTimestamp);
        Pair<Double, String> prices = processPrice(cost.getKey(), cost.getValue());

        Return createdReturn = DatabaseConnectionHandler.getInstance().createReturn(
                odometer, gasFull.isSelected(), prices.getKey(), existingRental, returnTimestamp);

        System.out.println(prices.getKey());
    }

    private Pair<Double, String> processPrice(CostSummary summary, VehicleType vt) {
        long daysBetween = summary.getDaysBetween();
        long hoursBetween = summary.getHoursBetween();
        long weeksBetween = summary.getWeeksBetween();

        double odometerDiff = summary.getOdometerDifference();
        double value = summary.getValue();
        double kmPrice = summary.getKmPrice();


        if (kmPrice > 0) {
            String receipt = String.format("" +
                    "Calculated Rate: %1$s\n" +
                    "Subtotals\n"+
                    "Hourly Insurance: Cost %2$s * Hours %3$s\n" +
                    "Daily Insurance: Cost %4$s * Days %5$s\n" +
                    "Weekly Insurance: Cost %6$s * Weeks %7$s\n" +
                    "Hourly Rate: Cost %8$s * Hours %9$s\n" +
                    "Daily Insurance: Cost %10$s * Days %11$s\n" +
                    "Weekly Rate: Cost %12$s * Weeks %13$s\n" +
                    "Extra KM Rate: Cost %14$s * KM %15$s",
                    value,
                    vt.getHirate(), hoursBetween,
                    vt.getDirate(), daysBetween,
                    vt.getWirate(), weeksBetween,
                    vt.getHrate(), hoursBetween,
                    vt.getDrate(), daysBetween,
                    vt.getWrate(), weeksBetween,
                    vt.getKrate(), odometerDiff);
            return new Pair(value, receipt);
        } else {
            String receipt = String.format("" +
                            "Calculated Rate: %1$s\n" +
                            "Subtotals\n"+
                            "Hourly Insurance: Cost %2$s * Hours %3$s\n" +
                            "Daily Insurance: Cost %4$s * Days %5$s\n" +
                            "Weekly Insurance: Cost %6$s * Weeks %7$s\n" +
                            "Hourly Rate: Cost %8$s * Hours %9$s\n" +
                            "Daily Insurance: Cost %10$s * Days %11$s\n" +
                            "Weekly Rate: Cost %12$s * Weeks %13$s\n" +
                            "No KM overage rate (limit 2000 km you had %14$s)",
                    value,
                    vt.getHirate(), hoursBetween,
                    vt.getDirate(), daysBetween,
                    vt.getWirate(), weeksBetween,
                    vt.getHrate(), hoursBetween,
                    vt.getDrate(), daysBetween,
                    vt.getWrate(), weeksBetween,
                    odometerDiff);
            return new Pair(value, receipt);
        }
    }

    public void onSubmitRental(ActionEvent event) {
        rentError.setVisible(false);
        if (reservationCheckbox.isSelected()) {
            String confNo = confirmationNumber.getText();
            String creditCardName = reservationCreditCardName.getText();
            if (creditCardName == null || creditCardName.isEmpty()) {
                showRentError("You must enter a credit card name");
                return;
            }
            String creditCardNumber = reservationCreditCardNumber.getText();
            if (creditCardNumber == null || creditCardNumber.isEmpty() || creditCardNumber.length() != 10) {
                showRentError("You must enter a credit card number (10 digits long)");
                return;
            }
            String expiryDate = reservationExpiryDate.getText();
            if (expiryDate == null || expiryDate.isEmpty() || expiryDate.length() != 5) {
                showRentError("You must select enter an expiry date in form MM/YY");
                return;
            }
            return;
        } else {
            Branch location = BranchUtil.decodeBranchFromString(locationPicker.getValue());
            if (location == null) {
                showRentError("You must select a branch");
                return;
            }
            VehicleTypeName vehicleTypeName = VehicleTypeName.getVehicleTypeName(vehicleTypePicker.getValue());
            if (vehicleTypeName == null) {
                showRentError("You must select a vehicle type");
                return;
            }
            Timestamp startTimestamp = TimeUtil.getTimeStamp(startDate, startTime);
            if (startTimestamp == null) {
                showRentError("You must select a start time and date");
                return;
            }
            Timestamp endTimeStamp = TimeUtil.getTimeStamp(endDate, endTime);
            if (endTimeStamp == null) {
                showRentError("You must select an end time and date");
                return;
            }
            String creditCardName = noReservationCreditCardName.getText();
            if (creditCardName == null || creditCardName.isEmpty()) {
                showRentError("You must enter a credit card name");
                return;
            }
            String creditCardNumber = noReservationCreditCardNumber.getText();
            if (creditCardNumber == null || creditCardNumber.isEmpty() || creditCardNumber.length() != 10) {
                showRentError("You must enter a credit card number (10 digits long)");
                return;
            }
            String expiryDate = noReservationExpiryDate.getText();
            if (expiryDate == null || expiryDate.isEmpty() || expiryDate.length() != 5) {
                showRentError("You must select enter an expiry date in form MM/YY");
                return;
            }

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

    private void showRentError(String message) {
        rentError.setVisible(true);
        rentError.setText(message);
    }

    private void showReturnError(String message) {
        returnError.setVisible(true);
        returnError.setText(message);
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        reservationDriverLicense.setText(customer.getLicense());
        noReservationDriverLicense.setText(customer.getLicense());
    }
}
