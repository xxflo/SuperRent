package ca.ubc.cs304.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitchUtil {
    public static final String loginFxml = "../fxml/Login.fxml";
    public static final String customerInfoFxml = "../fxml/CustomerInfo.fxml";
    public static final String reservationFxml = "../fxml/Reservation.fxml";
    public static final String vehicleFxml = "../fxml/Vehicle.fxml";
    public static final String confirmationFxml = "../fxml/Confirmation.fxml";
    public static final String adminFxml = "../fxml/Admin.fxml";
    public static final String clerkFxml = "../fxml/Clerk.fxml";
    public static final String reportFxml = "../fxml/Report.fxml";
    private static SceneSwitchUtil switchUtil;

    private SceneSwitchUtil(){}

    public static SceneSwitchUtil getInstance(){
        if (switchUtil == null){
            switchUtil = new SceneSwitchUtil();
        }
        return switchUtil;
    }


    public FXMLLoader getLoaderForScene(String location) {
        return new FXMLLoader(getClass().getResource(location));
    }

    public void switchSceneTo(ActionEvent actionEvent, String location) throws IOException {
        Parent root = getRootWithLoader(location);
        switchSceneTo(actionEvent,root);
    }

    private Parent getRootWithLoader(String location) throws IOException {
        return FXMLLoader.load(getClass().getResource(location));
    }

    public void switchSceneTo(ActionEvent actionEvent, Parent root){
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
