package cpsc304.controller;

import cpsc304.database.DatabaseConnectionHandler;
import cpsc304.util.SceneSwitchUtil;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    private DatabaseConnectionHandler handler = DatabaseConnectionHandler.getInstance();
    private SceneSwitchUtil sceneSwitchUtil = SceneSwitchUtil.getInstance();

    public ComboBox<String> tableViewBox;

    public AnchorPane viewPane;
    public TableView tableView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewBox.getItems().addAll(
                "CUSTOMER",
                "RESERVATION",
                "RENT",
                "VEHICLE",
                "BRANCH",
                "RETURN",
                "VEHICLETYPE"
        );
    }


    public void handleTableViewSelection(ActionEvent actionEvent) {
        switch (tableViewBox.getValue()) {
            case "CUSTOMER":
                updateTable(handler.getAllCustomers());
                break;
            case "RESERVATION":
                updateTable(handler.getAllReservations());
                break;
            case "RENT":
                updateTable(handler.getAllRentals());
                break;
            case "VEHICLE":
                updateTable(handler.getAllVehicles());
                break;
            case "BRANCH":
                updateTable(handler.getAllBranches());
                break;
            case "RETURN":
                updateTable(handler.getAllReturns());
                break;
            case "VEHICLETYPE":
                updateTable(handler.getAllVehicleTypes());
                break;
        }
    }

    private <T> void updateTable(List<T> entities) {
        setTable(generateColumns(entities), entities);
    }

    private <T> List<TableColumn<String, T>> generateColumns(List<T> entity) {
        if (entity.isEmpty()) {
            return new ArrayList<>();
        }
        Field[] fields = entity.get(0).getClass().getDeclaredFields();
        List<TableColumn<String, T>> columns = new ArrayList<>();
        for (Field f : fields) {
            if (Modifier.isFinal(f.getModifiers())) {
                TableColumn<String, T> column = new TableColumn<>(f.getName().toUpperCase());
                column.setCellValueFactory(new PropertyValueFactory<>(f.getName()));
                columns.add(column);
            }
        }
        return columns;
    }

    private <T> void setTable(List<TableColumn<String, T>> columns, List<T> items) {
        tableView.getColumns().clear();
        tableView.getItems().clear();

        tableView.getItems().addAll(items);
        tableView.getColumns().addAll(columns);
    }

    public void handleGoBackMainPressed(ActionEvent actionEvent) throws IOException {
        sceneSwitchUtil.switchSceneTo(actionEvent, SceneSwitchUtil.loginFxml);
    }
}
