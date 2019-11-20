package ca.ubc.cs304.database;

import ca.ubc.cs304.model.*;

import java.sql.*;
import java.util.ArrayList;


/**
 * This class handles all database related transactions
 */

public class DatabaseConnectionHandler {
    private static DatabaseConnectionHandler dbhandler;
    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";
    private Connection connection = null;

    private DatabaseConnectionHandler() {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    /**
     * Return singleton instance of the printer.
     * @return DictPrinter instance.
     */
    public static DatabaseConnectionHandler getInstance() {
        if (dbhandler == null) {
            dbhandler = new DatabaseConnectionHandler();
        }
        return dbhandler;
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
                        VehicleStatus.getVehicleStatus(rs.getInt("status")));
                result.add(vehicle);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result;
    }

    // TODO: figure out how to do date comparision; try in Oracle
    public ArrayList<Vehicle> getVehiclesBasedOnOption(String carType, String location, String date) {
        ArrayList<Vehicle> result = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            String queryMain = "SELECT * FROM vehicle";
            String where = (!carType.isEmpty()||!location.isEmpty()||!date.isEmpty())? " WHERE " : "";
            String vTypeCrit = carType.isEmpty()? "" : "vtname = " + carType;
            String locCrit = location.isEmpty()? "" : "vtname = " + location;
            //TODO: fix date
            String dateCrit = date.isEmpty()? "" : "date = " + date;

            ResultSet rs = stmt.executeQuery(queryMain + where + vTypeCrit + locCrit + dateCrit);
            while(rs.next()) {
                Vehicle vehicle = new Vehicle(rs.getString("licensePlate"),
                        VehicleType.getVehicleType(rs.getString("vtname")),
                        VehicleStatus.getVehicleStatus(rs.getInt("status")));
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
            PreparedStatement ps = connection.prepareStatement("INSERT INTO reservation VALUES (?,?,?)");

            ps.setInt(1, reservation.getConfNo());
            ps.setString(2, reservation.getVehicleType().getValue());
            ps.setString(3, reservation.getDriverLicense());
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

            while(rs.next()){
                customer = new Customer(
                        rs.getString("cellPhone"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("dLicense"));
            }
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
            ps.setString(1, customer.getPhoneNum());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getLicense());
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
