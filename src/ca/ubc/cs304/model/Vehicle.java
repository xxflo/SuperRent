package ca.ubc.cs304.model;

/**
 * This class stores information of a single vehicle
 */
public class Vehicle {
    private final String vlicense;
    private final String make;
    private final String model;
    private final String year;
    private final String color;
    private final int odometer;
    private final String location;
    private final String city;
    private final String vtname;
    private final VehicleStatus status;

    public Vehicle(String vlicense, String make, String model, String year, String color, int odometer, String location, String city, String vtname, VehicleStatus status) {
        this.vlicense = vlicense;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.odometer = odometer;
        this.location = location;
        this.city = city;
        this.vtname = vtname;
        this.status = status;
    }

    public String getVlicense() {
        return vlicense;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getYear() {
        return year;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public String getVtname() {
        return vtname;
    }

    public String getCity() {
        return city;
    }

    public String getLocation() {
        return location;
    }

    public int getOdometer() {
        return odometer;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString(){
        return "ID: " + vlicense + "  " + make + "  " + model + "  " + year;
    }
}
