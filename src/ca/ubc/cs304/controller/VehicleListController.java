package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.Vehicle;
import ca.ubc.cs304.model.VehicleType;
import ca.ubc.cs304.model.VehicleTypeName;
import ca.ubc.cs304.util.SceneSwitchUtil;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
        Arrays.asList(VehicleTypeName.values()).forEach(item -> vehicleType.getItems().add(item.getName()));
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
        // TODO: save vehicle in a Map, so that expand to show vehicles does what it supposed to;
        // Each vehicle type maps to a list of available vehicles
        showVehicles(vehicles);
    }

    /**
     * Show list of vehicles based on input options
     */
    private void showVehicles(ArrayList<Vehicle> vehicles) {
        System.out.println("Show me some vehicles");
        ArrayList<TitledPane> panes = new ArrayList<>();
        vehicles.forEach((item) -> {
            TitledPane newPane = new TitledPane( "testtesttest", new Button("Reserve"));
            ListView listView = new ListView();
            Button btnReserve = new Button("Reserve");
            btnReserve.setOnAction((event)->{
                System.out.println("Button Action");
                try {
                    switchToCustomerInfo(event, VehicleTypeName.getVehicleTypeName(item.getVtname()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            listView.getItems().add(btnReserve);
            listView.getItems().add("Item 1");
            listView.getItems().add("Item 2");
            listView.getItems().add("Item 3");
            newPane.setContent(listView);
            panes.add(newPane);
        });
        resultAccordion.getPanes().addAll(panes);
    }

    private void switchToCustomerInfo(ActionEvent actionEvent, VehicleTypeName vehicleType) throws IOException {
        FXMLLoader loader = sceneSwitchUtil.getLoaderForScene(SceneSwitchUtil.customerInfoFxml);
        Parent root = loader.load();

        CustomerInfoController customerInfoController = loader.getController();
        customerInfoController.setIntendedVehicleType(vehicleType);

        sceneSwitchUtil.switchSceneTo(actionEvent,root);
    }
}
