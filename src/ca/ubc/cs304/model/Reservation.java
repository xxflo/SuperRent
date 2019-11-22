package ca.ubc.cs304.model;

import java.sql.Timestamp;

/**
 * Store information about a single reservation
 */
public class Reservation {
    private final String confNo;
    private final String vtname;
    private final String driverLicense;
    private final Timestamp fromTime;
    private final Timestamp toTime;


    public Reservation(String confNo, String vtname, String driverLicense, Timestamp fromTime, Timestamp toTime) {
        this.confNo = confNo;
        this.vtname = vtname;
        this.driverLicense = driverLicense;
        this.fromTime = fromTime;
        this.toTime = toTime;
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
