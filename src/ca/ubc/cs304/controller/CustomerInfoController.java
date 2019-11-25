package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.Branch;
import ca.ubc.cs304.model.Customer;
import ca.ubc.cs304.model.VehicleTypeName;
import ca.ubc.cs304.util.LengthConstants;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void handleExistingLogin(ActionEvent actionEvent) throws IOException {
        String license = driverLicense.getText();
        if (license.isEmpty()){
            labelError.setText("Driver license cannot be empty.");
            driverLicense.setStyle("-fx-border-color: red");
        } else if (license.length() != LengthConstants.LICENSE_LENGTH) {
            labelError.setText("Driver license needs to be " +
                    LengthConstants.LICENSE_LENGTH + " characters long.");
            driverLicense.setStyle("-fx-border-color: red");
        } else {
            labelError.setText("");
            customer = getCustomer(driverLicense.getText());
            if (customer == null) {
                labelError.setText("Customer does not exist. Try again or sign up instead.");
            } else {
                switchToNextView(actionEvent,intendedVehicleType,customer);
            }
        }
    }

    public void handleNewSignUp(ActionEvent actionEvent) throws IOException {
        String license = newDriverLicense.getText();
        String phoneNum = newCellNumber.getText();
        String address = newAddress.getText();
        String name = newName.getText();
        if (license.isEmpty()){
            newDriverLicense.setStyle("-fx-border-color: red");
            newLabelError.setText("Driver license cannot be empty.");
        } else if (license.length() != LengthConstants.LICENSE_LENGTH) {
            newDriverLicense.setStyle("-fx-border-color: red");
            newLabelError.setText("Driver license needs to be " +
                    LengthConstants.LICENSE_LENGTH + " characters long.");
        } else if (phoneNum != null && phoneNum.length() > LengthConstants.PHONE_LENGTH){
            newCellNumber.setStyle("-fx-border-color: red");
            newLabelError.setText("Phone number needs to be less than " +
                    LengthConstants.PHONE_LENGTH + " characters long.");
        } else {
            newLabelError.setText("");
            customer = new Customer(license, phoneNum, address, name);
            boolean success = saveCustomer(customer);
            if (!success) {
                newLabelError.setText("This license number already exists. Try again.");
            } else {
                switchToNextView(actionEvent,intendedVehicleType,customer);
            }
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

    void setNextView(String fxml) {
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
