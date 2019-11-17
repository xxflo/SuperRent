package ca.ubc.cs304.model;

/**
 * Enumeration of vehicle type
 */
public enum VehicleType {
    ECONOMY(1, "Economy", 1, 2, 3, 4, 5, 6),
    COMPACT(2, "Compact", 1, 2, 3, 4, 5, 6),
    MID_SIZE(3, "Mid-size", 1, 2, 3, 4, 5, 6),
    STANDARD(4, "Standard", 1, 2, 3, 4, 5, 6),
    FULL_SIZE(5,"Full-size", 1, 2, 3, 4, 5, 6),
    SUV(6, "SUV", 1, 2, 3, 4, 5, 6),
    TRUCK(7, "Truck",1, 2, 3, 4, 5, 6);

    private int code;
    private final String value;
    public final int wrate;
    public final int drate;
    public final int hrate;
    public final int wirate;
    public final int hirate;
    public final int krate;

    VehicleType(int code, String value, int wrate, int drate, int hrate, int wirate, int hirate, int krate) {
        this.code = code;
        this.value = value;
        this.wrate = wrate;
        this.drate = drate;
        this.hrate = hrate;
        this.wirate = wirate;
        this.hirate = hirate;
        this.krate = krate;
    }

    public static VehicleType getVehicleType(String type) {
        switch (type) {
            case "Economy":
                return ECONOMY;
            case "Compact":
                return COMPACT;
            case "Mid-size":
                return MID_SIZE;
            case "Standard":
                return STANDARD;
            case "Full-size":
                return FULL_SIZE;
            case "SUV":
                return SUV;
            case "Truck":
                return TRUCK;
            default:
                return null;
        }
    }
}
