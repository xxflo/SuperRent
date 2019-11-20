package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.CustomerDelegate;
import ca.ubc.cs304.model.Customer;
import ca.ubc.cs304.model.Vehicle;
import ca.ubc.cs304.model.VehicleType;

import java.util.ArrayList;

public class VehicleListScreen {
    private CustomerDelegate delegate = null;

//    public VehicleListScreen(CustomerDelegate customerDelegate) {
//        this.delegate  = customerDelegate;
//    }
//
//    // TODO
//    /**
//     * Trigger search based on options selected
//     */
//    public void handleSelectVehicleOptions() {
//        // some kind of UI dropdown list here to get options on
//        // vehicleType, location and date
//        delegate.showVehicles(null, null, null);
//    }
//
//    // TODO
//    public void handleDisplayVehicles(ArrayList<Vehicle> vehicles) {
//    }
//
//    // TODO
//    public void handleReserve(){
//        CustomerInfoScreen customerInfoScreen = new CustomerInfoScreen(delegate);
//        Customer customer = customerInfoScreen.handleCustomerInfo();
//        // close customerInfoScreen
//        // get vehicle type option
//        delegate.makeReservation(customer, VehicleType.ECONOMY/*just default for testing*/);
//    }
}
