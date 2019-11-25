package cpsc304.model;

import java.sql.Timestamp;

/**
 * Store information about a single return
 */
public class Return {
    private final String rid;
    private final int odometer;
    private final boolean fullTank;
    private final double value;
    private final Timestamp returnTime;


    public Return(String rid, int odometer, boolean fullTank, double value, Timestamp returnTime) {
        this.rid = rid;
        this.odometer = odometer;
        this.fullTank = fullTank;
        this.value = value;
        this.returnTime = returnTime;
    }

    public String getRid() {
        return rid;
    }

    public int getOdometer() {
        return odometer;
    }

    public boolean isFullTank() {
        return fullTank;
    }

    public double getValue() {
        return value;
    }

    public Timestamp getReturnTime() {
        return returnTime;
    }
}
