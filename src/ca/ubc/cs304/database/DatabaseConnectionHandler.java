package ca.ubc.cs304.database;

import ca.ubc.cs304.model.*;

import java.sql.*;
import java.util.ArrayList;


/**
 * This class handles all database related transactions
 */

public class DatabaseConnectionHandler {
    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";

    private Connection connection = null;

    public DatabaseConnectionHandler() {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public ArrayList<Vehicle> getVehiclesBasedOnOption() {
        ArrayList<Vehicle> result = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM vehicle");

            while(rs.next()) {
                Vehicle vehicle = new Vehicle(rs.getString("licensePlate"),
                        VehicleType.getVehicleType(rs.getString("vtname")),
                        VehicleStatus.getVehicleStatus(rs.getInt("status")),
                        rs.getString("cellPhone"));
                result.add(vehicle);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result;
    }

    public boolean insertReservation(Reservation reservation) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO reservation VALUES (?,?,?,?,?)");
            ps.setInt(1, reservation.getConfNo());
            //TODO: incomplete
            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return false;
    }

    public Customer getCustomer(String driverLicense) {
        Customer customer = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM customer WHERE dLicense = ?");
            ps.setString(1, driverLicense);
            ResultSet rs = ps.executeQuery();

            while(rs.next())
                    customer = new Customer(
                    rs.getString("licensePlate"),
                    rs.getString("cellPhone"),
                    rs.getString("name"));

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return customer;
    }

    public boolean insertCustomer(Customer customer) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO customer VALUES (?,?,?,?,?)");
            ps.setString(1, customer.getLicense());
            //TODO: complete all the fields to insert
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return false;
    }

    private void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

//    public boolean login() {
//        try {
//            if (connection != null) {
//                connection.close();
//            }
//
//            connection = DriverManager.getConnection(ORACLE_URL, "", "");
//            connection.setAutoCommit(false);
//
//            System.out.println("\nConnected to Oracle!");
//            return true;
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//            return false;
//        }
//    }
}
