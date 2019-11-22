package ca.ubc.cs304.model;

public enum VehicleStatus {
    RENTED(0),
    MAINTENANCE(1),
    AVAILABLE(2);

    private int code;

    VehicleStatus(int code) {
        this.code = code;
    }
    public static VehicleStatus getVehicleStatus(String code) {
        switch (code) {
            case "rented":
                return RENTED;
            case "maintenance":
                return MAINTENANCE;
            case "available":
                return AVAILABLE;
            default:
                return null;
        }
    }
}
