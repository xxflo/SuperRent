package ca.ubc.cs304.model;

/**
 * This class stores information of a single vehicle
 */
public class Vehicle {
    public final String licensePlate;
    public final VehicleType vehicleType;
    public final VehicleStatus status;

    public Vehicle(String licensePlate, VehicleType vehicleType, VehicleStatus status) {
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
        this.status = status;
    }
}
