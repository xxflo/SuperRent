package ca.ubc.cs304.model;

public class CostSummary {
    private final long daysBetween;
    private final long hoursBetween;
    private final long weeksBetween;
    private final long odometerDifference;
    private final long value;
    private final long kmPrice;


    public CostSummary(long daysBetween, long hoursBetween, long weeksBetween, long odometerDifference, long value, long kmPrice) {
        this.daysBetween = daysBetween;
        this.hoursBetween = hoursBetween;
        this.weeksBetween = weeksBetween;
        this.odometerDifference = odometerDifference;
        this.kmPrice = kmPrice;
        this.value = value;
    }

    public long getDaysBetween() {
        return daysBetween;
    }

    public long getHoursBetween() {
        return hoursBetween;
    }

    public long getWeeksBetween() {
        return weeksBetween;
    }

    public long getValue() {
        return value;
    }

    public long getOdometerDifference() {
        return odometerDifference;
    }

    public long getKmPrice() {
        return kmPrice;
    }
}
