package ca.ubc.cs304.model;

import java.util.ArrayList;
import java.util.List;

public class ReturnDetailAggregate implements Aggregate {
    private final String returnCity;
    private final String returnLocation;
    private final VehicleTypeName vehicleTypeName;
    private final double total;
    private final String vlicense;
    private final String make;
    private final String model;
    private final String year;
    private final String color;

    public ReturnDetailAggregate(String returnCity, String returnLocation, VehicleTypeName vehicleTypeName, String vlicense, String make, String model, String year, String color, double total) {
        this.returnCity = returnCity;
        this.returnLocation = returnLocation;
        this.vehicleTypeName = vehicleTypeName;
        this.total = total;
        this.vlicense = vlicense;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
    }

    @Override
    public List<String> getLabels() {
        List<String> labels = new ArrayList<>();
        if (getReturnCity() != null) {
            labels.add(getReturnCity());
        }
        if (getReturnLocation() != null) {
            labels.add(getReturnLocation());
        }
        if (getVehicleTypeName() != null) {
            labels.add(getVehicleTypeName().toString());
        }
        if (getVlicense() != null) {
            labels.add(getVlicense());
        }
        if (getVMake() != null) {
            labels.add(getVMake());
        }
        if (getVModel() != null) {
            labels.add(getVModel());
        }
        if (getVYear() != null) {
            labels.add(getVYear());
        }
        if (getColor() != null) {
            labels.add(getColor());
        }
        return labels;
    }

    @Override
    public Integer getCount() {
        return null;
    }

    @Override
    public Double getTotal() {
        return Double.valueOf(total);
    }


    public String getReturnCity() {
        return returnCity;
    }

    public String getReturnLocation() {
        return returnLocation;
    }

    public VehicleTypeName getVehicleTypeName() {
        return vehicleTypeName;
    }

    private String getVlicense() {
        return vlicense;
    }

    private String getVMake() {
        return make;
    }

    private String getVModel() {
        return model;
    }

    private String getVYear() {
        return year;
    }

    private String getColor() {return color;}
}
