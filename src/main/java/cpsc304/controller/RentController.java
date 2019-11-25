package cpsc304.controller;

import cpsc304.database.DatabaseConnectionHandler;
import cpsc304.model.*;
import cpsc304.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Pair;
import cpsc304.model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
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
    private SceneSwitchUtil sceneSwitchUtil = SceneSwitchUtil.getInstance();

    private static double maxKmRental = 2000;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reservationCheckbox.setSelected(false);
        withoutReservationInputPane.setVisible(true);
        withReservationInputPane.setVisible(false);
        Arrays.asList(VehicleTypeName.values()).forEach(item -> vehicleTypePicker.getItems().add(item.getName()));
        locationPicker.getItems().addAll(BranchUtil.getInstance().getAllBranchesAsStringArray());
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

        if (returnOdometer.getText() == null || returnOdometer.getText().isEmpty()) {
            showReturnError("You must enter the return odometer");
            return;
        }

        int odometer = Integer.valueOf(returnOdometer.getText());
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
        } else if (returnTimestamp.getTime() <= existingRental.getStartTime().getTime()) {
            showReturnError(String.format("The return time must be greater than the rental start time: %s", existingRental.getStartTime().toString()));
            return;
        }

        Pair<CostSummary, VehicleType> cost = DatabaseConnectionHandler.getInstance().getCostSummary(existingRental.getRid(), odometer, returnTimestamp);
        Pair<Double, String> prices = processPrice(cost.getKey(), cost.getValue());

        Return createdReturn = DatabaseConnectionHandler.getInstance().createReturn(
                odometer, gasFull.isSelected(), prices.getKey(), existingRental, returnTimestamp);

        try {
            confirmReturn(event, createdReturn, prices.getValue(), customer, VehicleTypeName.getVehicleTypeName(cost.getValue().getVtname()), existingRental);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(prices.getKey());
    }

    private void confirmRent(ActionEvent e, Rental r, VehicleTypeName vtname, Branch b, Customer c) throws IOException {
        FXMLLoader loader = SceneSwitchUtil.getInstance().getLoaderForScene(SceneSwitchUtil.confirmationFxml);
        Parent root = loader.load();

        ConfirmationController confirmationController = loader.getController();
        confirmationController.setRental(r, vtname, b, c);

        SceneSwitchUtil.getInstance().switchSceneTo(e,root);
    }

    private void confirmReturn(ActionEvent e, Return r, String receipt, Customer c, VehicleTypeName vtname, Rental rent) throws IOException {
        FXMLLoader loader = SceneSwitchUtil.getInstance().getLoaderForScene(SceneSwitchUtil.confirmationFxml);
        Parent root = loader.load();

        ConfirmationController confirmationController = loader.getController();
        confirmationController.setReturn(r, receipt, c, vtname, rent);

        SceneSwitchUtil.getInstance().switchSceneTo(e,root);
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
                    vt.getKrate(), odometerDiff - 2000);
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
            if (creditCardNumber == null || creditCardNumber.length() != LengthConstants.CREDIT_CARD_NUM_LENGTH) {
                showRentError("You must enter a credit card number (16 digits long)");
                return;
            }
            String expiryDate = reservationExpiryDate.getText();
            if (expiryDate == null || expiryDate.isEmpty()
                    || expiryDate.length() != LengthConstants.EXPIRY_DATE_LENGTH) {
                showRentError("You must select enter an expiry date in form MM/YY");
                return;
            }

            Reservation reservation = DatabaseConnectionHandler.getInstance().getReservation(confNo, customer);

            if (reservation == null && confNo != null && !confNo.isEmpty()) {
                showRentError(String.format("Could not find a reservation using confNo: %s", confNo));
                return;
            } else if (reservation == null) {
                showRentError(String.format("Could not find a reservation using dlicense: %s", customer.getLicense()));
                return;
            }
            if (!nearEnoughStart(reservation.getStartTime())) {
                showRentError("Rentals can only be made within 7 days of today, and cannot be started before current time");
                return;
            }

            Rental r = DatabaseConnectionHandler.getInstance().createRentalFromReservation(
                    reservation,
                    customer,
                    creditCardName,
                    creditCardNumber,
                    expiryDate
            );

            if (r == null) {
                showRentError("Could not find available vehicle for desired rental");
                return;
            }

            try {
                confirmRent(event, r, VehicleTypeName.getVehicleTypeName(reservation.getVtname()), reservation.getBranch(), customer);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Branch location = BranchUtil.decodeBranchFromString(locationPicker.getValue());
            if (location == null) {
                showRentError("You must select a branch");
                return;
            }
            if (vehicleTypePicker.getValue() == null) {
                showRentError("You must select a vehicle type");
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

            if (startTimestamp.getTime() >= endTimeStamp.getTime()) {
                showRentError("Start time must be before end time");
                return;
            }

            if (!nearEnoughStart(startTimestamp)) {
                showRentError("Rentals can only be made within 7 days of today, and cannot be made before today");
                return;
            }
            String creditCardName = noReservationCreditCardName.getText();
            if (creditCardName == null || creditCardName.isEmpty()) {
                showRentError("You must enter a credit card name");
                return;
            }
            String creditCardNumber = noReservationCreditCardNumber.getText();
            if (creditCardNumber == null || creditCardNumber.length() != LengthConstants.CREDIT_CARD_NUM_LENGTH) {
                showRentError("You must enter a credit card number (16 digits long)");
                return;
            }
            String expiryDate = noReservationExpiryDate.getText();
            if (expiryDate == null || expiryDate.length() != LengthConstants.EXPIRY_DATE_LENGTH) {
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
            if (r == null) {
                showRentError("Could not find available vehicle for desired rental");
                return;
            }

            try {
                confirmRent(event, r, vehicleTypeName, location, customer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean nearEnoughStart(Timestamp proposedStartTime) {
        Instant now = Instant.now();
        Instant proposedInstant = Instant.ofEpochMilli(proposedStartTime.getTime());
        if (proposedInstant.toEpochMilli() < now.toEpochMilli()) {
            return false;
        }

        Duration d = Duration.between(now, proposedInstant);
        return d.toDays() < 7;
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

    public void handleGoBackMainPressed(ActionEvent actionEvent) throws IOException {
        sceneSwitchUtil.switchSceneTo(actionEvent, SceneSwitchUtil.loginFxml);
    }
}
