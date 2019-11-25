package ca.ubc.cs304.model;

/**
 * Store information about a single customer
 */
public class Customer {
    private final String license;
    private final String phoneNum;
    private final String address;
    private final String name;

    public Customer(String license, String phoneNum, String address, String name) {
        this.license = license;
        this.phoneNum = phoneNum;
        this.address = address;
        this.name = name;
    }

    public String getLicense() {
        return license;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return "(" + (phoneNum.isEmpty() ? "\'\', " : phoneNum + ", ")
                + (name.isEmpty() ? "\'\', " : name + ", ")
                + (address.isEmpty() ? "\'\', " : address + ", ")
                + (license.isEmpty() ? "\'\', " : license)
                + ")";
    }
}
