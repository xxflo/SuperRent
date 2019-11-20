package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.Branch;
import ca.ubc.cs304.model.Customer;
import ca.ubc.cs304.model.VehicleType;
import ca.ubc.cs304.util.SceneSwitchUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
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
    private DatabaseConnectionHandler dbHandler =  DatabaseConnectionHandler.getInstance();
    private SceneSwitchUtil sceneSwitchUtil = SceneSwitchUtil.getInstance();
    public Customer customer;
    public VehicleType intendedVehicleType;
//    public Branch intendedBranch;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void handleExistingLogin(ActionEvent actionEvent) throws IOException {
        if (!driverLicense.getText().isEmpty()){
            customer = getCustomer(driverLicense.getText());
            if (customer == null) {
                labelError.setText("Customer does not exist. Try again or sign up instead");
            } else {
                switchToReservation(actionEvent,intendedVehicleType,customer);
            }
        } else {
            labelError.setText("Field cannot be empty");
            driverLicense.setStyle("-fx-border-color: red");
            customer = new Customer("test","test","test","test");
            switchToReservation(actionEvent,intendedVehicleType,customer);
        }
    }

    public void handleNewSignUp(ActionEvent actionEvent) throws IOException {
        String license = newDriverLicense.getText();
        String phoneNum = newCellNumber.getText();
        String address = newAddress.getText();
        String name = newName.getText();
        if (!(license.isEmpty()|| phoneNum.isEmpty()|| name.isEmpty()) ){
            customer = new Customer(license,phoneNum,address,name);
            boolean success = saveCustomer(customer);
            if (!success) {
                newLabelError.setText("Database error. Try again.");
            }
        } else {
            newDriverLicense.setStyle("-fx-border-color: red");
            newCellNumber.setStyle("-fx-border-color: red");
            newName.setStyle("-fx-border-color: red");
            customer = new Customer("test","test","test","test");
            switchToReservation(actionEvent,intendedVehicleType,customer);
        }
    }

    private Customer getCustomer(String name){
        return dbHandler.getCustomer(name);
    }

    private boolean saveCustomer(Customer customer){
        return dbHandler.insertCustomer(customer);
    }

    void setIntendedVehicleType(VehicleType vehicleType){
        this.intendedVehicleType = vehicleType;
        System.out.println(vehicleType);
    }

    private void switchToReservation(ActionEvent actionEvent, VehicleType vehicleType, Customer customer) throws IOException {
        FXMLLoader loader = sceneSwitchUtil.getLoaderForScene(SceneSwitchUtil.reservationFxml);
        Parent root = loader.load();

        ReservationController reservationController = loader.getController();
        reservationController.setIntendedVehicleType(vehicleType);
        reservationController.setCustomer(customer);

        sceneSwitchUtil.switchSceneTo(actionEvent,root);
    }

    // TODO: after deciding on how to model branch
//    void setIntendedLocation(Branch branch){
//        this.intendedBranch = branch;
//        System.out.println(intendedBranch);
//    }
}
