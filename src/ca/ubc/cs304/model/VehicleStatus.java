package ca.ubc.cs304.model;

public enum VehicleStatus {
    RENTED("rented"),
    MAINTENANCE("maintenence_shop"),
    AVAILABLE("available");

    private String status;

    VehicleStatus(String status) {
        status = status;
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
