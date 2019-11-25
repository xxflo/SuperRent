package cpsc304.model;

/**
 * Enumeration of vehicle type
 */
public class VehicleType {
    private final String vtname;
    private final String features;
    private final double wrate;
    private final double drate;
    private final double hrate;
    private final double wirate;
    private final double hirate;
    private final double dirate;
    private final double krate;

    public VehicleType(String vtname, String features, double wrate, double drate, double hrate, double wirate, double dirate, double hirate, double krate) {
        this.vtname = vtname;
        this.features = features;
        this.wrate = wrate;
        this.drate = drate;
        this.hrate = hrate;
        this.wirate = wirate;
        this.hirate = hirate;
        this.dirate = dirate;
        this.krate = krate;
    }

    public String getVtname() {
        return vtname;
    }

    public String getFeatures() {
        return features;
    }

    public double getWrate() {
        return wrate;
    }

    public double getDrate() {
        return drate;
    }

    public double getHrate() {
        return hrate;
    }

    public double getWirate() {
        return wirate;
    }

    public double getHirate() {
        return hirate;
    }

    public double getKrate() {
        return krate;
    }

    public double getDirate() {
        return dirate;
    }
}
