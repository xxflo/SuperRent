package ca.ubc.cs304.model;

import java.util.ArrayList;

public enum Branch {
    VANCOUVER("123 Maple Street", "Vancouver"),
    RICHMOND("567 No.3 Road", "Richmond");

    public final String location;
    public final String city;

    Branch(String location, String city) {
        this.location = location;
        this.city = city;
    }
}
