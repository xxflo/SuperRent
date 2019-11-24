package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.Customer;
import ca.ubc.cs304.model.Reservation;
import ca.ubc.cs304.model.VehicleTypeName;
import ca.ubc.cs304.util.SceneSwitchUtil;
import ca.ubc.cs304.util.TimeSpinnerUtil;
import ca.ubc.cs304.util.TimeUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;

public class ReservationController implements Initializable {
    public ComboBox<String> vehicleType;
    public Button btnConfirm;
    public Button btnCancel;
    public Label labelError;
    public DatePicker startDate;
    public DatePicker endDate;
    public Spinner startTime;
    public Spinner endTime;

    private Customer customer;
    private SceneSwitchUtil sceneSwitchUtil = SceneSwitchUtil.getInstance();
    private DatabaseConnectionHandler dbHandler =  DatabaseConnectionHandler.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Arrays.asList(VehicleTypeName.values()).forEach(item -> vehicleType.getItems().add(item.getName()));
        startTime.setValueFactory(TimeSpinnerUtil.getSpinnerFactory());
        endTime.setValueFactory(TimeSpinnerUtil.getSpinnerFactory());
    }

    void setCustomer(Customer customer) {
        if (customer != null) {
            this.customer = customer;
        }
    }

    void setIntendedVehicleType(VehicleTypeName vehicleType){
        this.vehicleType.getSelectionModel().select(vehicleType.getName());
    }

    void setIntendedDateTime(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime){
        this.startDate.setValue(startDate);
        this.endDate.setValue(endDate);
        this.startTime.getValueFactory().setValue(startTime);
        this.endTime.getValueFactory().setValue(endTime);
    }

    public void handleConfirmPressed(ActionEvent actionEvent) throws IOException {
        String confNo = String.valueOf(new Random().nextInt(100000));
        String selectedVehicleType = vehicleType.getValue();
        if (startDate.getValue() == null || endDate.getValue() == null || startTime.getValue() == null || endTime.getValue() == null) {
            labelError.setText("You need to enter all the fields");
        }
        Timestamp startTimestamp = TimeUtil.getTimeStamp(startDate,startTime);
        Timestamp endTimestamp = TimeUtil.getTimeStamp(endDate, endTime);
        Reservation reservation = new Reservation(confNo, selectedVehicleType, customer.getLicense(), startTimestamp, endTimestamp);
        boolean isReserveSuccess = makeReservation(reservation);
        if (isReserveSuccess){
            switchToConfirmation(actionEvent, reservation);
        } else {
            labelError.setText("Reservation is not successful. Please modify your criteria.");
        }
    }

    public void handleCancelPressed(ActionEvent actionEvent) throws IOException {
        switchToVehicle(actionEvent);
    }

    private boolean makeReservation(Reservation reservation) {
        return dbHandler.insertReservation(reservation);
    }

    private void switchToConfirmation(ActionEvent actionEvent, Reservation reservation) throws IOException {
        FXMLLoader loader = sceneSwitchUtil.getLoaderForScene(SceneSwitchUtil.confirmationFxml);
        Parent root = loader.load();

        ConfirmationController confirmationController = loader.getController();
        confirmationController.setReservation(reservation);
        confirmationController.setCustomer(customer);

        sceneSwitchUtil.switchSceneTo(actionEvent,root);
    }

    private void switchToVehicle(ActionEvent actionEvent) throws IOException {
        sceneSwitchUtil.switchSceneTo(actionEvent,SceneSwitchUtil.vehicleFxml);
    }
}
