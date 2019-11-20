package ca.ubc.cs304.model;

/**
 * This class stores information of a single vehicle
 */
public class Vehicle {
    private final String licensePlate;
    private final VehicleType vehicleType;
    private final VehicleStatus status;
    private final String color;
    private final String make;
    private final String model;
    private final int year;

    public Vehicle(String licensePlate, VehicleType vehicleType, VehicleStatus status, String color, String make, String model, int year) {
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
        this.status = status;
        this.color = color;
        this.make = make;
        this.model = model;
        this.year = year;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public String getColor() { return color;}

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString(){
        return "ID: " + licensePlate + "  " + make + "  " + model + "  " + year;
    }
}
