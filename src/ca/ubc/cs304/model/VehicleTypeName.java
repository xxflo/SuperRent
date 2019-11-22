package ca.ubc.cs304.model;

public enum VehicleTypeName {
    ECONOMY("Economy"),
    COMPACT("Compact"),
    MID_SIZE("Mid-size"),
    STANDARD("Standard"),
    FULL_SIZE("Full-size"),
    SUV("SUV"),
    TRUCK("Truck");

    private String name;

    VehicleTypeName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static VehicleTypeName getVehicleTypeName(String type) {
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
