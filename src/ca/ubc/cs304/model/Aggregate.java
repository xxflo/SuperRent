package ca.ubc.cs304.model;

import java.util.List;

public interface Aggregate {
    List<String> getLabels();
    Integer getCount();
    Double getTotal();
}
