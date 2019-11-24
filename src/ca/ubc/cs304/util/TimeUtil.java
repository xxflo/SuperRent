package ca.ubc.cs304.util;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;


public class TimeUtil {
    public static Timestamp getTimeStamp(DatePicker date, Spinner time){
        LocalDate dateVal;
        LocalTime timeVal;
        if (date.getValue() != null){
            dateVal = date.getValue();
            timeVal = time.getValue() == null ? LocalTime.of(0,0) : (LocalTime) time.getValue();
            return Timestamp.valueOf(dateVal.atTime(timeVal));
        }
        return null;
    }

    public static Date getTimeStampDate(DatePicker date) {
        LocalDate dateVal = date.getValue();
        return Date.valueOf(dateVal);
    }
}
