package ca.ubc.cs304.delegates;

import ca.ubc.cs304.model.Customer;
import ca.ubc.cs304.model.VehicleType;

public interface CustomerDelegate {
    void showVehicles(VehicleType vehicleType, String location, String date);
    void makeReservation(Customer customer, VehicleType vehicleType);
    Customer getCustomer(String name);
    boolean saveCustomer(Customer customer);
}
