package cpsc304.model;

import java.util.ArrayList;
import java.util.List;

public class ReturnAggregate implements Aggregate {
    private final String returnCity;
    private final String returnLocation;
    private final VehicleTypeName vehicleTypeName;
    private final int count;
    private final double total;

    public ReturnAggregate(String returnCity, String returnLocation, VehicleTypeName vehicleTypeName, int count, double total) {
        this.returnCity = returnCity;
        this.returnLocation = returnLocation;
        this.vehicleTypeName = vehicleTypeName;
        this.count = count;
        this.total = total;
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
        return labels;
    }

    @Override
    public Integer getCount() {
        return Integer.valueOf(count);
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
}
