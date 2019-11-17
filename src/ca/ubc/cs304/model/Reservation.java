package ca.ubc.cs304.model;

/**
 * Store information about a single reservation
 */
public class Reservation {
    private final int confNo;
    private final VehicleType vehicleType;
    private final String cellPhone;


    public Reservation(int confNo, VehicleType vehicleType, String cellPhone) {
        this.confNo = confNo;
        this.vehicleType = vehicleType;
        this.cellPhone = cellPhone;
    }

    public int getConfNo() {
        return confNo;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public String getCellPhone() {
        return cellPhone;
    }
}
