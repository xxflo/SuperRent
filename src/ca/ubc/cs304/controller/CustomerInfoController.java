package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.Customer;
import ca.ubc.cs304.model.VehicleTypeName;
import ca.ubc.cs304.util.SceneSwitchUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class CustomerInfoController implements Initializable {


    public TextField driverLicense;
    public TextField newDriverLicense;
    public TextField newCellNumber;
    public TextField newName;
    public TextField newAddress;
    public Button btnLogIn;
    public Button btnSignUp;
    public Label labelError;
    public Label newLabelError;
    public LocalDate startDay;
    public LocalDate endDay;
    public LocalTime startT;
    public LocalTime endT;
    private DatabaseConnectionHandler dbHandler =  DatabaseConnectionHandler.getInstance();
    private SceneSwitchUtil sceneSwitchUtil = SceneSwitchUtil.getInstance();
    private Customer customer;
    private VehicleTypeName intendedVehicleType;
//    public Branch intendedBranch;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void handleExistingLogin(ActionEvent actionEvent) throws IOException {
        if (!driverLicense.getText().isEmpty()){
            customer = getCustomer(driverLicense.getText());
            if (customer == null) {
                labelError.setText("Customer does not exist. Try again or sign up instead.");
            } else {
                System.out.println("Customer's driver license is:" + customer.getLicense());
                switchToReservation(actionEvent,intendedVehicleType,customer);
            }
        } else {
            labelError.setText("Field cannot be empty");
            driverLicense.setStyle("-fx-border-color: red");
        }
    }

    public void handleNewSignUp(ActionEvent actionEvent) throws IOException {
        String license = newDriverLicense.getText();
        String phoneNum = newCellNumber.getText();
        String address = newAddress.getText();
        String name = newName.getText();
        if (!license.isEmpty()){
            customer = new Customer(phoneNum,name,address,license);
            boolean success = saveCustomer(customer);
            if (!success) {
                newLabelError.setText("Database error. Try again.");
            } else {
                switchToReservation(actionEvent,intendedVehicleType,customer);
            }
        } else {
            newDriverLicense.setStyle("-fx-border-color: red");
            newLabelError.setText("Driver license cannot be empty.");
        }
    }

    private Customer getCustomer(String name){
        return dbHandler.getCustomer(name);
    }

    private boolean saveCustomer(Customer customer){
        return dbHandler.insertCustomer(customer);
    }

    void setIntendedVehicleType(VehicleTypeName vehicleType){
        this.intendedVehicleType = vehicleType;
        System.out.println(vehicleType);
    }

    void setIntendedDateTime(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime){
        this.startDay = startDate;
        this.endDay = endDate;
        this.startT = startTime;
        this.endT = endTime;
    }

    private void switchToReservation(ActionEvent actionEvent, VehicleTypeName vehicleType, Customer customer) throws IOException {
        FXMLLoader loader = sceneSwitchUtil.getLoaderForScene(SceneSwitchUtil.reservationFxml);
        Parent root = loader.load();

        ReservationController reservationController = loader.getController();
        reservationController.setIntendedVehicleType(vehicleType);
        reservationController.setCustomer(customer);
        reservationController.setIntendedDateTime(startDay, endDay, startT, endT);

        sceneSwitchUtil.switchSceneTo(actionEvent,root);
    }
}
