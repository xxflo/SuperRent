package cpsc304.controller;

import cpsc304.database.DatabaseConnectionHandler;
import cpsc304.model.Branch;
import cpsc304.model.Vehicle;
import cpsc304.model.VehicleTypeName;
import cpsc304.util.BranchUtil;
import cpsc304.util.SceneSwitchUtil;
import cpsc304.util.TimeSpinnerUtil;
import cpsc304.util.TimeUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

public class VehicleListController implements Initializable {

    public Button btnSearch;
    public DatePicker startDate;
    public DatePicker endDate;
    public ComboBox<String> branchLocation;
    public ComboBox<String> vehicleType;
    public Label labelStatus;
    public Accordion resultAccordion;
    public Spinner startTime;
    public Spinner endTime;
    public Label labelError;
    private DatabaseConnectionHandler dbHandler =  DatabaseConnectionHandler.getInstance();
    private BranchUtil branchUtil= BranchUtil.getInstance();
    private SceneSwitchUtil sceneSwitchUtil = SceneSwitchUtil.getInstance();
    private Branch location;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Arrays.asList(VehicleTypeName.values()).forEach(item -> vehicleType.getItems().add(item.getName()));
        vehicleType.getItems().add("");
        vehicleType.getSelectionModel().select("");
        startTime.setValueFactory(TimeSpinnerUtil.getSpinnerFactory());
        endTime.setValueFactory(TimeSpinnerUtil.getSpinnerFactory());
        branchLocation.getItems().addAll(branchUtil.getAllBranchesAsStringArray());
        branchLocation.getSelectionModel().select(branchUtil.getDefaultBranchAsString());
    }

    public void handleSelectVehicleOptions() {
        String vType = vehicleType.getValue();
        location = BranchUtil.decodeBranchFromString(branchLocation.getValue());
        Timestamp startTimestamp = TimeUtil.getTimeStamp(startDate,startTime);
        Timestamp endTimestamp = TimeUtil.getTimeStamp(endDate, endTime);

        if ((startTimestamp != null && endTimestamp == null) ||
                (startTimestamp == null && endTimestamp != null) ||
                (startTimestamp != null && endTimestamp != null
                        && (startTimestamp.after(endTimestamp)))){
            labelError.setText("Please select valid date range and (optionally) time.");
        } else {
            labelError.setText("");
            ArrayList<Vehicle> vehicles = dbHandler.getVehiclesBasedOnOption(vType,location,startTimestamp,endTimestamp);

            HashMap<String,ArrayList<Vehicle>> vehicleMap = new HashMap<>();

            for(Vehicle v:vehicles){
                ArrayList<Vehicle> vehicleList = vehicleMap.get(v.getVtname());
                if (vehicleList != null && vehicleList.size() > 0) {
                    vehicleList.add(v);
                } else {
                    vehicleList = new ArrayList<>();
                    vehicleList.add(v);
                    vehicleMap.put(v.getVtname(),vehicleList);
                }
            }
            showVehicles(vehicleMap);
        }
    }

    /**
     * Show list of vehicles based on input options
     * @param vehicleMap
     */
    private void showVehicles(HashMap<String, ArrayList<Vehicle>> vehicleMap) {
        resultAccordion.getPanes().remove(0,resultAccordion.getPanes().size());
        ArrayList<TitledPane> panes = new ArrayList<>();
        for(String key : vehicleMap.keySet()){
            ArrayList<Vehicle> vehicles = vehicleMap.get(key);
            TitledPane newPane = new TitledPane();

            BorderPane borderPane = new BorderPane();
            Label title = new Label(key + " (" + vehicles.size() + ")");
            BorderPane.setAlignment(title, Pos.CENTER);
            title.setMinWidth(250.0);
            Button btnReserve = new Button("Reserve");

            borderPane.setLeft(title);
            borderPane.setRight(btnReserve);

            newPane.setGraphic(borderPane);
            newPane.setAnimated(false);
            newPane.setPrefHeight(32.0);

            ListView listView = new ListView();
            btnReserve.setOnAction((event)->{
                try {
                    switchToCustomerInfo(event, key);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            vehicles.forEach(vehicle -> {
                HBox vehicleHBox = new HBox();
                Label field = new Label(vehicle.toString());
                Label iconField = new Label(VehicleTypeName.getVehicleTypeName(key).getVehicleIcon());
                iconField.getStyleClass().add("test_font");
                iconField.setStyle("-fx-text-fill: " + vehicle.getColor());
                vehicleHBox.getChildren().add(field);
                vehicleHBox.getChildren().add(iconField);
                listView.getItems().add(vehicleHBox);
            });
            newPane.setContent(listView);
            panes.add(newPane);
        }
        resultAccordion.getPanes().addAll(panes);
    }

    private void switchToCustomerInfo(ActionEvent actionEvent, String vehicleType) throws IOException {
        FXMLLoader loader = sceneSwitchUtil.getLoaderForScene(SceneSwitchUtil.customerInfoFxml);
        Parent root = loader.load();

        CustomerInfoController customerInfoController = loader.getController();
        customerInfoController.setIntendedVehicleType(VehicleTypeName.getVehicleTypeName(vehicleType));
        customerInfoController.setIntendedDateTime(startDate.getValue(), endDate.getValue(), (LocalTime)startTime.getValue(), (LocalTime)endTime.getValue());
        customerInfoController.setNextView(SceneSwitchUtil.reservationFxml);
        customerInfoController.setIntendedBranch(location);

        sceneSwitchUtil.switchSceneTo(actionEvent,root);
    }

    public void handleGoBackMainPressed(ActionEvent actionEvent) throws IOException {
        sceneSwitchUtil.switchSceneTo(actionEvent, SceneSwitchUtil.loginFxml);
    }
}
