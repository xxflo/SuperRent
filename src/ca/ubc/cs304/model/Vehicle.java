package ca.ubc.cs304.model;

/**
 * This class stores information of a single vehicle
 */
public class Vehicle {
    public final String licensePlate;
    public final VehicleType vehicleType;
    public final VehicleStatus status;
    public final String cellphone;

    public Vehicle(String licensePlate, VehicleType vehicleType, VehicleStatus status, String cellphone) {
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
        this.status = status;
        this.cellphone = cellphone;
    }
}
