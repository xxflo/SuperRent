package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.Branch;
import ca.ubc.cs304.model.Customer;
import ca.ubc.cs304.model.VehicleType;
import ca.ubc.cs304.model.VehicleTypeName;
import ca.ubc.cs304.util.SceneSwitchUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

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
    private LocalDate startDay;
    private LocalDate endDay;
    private LocalTime startT;
    private LocalTime endT;
    private Branch branch;
    private DatabaseConnectionHandler dbHandler =  DatabaseConnectionHandler.getInstance();
    private SceneSwitchUtil sceneSwitchUtil = SceneSwitchUtil.getInstance();
    private Customer customer;
    private VehicleTypeName intendedVehicleType;
    private String nextView;

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
                switchToNextView(actionEvent,intendedVehicleType,customer);
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
                switchToNextView(actionEvent,intendedVehicleType,customer);
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
    }

    void setIntendedDateTime(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime){
        this.startDay = startDate;
        this.endDay = endDate;
        this.startT = startTime;
        this.endT = endTime;
    }

    public void setNextView(String fxml) {
        this.nextView = fxml;
    }

    private void switchToNextView(ActionEvent actionEvent, VehicleTypeName vehicleType, Customer customer) throws IOException {
        FXMLLoader loader = sceneSwitchUtil.getLoaderForScene(nextView);
        Parent root = loader.load();

        if (nextView == SceneSwitchUtil.reservationFxml) {
            ReservationController reservationController = loader.getController();
            reservationController.setIntendedVehicleType(vehicleType);
            reservationController.setCustomer(customer);
            reservationController.setIntendedDateTime(startDay, endDay, startT, endT);
            reservationController.setIntendedBranch(branch);
         } else if (nextView == SceneSwitchUtil.rentFxml) {
            RentController rentController = loader.getController();
            rentController.setCustomer(customer);
        }

        sceneSwitchUtil.switchSceneTo(actionEvent,root);
    }

    void setIntendedBranch(Branch branch) {
        this.branch = branch;
    }
}
