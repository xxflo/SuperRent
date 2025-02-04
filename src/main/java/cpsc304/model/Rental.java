package cpsc304.model;

import java.sql.Timestamp;

/**
 * Store information about a single rental
 */
public class Rental implements DateTimeModel {
    private final String rid;
    private final String vlicense;
    private final String dlicense;
    private final int odometer;
    private final String cardName;
    private final String cardNo;
    private final String expDate;
    private final String confNo;
    private final Timestamp fromDateTime;
    private final Timestamp toDateTime;

    public Rental(String rid, String vlicense, String dlicense, int odometer, String cardName, String cardNo, String expDate, String confNo, Timestamp fromDateTime, Timestamp toDateTime) {
        this.rid = rid;
        this.vlicense = vlicense;
        this.dlicense = dlicense;
        this.odometer = odometer;
        this.cardName = cardName;
        this.cardNo = cardNo;
        this.expDate = expDate;
        this.confNo = confNo;
        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
    }

    public Timestamp getFromDateTime() {
        return fromDateTime;
    }

    public Timestamp getToDateTime() {
        return toDateTime;
    }

    public String getRid() {
        return rid;
    }

    public String getVlicense() {
        return vlicense;
    }

    public String getDlicense() {
        return dlicense;
    }

    public int getOdometer() {
        return odometer;
    }

    public String getCardName() {
        return cardName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public String getExpDate() {
        return expDate;
    }

    public String getConfNo() {
        return confNo;
    }

    @Override
    public Timestamp getStartTime() {
        return fromDateTime;
    }

    @Override
    public Timestamp getEndTime() {
        return toDateTime;
    }
}
