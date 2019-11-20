package ca.ubc.cs304.model;

/**
 * Enumeration of vehicle type
 */
public enum VehicleType {

    ECONOMY("Economy"),
    COMPACT("Compact"),
    MID_SIZE("Mid-size"),
    STANDARD("Standard"),
    FULL_SIZE("Full-size"),
    SUV("SUV"),
    TRUCK("Truck");

    private final String value;

    VehicleType(String value) {
        this.value = value;
    }

    public String getValue(){return this.value;}

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

//    public final int wrate;
//    public final int drate;
//    public final int hrate;
//    public final int wirate;
//    public final int hirate;
//    public final int krate;
//
//    VehicleType(int code, String value, int wrate, int drate, int hrate, int wirate, int hirate, int krate) {
//        this.code = code;
//        this.value = value;
//        this.wrate = wrate;
//        this.drate = drate;
//        this.hrate = hrate;
//        this.wirate = wirate;
//        this.hirate = hirate;
//        this.krate = krate;
//    }
}
