package ca.ubc.cs304.database;

import ca.ubc.cs304.model.*;
import ca.ubc.cs304.util.LoginCreds;

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

    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> result = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM CUSTOMER");
            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getString("dlicense"),
                        rs.getString("cellphone"),
                        rs.getString("address"),
                        rs.getString("name")
                );
                result.add(customer);
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result;
    }

    public ArrayList<Reservation> getAllReservations() {
        ArrayList<Reservation> result = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM RESERVATION");
            while (rs.next()) {
                Reservation reservation = new Reservation(
                        rs.getString("confno"),
                        rs.getString("vtname"),
                        rs.getString("dlicense"),
                        Timestamp.valueOf(rs.getString("fromDateTime")),
                        Timestamp.valueOf(rs.getString("toDateTime")));
                result.add(reservation);
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result;
    }

    public ArrayList<Rental> getAllRentals() {
        ArrayList<Rental> result = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM RENT");
            while (rs.next()) {
                Rental rental = new Rental(
                        rs.getString("RID"),
                        rs.getString("VLICENSE"),
                        rs.getString("DLICENSE"),
                        rs.getInt("ODOMETER"),
                        rs.getString("CARDNAME"),
                        rs.getString("CARDNO"),
                        rs.getString("EXPDATE"),
                        rs.getString("CONFNO")
                );
                result.add(rental);
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result;
    }

    public ArrayList<Vehicle> getAllVehicles() {
        ArrayList<Vehicle> result = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM VEHICLE");
            while (rs.next()) {
                Vehicle vehicle = new Vehicle(
                        rs.getString("VLICENSE"),
                        rs.getString("MAKE"),
                        rs.getString("MODEL"),
                        rs.getString("YEAR"),
                        rs.getString("COLOR"),
                        rs.getInt("ODOMETER"),
                        rs.getString("LOCATION"),
                        rs.getString("CITY"),
                        rs.getString("VTNAME"),
                        VehicleStatus.getVehicleStatus(rs.getString("STATUS"))
                );
                result.add(vehicle);
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result;
    }

    public ArrayList<Branch> getAllBranches() {
        ArrayList<Branch> result = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM BRANCH");
            while (rs.next()) {
                Branch branch = new Branch(
                        rs.getString("LOCATION"),
                        rs.getString("CITY")
                );
                result.add(branch);
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result;
    }

    public ArrayList<Return> getAllReturns() {
        ArrayList<Return> result = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM RETURN");
            while (rs.next()) {
                Return branch = new Return(
                        rs.getString("RID"),
                        rs.getInt("ODOMETER"),
                        rs.getBoolean("FULLTANK"),
                        rs.getDouble("VALUE")
                );
                result.add(branch);
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result;
    }

    public ArrayList<VehicleType> getAllVehicleTypes() {
        ArrayList<VehicleType> result = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM VEHICLETYPE");
            while (rs.next()) {
                VehicleType vt = new VehicleType(
                        rs.getString("VTNAME"),
                        rs.getString("FEATURES"),
                        rs.getDouble("WRATE"),
                        rs.getDouble("DRATE"),
                        rs.getDouble("HRATE"),
                        rs.getDouble("WIRATE"),
                        rs.getDouble("DIRATE"),
                        rs.getDouble("HIRATE"),
                        rs.getDouble("KRATE")
                );
                result.add(vt);
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result;
    }

    // TODO: Fix Location and Time Filter
    public ArrayList<Vehicle> getVehiclesBasedOnOption(String carType, Branch branch, Timestamp startDateTime, Timestamp endDateTime) {
        ArrayList<Vehicle> result = new ArrayList<>();
        String vTypeCrit = "", cityCrit="", locationCrit="";
        String and = " AND ";
        try {
            Statement stmt = connection.createStatement();
            String queryMain = "SELECT * FROM Vehicle LEFT JOIN Rent ON Vehicle.vlicense = Rent.vlicense WHERE";
            vTypeCrit = carType.isEmpty()? " " : "vtname = " + carType + and + "";
            cityCrit = "city = '" + branch.getCity() + "'";
            locationCrit = "location = '" + branch.getLocation() + "'";
            String dateCrit = "(fromDateTime = null AND toDateTime = null)";
            String dateCriteExtra = " OR (toDateTime < '" + startDateTime + "')";
            String sql = queryMain + vTypeCrit + cityCrit + and + locationCrit;

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Vehicle vehicle = new Vehicle(
                        rs.getString("VLICENSE"),
                        rs.getString("MAKE"),
                        rs.getString("MODEL"),
                        rs.getString("YEAR"),
                        rs.getString("COLOR"),
                        rs.getInt("ODOMETER"),
                        rs.getString("LOCATION"),
                        rs.getString("CITY"),
                        rs.getString("VTNAME"),
                        VehicleStatus.getVehicleStatus(rs.getString("STATUS"))
                );
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

            ps.setString(1, reservation.getConfNo());
            ps.setString(2, reservation.getVtname());
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
            PreparedStatement ps = connection.prepareStatement("INSERT INTO customer VALUES (?,?,?,?)");
            ps.setString(1, customer.getPhoneNum());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getLicense());
            ps.executeUpdate();
            connection.commit();
            ps.close();
            return true;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            return false;
        }
    }

    private void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public boolean login() {
        try {
            if (connection != null) {
                connection.close();
            }

            connection = DriverManager.getConnection(ORACLE_URL, LoginCreds.userName, LoginCreds.passWord);
            connection.setAutoCommit(false);

            System.out.println("\nConnected to Oracle!");
            return true;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return false;
        }
    }
}
