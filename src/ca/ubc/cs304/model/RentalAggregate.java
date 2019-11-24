package ca.ubc.cs304.model;

import java.util.ArrayList;
import java.util.List;

public class RentalAggregate implements Aggregate {
    private final String rentalCity;
    private final String rentalLocation;
    private final VehicleTypeName vehicleTypeName;
    private final int count;

    public RentalAggregate(String rentalCity, String rentalLocation, VehicleTypeName vehicleTypeName, int count) {
        this.rentalCity = rentalCity;
        this.rentalLocation = rentalLocation;
        this.vehicleTypeName = vehicleTypeName;
        this.count = count;
    }

    public String getRentalCity() {
        return rentalCity;
    }

    public String getRentalLocation() {
        return rentalLocation;
    }

    public VehicleTypeName getVehicleTypeName() {
        return vehicleTypeName;
    }

    public int getRentalCount() {
        return count;
    }

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
        return labels;
    }

    @Override
    public Integer getCount() {
        return Integer.valueOf(count);
    }

    @Override
    public Double getTotal() {
        return null;
    }
}
