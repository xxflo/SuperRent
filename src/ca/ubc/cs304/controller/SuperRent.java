package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.ClerkDelegate;
import ca.ubc.cs304.delegates.CustomerDelegate;
import ca.ubc.cs304.model.Customer;
import ca.ubc.cs304.model.Reservation;
import ca.ubc.cs304.model.Vehicle;
import ca.ubc.cs304.model.VehicleType;
import ca.ubc.cs304.ui.LoginScreen;
import ca.ubc.cs304.ui.VehicleListScreen;

import java.util.ArrayList;
import java.util.Random;

/**
 * Main controller class, responsible for creating UI frames and servicing requests
 */
public class SuperRent implements ClerkDelegate, CustomerDelegate {
    private DatabaseConnectionHandler dbHandler = null;
    private LoginScreen loginScreen = null;

    public SuperRent() {
        dbHandler = new DatabaseConnectionHandler();
    }

    private void start() {
        loginScreen = new LoginScreen();
        LoginScreen.showFrame(this);
    }

//    public void login() {
//        boolean didConnect = dbHandler.login();
//        if(didConnect) {
//            System.out.println("Logged in");
//        }
//    }

    /**
     * Show list of vehicles based on input options
     */
    public void showVehicles(VehicleType vehicleType, String location, String date) {
        System.out.println("Show me some vehicles");
        //Todo: implement options
        ArrayList<Vehicle> vehicles = dbHandler.getVehiclesBasedOnOption();
        VehicleListScreen screen = new VehicleListScreen(this);
        screen.handleDisplayVehicles(vehicles);
    }

    /**
     * Implement reservation transaction
     */
    // TODO: add phone number parameter
    public void makeReservation(Customer customer, VehicleType vehicleType) {
        int confNo = new Random().nextInt(100000);
        Reservation reservation = new Reservation(confNo, vehicleType, "123-3456");
        boolean didInsert = dbHandler.insertReservation(reservation);
        if (didInsert) {
            System.out.println("SUCCESS!");
            System.out.println(confNo);
        } else {
            System.out.println("SOME ERROR");
        }
    }

    public Customer getCustomer(String name){
        return dbHandler.getCustomer(name);
    }

    //TODO
    public boolean saveCustomer(Customer customer){
        return dbHandler.insertCustomer(customer);
    }

    public static void main(String[] args) {
        System.out.println("Hello world lets do some renting");
        SuperRent superRent = new SuperRent();
        superRent.start();
    }
}
