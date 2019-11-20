package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.Vehicle;
import ca.ubc.cs304.model.VehicleType;
import ca.ubc.cs304.util.SceneSwitchUtil;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import sun.jvm.hotspot.memory.Space;

import java.io.IOException;
import java.net.URL;
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
    private DatabaseConnectionHandler dbHandler =  DatabaseConnectionHandler.getInstance();
    private SceneSwitchUtil sceneSwitchUtil = SceneSwitchUtil.getInstance();
    private ObservableList<ObservableList> data;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Arrays.asList(VehicleType.values()).forEach(item -> vehicleType.getItems().add(item.getValue()));
        vehicleType.getItems().add("");
        vehicleType.getSelectionModel().select("");
        branchLocation.getItems().addAll("Vancouver", "Burnaby", "Richmond");
        branchLocation.getSelectionModel().select("Vancouver");
    }


    /**
     * Trigger search based on options selected
     * @param event
     */
    public void handleSelectVehicleOptions(MouseEvent event) {
        String vType = vehicleType.getValue();
        String location = branchLocation.getValue();
        // TODO: date is not working!!
//        String date = startDate.getValue().toString();
//        String date = startDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        ArrayList<Vehicle> vehicles = dbHandler.getVehiclesBasedOnOption(vType,location,"date");

        ArrayList<Vehicle> vehicles = new ArrayList<>();
        // Stub for now, just for testing
        vehicles.add(new Vehicle("A12345",VehicleType.ECONOMY,null,"blue", "Toyota", "Corolla", 2014));
        vehicles.add(new Vehicle("DE2345",VehicleType.ECONOMY,null,"red", "BMW", "M2", 2011));
        vehicles.add(new Vehicle("FS2345",VehicleType.ECONOMY,null,"orange", "Ford", "Mustang", 2015));
        vehicles.add(new Vehicle("SDF345",VehicleType.COMPACT,null,"orange", "Toyota", "Land Cruiser", 2016));
        vehicles.add(new Vehicle("ABD945",VehicleType.TRUCK,null,"purple", "Nissan", "Titan", 2009));

        HashMap<VehicleType,ArrayList<Vehicle>> vehicleMap = new HashMap<>();
        for(Vehicle v:vehicles){
            ArrayList<Vehicle> vehicleList = vehicleMap.get(v.getVehicleType());
            if (vehicleList != null && vehicleList.size() > 0) {
                vehicleList.add(v);
            } else {
                vehicleList = new ArrayList<>();
                vehicleList.add(v);
                vehicleMap.put(v.getVehicleType(),vehicleList);
            }
        }

        showVehicles(vehicleMap);
    }

    /**
     * Show list of vehicles based on input options
     * @param vehicleMap
     */
    private void showVehicles(HashMap<VehicleType, ArrayList<Vehicle>> vehicleMap) {
        ArrayList<TitledPane> panes = new ArrayList<>();
        for(VehicleType key : vehicleMap.keySet()){
            TitledPane newPane = new TitledPane();

            BorderPane borderPane = new BorderPane();
            Label title = new Label(key.getValue());
            BorderPane.setAlignment(title, Pos.CENTER);
            title.setMinWidth(250.0);
            Button btnReserve = new Button("Reserve");

            borderPane.setLeft(title);
            borderPane.setRight(btnReserve);

            newPane.setGraphic(borderPane);
            newPane.setAnimated(false);
            newPane.setPrefHeight(32.0);

            ArrayList<Vehicle> vehicles = vehicleMap.get(key);
            ListView listView = new ListView();
            btnReserve.setOnAction((event)->{
                System.out.println("Reserve Button Clicked");
                try {
                    switchToCustomerInfo(event, key);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            vehicles.forEach(vehicle -> {
                HBox vehicleHBox = new HBox();
                Label field = new Label(vehicle.toString());
                Label iconField = new Label(key.getVehicleIcon());
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

    private void switchToCustomerInfo(ActionEvent actionEvent, VehicleType vehicleType) throws IOException {
        FXMLLoader loader = sceneSwitchUtil.getLoaderForScene(SceneSwitchUtil.customerInfoFxml);
        Parent root = loader.load();

        CustomerInfoController customerInfoController = loader.getController();
        customerInfoController.setIntendedVehicleType(vehicleType);

        sceneSwitchUtil.switchSceneTo(actionEvent,root);
    }
}
