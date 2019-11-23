package ca.ubc.cs304.controller;

import ca.ubc.cs304.util.SceneSwitchUtil;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private Button btnCustomer;

    @FXML
    private Button btnClerk;

    @FXML
    private Button btnAdmin;

    private SceneSwitchUtil sceneSwitchUtil = SceneSwitchUtil.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void handleClicks(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == btnCustomer) {
            btnCustomer.setStyle("-fx-background-color : #1620A1");
            sceneSwitchUtil.switchSceneTo(actionEvent, SceneSwitchUtil.vehicleFxml);
        }
        if (actionEvent.getSource() == btnClerk) {
            btnClerk.setStyle("-fx-background-color : #1620A1");
            sceneSwitchUtil.switchSceneTo(actionEvent, SceneSwitchUtil.clerkFxml);
        }
        if (actionEvent.getSource() == btnAdmin) {
            btnCustomer.setStyle("-fx-background-color: #1620A1");
            sceneSwitchUtil.switchSceneTo(actionEvent, SceneSwitchUtil.adminFxml);
        }
    }
}
