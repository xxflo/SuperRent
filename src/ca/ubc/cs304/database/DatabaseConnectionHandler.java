package ca.ubc.cs304.database;

import ca.ubc.cs304.model.*;
import ca.ubc.cs304.util.LoginCreds;
import javafx.util.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


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
                        rs.getTimestamp("fromDateTime"),
                        rs.getTimestamp("toDateTime"),
                        new Branch(rs.getString("location"),rs.getString("city")));
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
                        rs.getString("CONFNO"),
                        rs.getTimestamp("FROMDATETIME"),
                        rs.getTimestamp("TODATETIME")
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
                        rs.getDouble("VALUE"),
                        rs.getTimestamp("return_dateTime")
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

    public ArrayList<Vehicle> getVehiclesBasedOnOption(String carType, Branch branch, Timestamp startDateTime, Timestamp endDateTime) {
        ArrayList<Vehicle> result = new ArrayList<>();
        String vTypeCrit = "", cityCrit="", locationCrit="", dateCrit="";
        String and = " AND ";
        try {
            Statement stmt = connection.createStatement();
            String mainQuery = "SELECT * FROM VEHICLE V WHERE";
            String subQuery = "NOT EXISTS (SELECT * FROM RENT R WHERE R.vlicense = V.vlicense";
            vTypeCrit = carType.isEmpty()? " " : " vtname = '" + carType + "'" + and;
            cityCrit = "city = '" + branch.getCity() + "'";
            locationCrit = "location = '" + branch.getLocation() + "'";
            if (startDateTime!=null && endDateTime!=null){
                String endTimeQuery = String.format("TO_TIMESTAMP('%1$s','YYYY-MM-DD hh24:mi:ss.ff')", endDateTime);
                String startTimeQuery = String.format("TO_TIMESTAMP('%1$s','YYYY-MM-DD hh24:mi:ss.ff')", startDateTime);
                dateCrit = String.format(and + "(%1$s > R.fromDateTime AND %2$s < R.toDateTime))", endTimeQuery, startTimeQuery);
            } else {
                subQuery += ")";
            }
            
            String sql = mainQuery + vTypeCrit + cityCrit + and +
                    locationCrit + and +
                    subQuery + dateCrit;
            System.out.println("SQL to query available vehicles based on criteria: " + sql);

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

    private void insertTimePeriodIfNotExist(Timestamp startTime, Timestamp endTime) {
        try {
            String sql_select = "SELECT * FROM TimePeriod WHERE fromDateTime = ? AND toDateTime = ?";

            PreparedStatement ps = connection.prepareStatement(sql_select);
            ps.setTimestamp(1, startTime);
            ps.setTimestamp(2, endTime);

            System.out.println("SQL for selecting TimePeriod with entry: " + sql_select);
            System.out.println("With parameters: " + startTime.toString() + ", " + endTime.toString());

            ResultSet rs = ps.executeQuery();
            if (!rs.next()){
                String sql_insert = "INSERT INTO TimePeriod VALUES (?,?)";
                PreparedStatement ps2 = connection.prepareStatement(sql_insert);
                ps2.setTimestamp(1, startTime);
                ps2.setTimestamp(2, endTime);
                ps2.executeQuery();
                ps2.close();
                System.out.println("SQL for inserting new TimePeriod into table: " + sql_insert);
                System.out.println("With parameters: " + startTime.toString() + ", " + endTime.toString());
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public boolean insertReservation(Reservation reservation) {
        try {
            insertTimePeriodIfNotExist(reservation.getStartTime(), reservation.getEndTime());
            String sql = "INSERT INTO Reservation VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, reservation.getConfNo());
            ps.setString(2, reservation.getVtname());
            ps.setString(3, reservation.getDriverLicense());
            ps.setTimestamp(4, reservation.getStartTime());
            ps.setTimestamp(5, reservation.getEndTime());
            ps.setString(6, reservation.getBranch().getLocation());
            ps.setString(7, reservation.getBranch().getCity());

            System.out.println("SQL for inserting new reservation into table: " + sql);
            System.out.println("With parameters: " + reservation.toString());

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

    public Customer getCustomer(String driverLicense) {
        Customer customer = null;
        try {
            String sql = String.format("SELECT * FROM customer WHERE dLicense = '%1$s'", driverLicense);
            PreparedStatement ps = connection.prepareStatement(sql);

            System.out.println("SQL for getting customer with license: " + sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                customer = new Customer(
                        rs.getString("dLicense"),
                        rs.getString("cellPhone"),
                        rs.getString("address"),
                        rs.getString("name"));
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
            String sql = "INSERT INTO customer VALUES (?,?,?,?)";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, customer.getPhoneNum());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getLicense());

            System.out.println("SQL for inserting new customer into table: " + sql);
            System.out.println("With Parameters: " + customer.toString());

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

    public List<RentalDetailAggregate> getDailyRentalAggregate(Date date, Branch branch) {
        try {
            String filterBranchClause = "";
            if (branch != null) {
                filterBranchClause = String.format("and v.location = '%1$s' and v.city = '%2$s' ", branch.getLocation(), branch.getCity());
            }
            String sql =
                    "SELECT v.location, v.city, v.vtname, v.vlicense, v.make, v.model, v.year, v.color " +
                            "FROM RENT r, VEHICLE v " +
                            "WHERE r.vlicense = v.vlicense " +
                            filterBranchClause +
                            String.format("and trunc(r.fromDateTime) = to_date('%s', 'YYYY-MM-DD') ", date) +
                            "ORDER BY v.city, v.location, v.vtname";

            PreparedStatement ps = connection.prepareStatement(sql);

            System.out.println("SQL for daily rental aggregate: " + sql);
            ResultSet rs = ps.executeQuery();

            List<RentalDetailAggregate> aggregates = new ArrayList<>();
            while (rs.next()) {
                String branchLocation = rs.getString("location");
                String branchCity = rs.getString("city");
                VehicleTypeName vtName = VehicleTypeName.getVehicleTypeName(rs.getString("vtname"));
                String vlicense = rs.getString("vlicense");
                String make = rs.getString("make");
                String model = rs.getString("model");
                String year = rs.getString("year");
                String color = rs.getString("color");
                aggregates.add(new RentalDetailAggregate(branchCity, branchLocation, vtName, vlicense, make, model, year, color));
            }

            return aggregates;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            return null;
        }
    }

    public List<RentalAggregate> getDailyRentalAggregateByVehicleType(Date date, Branch branch) {
        try {
            String sql =
                    "SELECT v.vtname, COUNT(r.rid) as rentCount " +
                            "FROM RENT r, VEHICLE v " +
                            "WHERE r.vlicense = v.vlicense " +
                            String.format("and trunc(r.fromDateTime) = to_date('%s', 'YYYY-MM-DD')", date);

            if (branch != null) {
                String branchClause = String.format("and v.location = '%1$s' and v.city = '%2$s'", branch.getLocation(), branch.getCity());
                sql = sql + " " + branchClause;
            }

            sql = sql + " " + "GROUP BY v.vtname";
            PreparedStatement ps = connection.prepareStatement(sql);

            System.out.println("SQL for daily rental aggregate by vehicle category: " + sql);
            ResultSet rs = ps.executeQuery();

            List<RentalAggregate> aggregates = new ArrayList<>();
            while (rs.next()) {
                VehicleTypeName vtName = VehicleTypeName.getVehicleTypeName(rs.getString("vtname"));
                int count = rs.getInt("rentcount");
                aggregates.add(new RentalAggregate(null, null, vtName, count));
            }

            return aggregates;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            return null;
        }
    }

    public List<RentalAggregate> getDailyRentalAggregateByBranch(Date date, Branch branch) {
        try {
            String sql =
                    "SELECT v.location, v.city, COUNT(r.rid) as rentCount " +
                            "FROM RENT r, VEHICLE v " +
                            "WHERE r.vlicense = v.vlicense " +
                            String.format("and trunc(r.fromDateTime) = to_date('%s', 'YYYY-MM-DD') ", date) +
                            "GROUP BY (v.location, v.city)";
            if (branch != null) {
                String havingClause = String.format("HAVING v.location = '%1$s' and v.city = '%2$s'", branch.getLocation(), branch.getCity());
                sql = sql + " " + havingClause;
            }

            PreparedStatement ps = connection.prepareStatement(sql);

            System.out.println("SQL for daily rental aggregate by branch: " + sql);
            ResultSet rs = ps.executeQuery();

            List<RentalAggregate> aggregates = new ArrayList<>();
            while (rs.next()) {
                String branchLocation = rs.getString("location");
                String branchCity = rs.getString("city");
                int count = rs.getInt("rentcount");
                aggregates.add(new RentalAggregate(branchCity, branchLocation, null, count));
            }
            return aggregates;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            return null;
        }
    }

    public int getDailyRentalCount(Date date, Branch branch) {
        try {
            String sql =
                    "SELECT COUNT(r.rid) as rentCount " +
                            "FROM RENT r, Vehicle v " +
                            String.format("WHERE trunc(r.fromDateTime) = to_date('%s', 'YYYY-MM-DD') ", date) +
                            String.format("and r.vlicense = v.vlicense");

            if (branch != null) {
                String branchClause = String.format("and v.city = '%1$s' and v.location = '%2$s'", branch.getCity(), branch.getLocation());
                sql = sql + " " + branchClause;
            }
            PreparedStatement ps = connection.prepareStatement(sql);

            System.out.println("SQL for daily rental aggregate by branch: " + sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int count = rs.getInt("rentcount");
                return count;
            }
            return 0;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            return 0;
        }
    }

    public List<ReturnDetailAggregate> getDailyReturnAggregate(Date date, Branch branch) {
        try {
            String filterBranchClause = "";
            if (branch != null) {
                filterBranchClause = String.format("and v.location = '%1$s' and v.city = '%2$s' ", branch.getLocation(), branch.getCity());
            }

            String sql =
                    "SELECT v.location, v.city, v.vtname, v.vlicense, v.make, v.model, v.year, v.color, ret.value " +
                            "FROM RENT rent, RETURN ret, VEHICLE v " +
                            "WHERE rent.vlicense = v.vlicense and rent.rid = ret.rid " +
                            filterBranchClause +
                            String.format("and trunc(ret.return_dateTime) = to_date('%s', 'YYYY-MM-DD') ", date) +
                            "ORDER BY v.city, v.location, v.vtname";

            PreparedStatement ps = connection.prepareStatement(sql);

            System.out.println("SQL for daily return aggregate: " + sql);
            ResultSet rs = ps.executeQuery();

            List<ReturnDetailAggregate> aggregates = new ArrayList<>();
            while (rs.next()) {
                String branchLocation = rs.getString("location");
                String branchCity = rs.getString("city");
                VehicleTypeName vtName = VehicleTypeName.getVehicleTypeName(rs.getString("vtname"));
                double value = rs.getDouble("value");
                String vlicense = rs.getString("vlicense");
                String make = rs.getString("make");
                String model = rs.getString("model");
                String year = rs.getString("year");
                String color = rs.getString("color");
                aggregates.add(new ReturnDetailAggregate(branchCity, branchLocation,  vtName, vlicense, make, model, year, color, value));
            }

            return aggregates;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            return null;
        }
    }

    public List<ReturnAggregate> getDailyReturnAggregateByCategory(Date date, Branch branch) {
        try {
            String sql =
                    "SELECT v.vtname, COUNT(ret.rid) as returnCount, SUM(ret.value) as totalValue " +
                            "FROM RENT rent, RETURN ret, VEHICLE v " +
                            "WHERE rent.vlicense = v.vlicense and rent.rid = ret.rid " +
                            String.format("and trunc(ret.return_dateTime) = to_date('%s', 'YYYY-MM-DD')", date);

            if (branch != null) {
                String branchClause = String.format("and v.location = '%1$s' and v.city = '%2$s'", branch.getLocation(), branch.getCity());
                sql = sql + " " + branchClause;
            }

            sql = sql + " " + "GROUP BY (v.vtname)";
            PreparedStatement ps = connection.prepareStatement(sql);

            System.out.println("SQL for daily return aggregate by vehicle category: " + sql);
            ResultSet rs = ps.executeQuery();

            List<ReturnAggregate> aggregates = new ArrayList<>();
            while (rs.next()) {
                VehicleTypeName vtName = VehicleTypeName.getVehicleTypeName(rs.getString("vtname"));
                int count = rs.getInt("returnCount");
                double value = rs.getDouble("totalValue");
                aggregates.add(new ReturnAggregate(null, null, vtName, count, value));
            }

            return aggregates;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            return null;
        }
    }

    public List<ReturnAggregate> getDailyReturnAggregateByBranch(Date date, Branch branch) {
        try {
            String sql =
                    "SELECT v.location, v.city, COUNT(ret.rid) as returnCount, SUM(ret.value) as totalValue " +
                            "FROM RENT rent, RETURN ret, VEHICLE v " +
                            "WHERE rent.vlicense = v.vlicense and rent.rid = ret.rid " +
                            String.format("and trunc(ret.return_dateTime) = to_date('%s', 'YYYY-MM-DD') ", date) +
                            "GROUP BY (v.location, v.city)";

            if (branch != null) {
                String havingClause = String.format("HAVING v.location = '%1$s' and v.city = '%2$s'", branch.getLocation(), branch.getCity());
                sql = sql + havingClause;
            }
            PreparedStatement ps = connection.prepareStatement(sql);

            System.out.println("SQL for daily return aggregate by branch: " + sql);
            ResultSet rs = ps.executeQuery();

            List<ReturnAggregate> aggregates = new ArrayList<>();
            while (rs.next()) {
                String branchLocation = rs.getString("location");
                String branchCity = rs.getString("city");
                int count = rs.getInt("returnCount");
                double value = rs.getDouble("totalValue");
                aggregates.add(new ReturnAggregate(branchCity, branchLocation, null, count, value));
            }

            return aggregates;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            return null;
        }
    }

    public double getDailyReturnValue(Date date, Branch branch) {
        try {
            String sql =
                    "SELECT SUM(ret.value) as returnValue " +
                            "FROM RETURN ret, RENT rent, Vehicle v " +
                            String.format("WHERE trunc(ret.return_dateTime) = to_date('%s', 'YYYY-MM-DD')", date) +
                            " and ret.rid = rent.rid";

            if (branch != null) {
                String branchClause = String.format("and v.city = '%1$s' and v.location = '%2$s'", branch.getCity(), branch.getLocation());
                sql = sql + " " + branchClause;
            }
            PreparedStatement ps = connection.prepareStatement(sql);

            System.out.println("SQL for daily rental aggregate by branch: " + sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                double count = rs.getDouble("returnValue");
                return count;
            }
            return 0;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            return 0;
        }
    }

    public Rental createRental(
            Branch location,
            VehicleTypeName vehicleTypeName,
            Timestamp startTime,
            Timestamp endTime,
            Customer customer,
            String creditCardName,
            String creditCardNumber,
            String expiryDate
    ) {
        try {
            connection.setAutoCommit(false);
            insertTimePeriodIfNotExist(startTime, endTime);
            String rid = UUID.randomUUID().toString();
            String endTimeQuery = String.format("TO_TIMESTAMP('%1$s','YYYY-MM-DD hh24:mi:ss.ff')", endTime);
            String startTimeQuery = String.format("TO_TIMESTAMP('%1$s','YYYY-MM-DD hh24:mi:ss.ff')", startTime);

            String sql = String.format(
                    "INSERT INTO RENT (rid, vlicense, dlicense, fromDateTime, toDateTime, odometer, cardName, cardNo, ExpDate) " +
                    "SELECT '%1$s', v.vlicense, '%2$s', %3$s, %4$s, v.odometer, '%5$s', '%6$s', '%7$s' " +
                    "FROM Vehicle v " +
                    "WHERE v.status = 'available' " +
                    "and v.vtname = '%8$s' and v.location = '%9$s' and v.city = '%10$s' " +
                    "and rownum = 1",
                    rid,
                    customer.getLicense(),
                    startTimeQuery,
                    endTimeQuery,
                    creditCardName,
                    creditCardNumber,
                    expiryDate,
                    vehicleTypeName.getName(),
                    location.getLocation(),
                    location.getCity());

            System.out.println("SQL for inserting new rental into table: " + sql);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();


            String fetchSql = String.format("SELECT * FROM RENT r WHERE r.rid = '%1$s'", rid);
            System.out.println("SQL for fetch return: " + fetchSql);
            ps = connection.prepareStatement(fetchSql);
            ps.executeQuery(fetchSql);
            ResultSet rs = ps.getResultSet();
            if (rs.next()) {
                Rental r = new Rental(
                        rs.getString("rid"),
                        rs.getString("vlicense"),
                        rs.getString("dlicense"),
                        rs.getInt("odometer"),
                        rs.getString("cardname"),
                        rs.getString("cardNo"),
                        rs.getString("expdate"),
                        rs.getString("confno"),
                        rs.getTimestamp("fromDateTime"),
                        rs.getTimestamp("toDateTime")
                );
                ps.close();

                String updateVehicleSql = String.format(
                        "UPDATE VEHICLE " +
                                "SET status = 'rented' " +
                                "WHERE vlicense = '%1$s'",
                        r.getVlicense());

                System.out.println(String.format("Query to update vehicle: %s", updateVehicleSql));
                ps = connection.prepareStatement(updateVehicleSql);
                ps.executeUpdate();
                connection.commit();
                return r;
            }
            ps.close();
            return null;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            return null;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Rental getRental(
            String vlicense,
            String dlicense
    ) {
        try {
            String sql = String.format("SELECT * FROM Rent r WHERE r.vlicense = %1$s and r.dlicense = %2$s ORDER BY r.fromDateTime DESC", vlicense, dlicense);
            System.out.println(String.format("SQL for fetching rental: %1$s", sql));

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();

            if (rs.next()) {
                return new Rental(
                        rs.getString("rid"),
                        rs.getString("vlicense"),
                        rs.getString("dlicense"),
                        rs.getInt("odometer"),
                        rs.getString("cardName"),
                        rs.getString("cardNo"),
                        rs.getString("expDate"),
                        rs.getString("confNo"),
                        rs.getTimestamp("fromDateTime"),
                        rs.getTimestamp("toDateTime")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            return null;
        }
    }

    public Pair<CostSummary, VehicleType> getCostSummary(
            String rid,
            int returnOdometer,
            Timestamp returnTime
    ) {
        try {
            String returnTimeQuery = String.format("TO_TIMESTAMP('%1$s','YYYY-MM-DD hh24:mi:ss.ff')", returnTime);
            String sql = String.format(
                    "WITH returnSummary as (SELECT v.vtname as vtname, " +
                            "EXTRACT(hour from %1$s - r.fromDateTime) as hoursBetween, " +
                            "FLOOR(EXTRACT(day from %1$s - r.fromDateTime) / 7) as weeksBetween, " +
                            "EXTRACT(day from %1$s - r.fromDateTime) - " +
                            "FLOOR(EXTRACT(day from %1$s - r.fromDateTime) / 7) * 7 as daysBetween, " +
                            "(%2$s - r.odometer) as odometerDifference " +
                            "FROM Rent r, Vehicle v " +
                            "WHERE r.rid = '%3$s' and r.vlicense = v.vlicense) " +
                            "SELECT hoursBetween, weeksBetween, daysBetween, odometerDifference, " +
                            "krate, hrate, drate, wrate, hirate, dirate, wirate, vt.vtname, vt.features, " +
                            "(CASE WHEN odometerDifference > 2000 then (odometerDifference - 2000) * vt.krate ELSE 0 END) as kmPrice, " +
                            "(CASE WHEN odometerDifference > 2000 then (odometerDifference - 2000) * vt.krate ELSE 0 END) + hoursBetween * hrate + hoursBetween * hirate + daysBetween * drate + daysBetween * dirate + weeksBetween * wrate + weeksBetween * wirate as returnValue " +
                            "FROM VehicleType vt, returnSummary rs " +
                            "WHERE vt.vtname = rs.vtname",
                    returnTimeQuery, returnOdometer, rid);
            System.out.println(String.format("SQL for generating cost for return: %s", sql));
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeQuery();

            ResultSet rs = ps.getResultSet();
            if (rs.next()) {
                CostSummary c = new CostSummary(
                        rs.getLong("daysBetween"),
                        rs.getLong("hoursBetween"),
                        rs.getLong("weeksBetween"),
                        rs.getLong("odometerDifference"),
                        rs.getLong("returnValue"),
                        rs.getLong("kmPrice")
                );

                VehicleType vt = new VehicleType(
                        rs.getString("vtname"),
                        rs.getString("features"),
                        rs.getDouble("wrate"),
                        rs.getDouble("drate"),
                        rs.getDouble("hrate"),
                        rs.getDouble("wirate"),
                        rs.getDouble("dirate"),
                        rs.getDouble("hirate"),
                        rs.getDouble("krate")
                );
                return new Pair(c, vt);
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            return null;
        }
    }

    public Return createReturn(
            long odometer,
            boolean isGasFull,
            Double value,
            Rental rental,
            Timestamp returnTime
    ) {
        try {
            connection.setAutoCommit(false);
            String gasFullSql = (isGasFull) ? "1" : "0";
            String returnTimeQuery = String.format("TO_TIMESTAMP('%1$s','YYYY-MM-DD hh24:mi:ss.ff')", returnTime);
            String insertReturnSql = String.format("INSERT INTO RETURN VALUES('%1$s', %2$s, %3$s, '%4$s', %5$s)",
            rental.getRid(), returnTimeQuery, odometer, gasFullSql, value);
            PreparedStatement ps = connection.prepareStatement(insertReturnSql);
            System.out.println(String.format("Query to insert rental: %s", insertReturnSql));
            ps.executeUpdate();
            String updateVehicleSql = String.format(
                    "UPDATE VEHICLE " +
                    "SET odometer = %1$s, " +
                            "status = 'available' " +
                            "WHERE vlicense = '%2$s'",
                    odometer, rental.getVlicense());

            System.out.println(String.format("Query to update vehicle: %s", updateVehicleSql));
            ps = connection.prepareStatement(updateVehicleSql);
            ps.executeUpdate();
            connection.commit();

            System.out.println(String.format("Query to fetch return: %s", updateVehicleSql));
            String fetchReturnSql = String.format("SELECT * FROM RETURN WHERE rid = '%1$s'", rental.getRid());
            ps = connection.prepareStatement(fetchReturnSql);
            ps.executeQuery();

            ResultSet rs = ps.getResultSet();

            if (rs.next()) {
                return new Return(
                        rs.getString("rid"),
                        rs.getInt("odometer"),
                        rs.getBoolean("fullTank"),
                        rs.getDouble("value"),
                        rs.getTimestamp("return_dateTime")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            return null;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
