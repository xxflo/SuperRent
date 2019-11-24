package ca.ubc.cs304.util;

import ca.ubc.cs304.model.Branch;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;


public class TimeUtil {
    public static Timestamp getTimeStamp(DatePicker date, Spinner time){
        if (date.getValue() == null || time.getValue() == null) {
            return null;
        }
        LocalDate dateVal = date.getValue();
        LocalTime timeVal = (LocalTime) time.getValue();
        return Timestamp.valueOf(dateVal.atTime(timeVal));
    }

    public static Date getTimeStampDate(DatePicker date) {
        LocalDate dateVal = date.getValue();
        return Date.valueOf(dateVal);
    }
}
