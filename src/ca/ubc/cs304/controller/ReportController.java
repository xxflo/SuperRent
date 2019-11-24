package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.*;
import ca.ubc.cs304.util.BranchUtil;
import ca.ubc.cs304.util.TimeUtil;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReportController implements Initializable {

    public ComboBox<String> branchLocation;
    public DatePicker datePicker;
    public Button dailyRentalAllBranch;
    public Button dailyRentalOneBranch;
    public Button dailyReturnAllBranch;
    public Button dailyReturnOneBranch;
    public ListView<Aggregate> aggregateList;
    public ListView<Aggregate> perCategoryList;
    public ListView<Aggregate> perBranchList;
    public Text totalText;
    public Text perCategoryHeader;
    public Text perBranchHeader;
    public Text errorText;

    private DatabaseConnectionHandler handler;
    private static final String RENT_TOTAL_TEXT = "Total Rentals: %s";
    private static final String RENT_PER_VEHICLE_TEXT = "Rentals per Category";
    private static final String RENT_PER_BRANCH_TEXT = "Rentals per Branch";
    private static final String RETURN_TOTAL_TEXT = "Total Revenue: %s";
    private static final String RETURN_PER_VEHICLE_TEXT = "Subtotal Per Category";
    private static final String RETURN_PER_BRANCH_TEXT = "Subtotal Per Branch";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        branchLocation.getItems().addAll(BranchUtil.branchesToStringArray());
        datePicker.setValue(LocalDate.now());
        handler = DatabaseConnectionHandler.getInstance();

        Callback<ListView<Aggregate>, ListCell<Aggregate>> factory = param -> new ListCell<Aggregate>() {
            @Override
            protected void updateItem(Aggregate item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    List<String> labels = new ArrayList<>();
                    labels.addAll(item.getLabels());
                    if (item.getCount() != null) {
                        labels.add(String.valueOf(item.getCount()));
                    }
                    if (item.getTotal() != null) {
                        labels.add(String.valueOf(item.getTotal()));
                    }
                    setText(String.join("  |  ", labels));
                }
            }
        };


        aggregateList.setCellFactory(factory);
        perCategoryList.setCellFactory(factory);
        perBranchList.setCellFactory(factory);
    }

    public void handlePress(ActionEvent event) {
        Date sqlDate = TimeUtil.getTimeStampDate(datePicker);
        if (event.getSource() == dailyRentalAllBranch) {
            fetchRentalReport(sqlDate, null);
        } else if (event.getSource() == dailyReturnAllBranch) {
            fetchReturnReport(sqlDate, null);
        } else if (event.getSource() == dailyRentalOneBranch) {
            Branch b = BranchUtil.decodeBranchFromString(branchLocation.getValue());
            if (b == null) {
                showError("You must select a branch");
                return;
            }
            fetchRentalReport(sqlDate, b);
        } else if (event.getSource() == dailyReturnOneBranch) {
            Branch b = BranchUtil.decodeBranchFromString(branchLocation.getValue());
            if (b == null) {
                showError("You must select a branch");
                return;
            }
            fetchReturnReport(sqlDate, b);
        }
    }

    private void fetchReturnReport(Date date, Branch branch) {
        setReturnLabels();
        clearLists();
        List<ReturnAggregate> aggregates = handler.getDailyReturnAggregate(date, branch);
        aggregateList.getItems().addAll(aggregates);

        List<ReturnAggregate> perCategoryAggregates = handler.getDailyReturnAggregateByCategory(date, branch);
        perCategoryList.getItems().addAll(perCategoryAggregates);

        if (branch == null) {
            List<ReturnAggregate> perBranchAggregates = handler.getDailyReturnAggregateByBranch(date, null);
            perBranchList.getItems().addAll(perBranchAggregates);
        } else {
            perBranchHeader.setVisible(false);
            perBranchList.setVisible(false);
        }

        double totalCount = handler.getDailyReturnValue(date, branch);
        totalText.setText(String.format(RETURN_TOTAL_TEXT, String.valueOf(totalCount)));
    }

    private void fetchRentalReport(Date date, Branch branch) {
        setRentLabels();
        clearLists();
        List<RentalAggregate> aggregates = handler.getDailyRentalAggregate(date, branch);
        aggregateList.getItems().addAll(aggregates);

        List<RentalAggregate> perCategoryAggregates = handler.getDailyRentalAggregateByVehicleType(date, branch);
        perCategoryList.getItems().addAll(perCategoryAggregates);

        if (branch == null) {
            List<RentalAggregate> perBranchAggregates = handler.getDailyRentalAggregateByBranch(date, null);
            perBranchList.getItems().addAll(perBranchAggregates);
        } else {
            perBranchHeader.setVisible(false);
            perBranchList.setVisible(false);
        }

        int totalCount = handler.getDailyRentalCount(date, branch);
        totalText.setText(String.format(RENT_TOTAL_TEXT, String.valueOf(totalCount)));
    }

    private void clearLists() {
        errorText.setVisible(false);
        aggregateList.setVisible(true);
        perCategoryList.setVisible(true);
        perBranchList.setVisible(true);
        aggregateList.getItems().clear();
        perCategoryList.getItems().clear();
        perBranchList.getItems().clear();
    }

    private void setReturnLabels() {
        totalText.setVisible(true);
        perCategoryHeader.setVisible(true);
        perBranchHeader.setVisible(true);
        perCategoryHeader.setText(RETURN_PER_VEHICLE_TEXT);
        perBranchHeader.setText(RETURN_PER_BRANCH_TEXT);
    }

    private void setRentLabels() {
        totalText.setVisible(true);
        perCategoryHeader.setVisible(true);
        perBranchHeader.setVisible(true);
        perCategoryHeader.setText(RENT_PER_VEHICLE_TEXT);
        perBranchHeader.setText(RENT_PER_BRANCH_TEXT);
    }

    private void showError(String message) {
        aggregateList.setVisible(false);
        perCategoryList.setVisible(false);
        perBranchList.setVisible(false);

        perCategoryHeader.setVisible(false);
        perBranchHeader.setVisible(false);
        totalText.setVisible(false);

        errorText.setVisible(true);
        errorText.setText(message);
    }
}
