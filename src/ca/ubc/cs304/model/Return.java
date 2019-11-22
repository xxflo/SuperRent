package ca.ubc.cs304.model;

/**
 * Store information about a single return
 */
public class Return {
    private final String rid;
    private final int odometer;
    private final boolean fullTank;
    private final double value;


    public Return(String rid, int odometer, boolean fullTank, double value) {
        this.rid = rid;
        this.odometer = odometer;
        this.fullTank = fullTank;
        this.value = value;
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
}
