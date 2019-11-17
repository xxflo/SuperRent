package ca.ubc.cs304.model;

import java.util.ArrayList;

public class Branch {
    public final String location;
    public final String city;
    public final ArrayList<Vehicle> vehicles;

    public Branch(String location, String city, ArrayList<Vehicle> vehicles) {
        this.location = location;
        this.city = city;
        this.vehicles = vehicles;
    }

    public ArrayList<Vehicle> getVehiclesByType(VehicleType vehicleType) {
        return null;
    }
}
