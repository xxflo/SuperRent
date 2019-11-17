package ca.ubc.cs304.model;

/**
 * Store information about a single customer
 */
public class Customer {
    private final String license;
    private final String phoneNum;
    private final String name;

    public Customer(String license, String phoneNum, String name) {
        this.license = license;
        this.phoneNum = phoneNum;
        this.name = name;
    }

    public String getLicense() {
        return license;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getName() {
        return name;
    }
}
