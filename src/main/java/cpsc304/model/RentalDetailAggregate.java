package cpsc304.model;

import java.util.ArrayList;
import java.util.List;

public class RentalDetailAggregate implements Aggregate {
    private final String vlicense;
    private final String make;
    private final String model;
    private final String year;
    private final String rentalCity;
    private final String rentalLocation;
    private final VehicleTypeName vehicleTypeName;
    private final String color;

    public RentalDetailAggregate(String rentalCity, String rentalLocation, VehicleTypeName vehicleTypeName, String vlicense, String make, String model, String year, String color) {
        this.rentalCity = rentalCity;
        this.rentalLocation = rentalLocation;
        this.vehicleTypeName = vehicleTypeName;
        this.vlicense = vlicense;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
    }

    private String getRentalCity() {
        return rentalCity;
    }

    private String getRentalLocation() {
        return rentalLocation;
    }

    private VehicleTypeName getVehicleTypeName() {
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

    @Override
    public List<String> getLabels() {
        List<String> labels = new ArrayList<>();
        if (getRentalCity() != null) {
            labels.add(getRentalCity());
        }
        if (getRentalLocation() != null) {
            labels.add(getRentalLocation());
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
        return null;
    }
}
