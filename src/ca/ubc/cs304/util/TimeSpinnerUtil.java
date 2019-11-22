package ca.ubc.cs304.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import javafx.util.converter.LocalTimeStringConverter;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeSpinnerUtil {

    public static SpinnerValueFactory getSpinnerFactory(){
        return new SpinnerValueFactory() {
            {
                setConverter(new LocalTimeStringConverter(DateTimeFormatter.ofPattern("HH:mm"), DateTimeFormatter.ofPattern("HH:mm")));
            }
            @Override
            public void decrement(int steps) {
                if (getValue() == null)
                    setValue(LocalTime.now());
                else {
                    LocalTime time = (LocalTime) getValue();
                    setValue(time.minusMinutes(steps));
                }
            }

            @Override
            public void increment(int steps) {
                if (this.getValue() == null)
                    setValue(LocalTime.now());
                else {
                    LocalTime time = (LocalTime) getValue();
                    setValue(time.plusMinutes(steps));
                }
            }
        };
    }

}
