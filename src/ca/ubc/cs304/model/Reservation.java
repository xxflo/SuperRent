package ca.ubc.cs304.model;

/**
 * Store information about a single reservation
 */
public class Reservation {
    private final String confNo;
    private final String vtname;
    private final String driverLicense;
    private String location;
    private Customer customer;
    //TODO: Date


    public Reservation(String confNo, String vtname, String driverLicense) {
        this.confNo = confNo;
        this.vtname = vtname;
        this.driverLicense = driverLicense;
    }

    public String getConfNo() {
        return confNo;
    }

    public String getVtname() {
        return vtname;
    }

    public String getDriverLicense() {
        return driverLicense;
    }
}
