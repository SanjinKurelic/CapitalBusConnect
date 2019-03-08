package eu.sanjin.kurelic.cbc.business.viewmodel.traffic;

import java.time.LocalDate;

public class TrafficInfoItem {

    private String textMessage;
    private TrafficWarningType warningType;
    private LocalDate date;

    public TrafficInfoItem() {
    }

    public TrafficInfoItem(String textMessage, TrafficWarningType warningType, LocalDate date) {
        this.textMessage = textMessage;
        this.warningType = warningType;
        this.date = date;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public TrafficWarningType getWarningType() {
        return warningType;
    }

    public void setWarningType(TrafficWarningType warningType) {
        this.warningType = warningType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
