package ca.ubc.cs304.main;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.util.SceneSwitchUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Main controller class, responsible for creating UI frames and servicing requests
 */
public class SuperRent extends Application {

    private DatabaseConnectionHandler dbHandler = DatabaseConnectionHandler.getInstance();

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // To log in to Oracle, change credential and uncomment in DbConnectionHandler
        boolean didLogin = dbHandler.login();
        if(!didLogin) System.exit(0);

        Parent root = FXMLLoader.load(getClass().getResource(SceneSwitchUtil.loginFxml));
        Font.loadFont(getClass().getResource("../styles/OpenSansEmoji.ttf").
                toExternalForm(), 15);
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.UNIFIED);
        primaryStage.setTitle("SuperRent");
        primaryStage.show();
    }
}
