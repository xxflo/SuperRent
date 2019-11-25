package cpsc304.controller;

import cpsc304.util.SceneSwitchUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClerkController implements Initializable {

    public Button rentReturnButton;
    public Button reportButton;
    private SceneSwitchUtil sceneSwitchUtil = SceneSwitchUtil.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void handlePress(ActionEvent event) throws IOException {
        if (event.getSource() == reportButton) {
            SceneSwitchUtil.getInstance().switchSceneTo(event, SceneSwitchUtil.reportFxml);
        } else if (event.getSource() == rentReturnButton) {
            FXMLLoader loader = SceneSwitchUtil.getInstance().getLoaderForScene(SceneSwitchUtil.customerInfoFxml);
            Parent root = loader.load();
            CustomerInfoController customerInfoController = loader.getController();
            customerInfoController.setNextView(SceneSwitchUtil.rentFxml);
            SceneSwitchUtil.getInstance().switchSceneTo(event, root);
        }
    }

    public void handleGoBackMainPressed(ActionEvent actionEvent) throws IOException {
        sceneSwitchUtil.switchSceneTo(actionEvent, SceneSwitchUtil.loginFxml);
    }
}
