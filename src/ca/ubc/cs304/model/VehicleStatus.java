package ca.ubc.cs304.model;

public enum VehicleStatus {
    RENTED(0),
    MAINTENANCE(1),
    AVAILABLE(2);

    private int code;

    VehicleStatus(int code) {
        this.code = code;
    }
    public static VehicleStatus getVehicleStatus(int code) {
        switch (code) {
            case 0:
                return RENTED;
            case 1:
                return MAINTENANCE;
            case 2:
                return AVAILABLE;
            default:
                return null;
        }
    }
}
