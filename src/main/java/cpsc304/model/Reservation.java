package cpsc304.model;

import java.sql.Timestamp;

/**
 * Store information about a single reservation
 */
public class Reservation implements DateTimeModel {
    private final String confNo;
    private final String vtname;
    private final String driverLicense;
    private final Timestamp fromTime;
    private final Timestamp toTime;
    private final Branch branch;

    public Reservation(String confNo, String vtname, String driverLicense, Timestamp fromTime, Timestamp toTime, Branch branch) {
        this.confNo = confNo;
        this.vtname = vtname;
        this.driverLicense = driverLicense;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.branch = branch;
    }

    public Timestamp getFromTime() {
        return fromTime;
    }

    public Timestamp getToTime() {
        return toTime;
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

    @Override
    public Timestamp getStartTime() {
        return fromTime;
    }

    @Override
    public Timestamp getEndTime() {
        return toTime;
    }

    public Branch getBranch() {return branch;}

    @Override
    public String toString(){
        return "(" + (confNo.isEmpty() ? "\'\', " : confNo + ", ")
                + (vtname.isEmpty() ? "\'\', " : vtname + ", ")
                + (driverLicense.isEmpty() ? "\'\', " : driverLicense + ", ")
                + (fromTime == null ? "\'\', " : fromTime.toString() + ", ")
                + (toTime == null ? "\'\', " : toTime.toString())
                + ")";
    }
}
