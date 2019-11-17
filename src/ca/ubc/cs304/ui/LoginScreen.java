package ca.ubc.cs304.ui;

import ca.ubc.cs304.controller.SuperRent;

/**
 * Login screen where you select if you are customer of clerk
 */
public class LoginScreen {
    // Todo
    public static void showFrame(SuperRent superRent) {
        // 1. render screen with option customer/clerk
        // 2. redirect based on option
        boolean isCustomer = false;
        boolean isClerk = false;
        if (isCustomer){
            System.out.println("Let me do something as a customer");
            VehicleListScreen vehicleListScreen = new VehicleListScreen(superRent);
            vehicleListScreen.handleSelectVehicleOptions();
        } else if (isClerk) {
            System.out.println("Let me do something as a clerk");
        }
    }
}
