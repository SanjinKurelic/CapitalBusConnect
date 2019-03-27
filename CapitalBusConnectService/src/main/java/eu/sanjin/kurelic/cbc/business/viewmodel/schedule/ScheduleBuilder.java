/*
 * Created by Sanjin Kurelic (kurelic@sanjin.eu)
 */

package eu.sanjin.kurelic.cbc.business.viewmodel.schedule;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class ScheduleBuilder {
    
    private int scheduleId;
    private String fromPlace;
    private String toPlace;
    private LocalTime fromTime;
    private LocalTime toTime;
    private LocalDateTime date;
    private double price;
    private double basePrice;
    private int numberOfAdults;
    private int numberOfChildren;
    private ScheduleButtonType button;
    private ScheduleUpdateType onUpdate;
    private SchedulePayingMethod payingMethod;
    private boolean disabled;

    public ScheduleBuilder() {
        scheduleId = 0;
        fromPlace = "";
        toPlace = "";
        fromTime = null;
        toTime = null;
        date = null;
        basePrice = price = 0;
        numberOfAdults = 0;
        numberOfChildren = 0;
        onUpdate = ScheduleUpdateType.NONE;
        button = ScheduleButtonType.NONE;
        payingMethod = SchedulePayingMethod.NONE;
        disabled = false;
    }

    int getScheduleId() {
        return scheduleId;
    }

    String getFromPlace() {
        return fromPlace;
    }

    String getToPlace() {
        return toPlace;
    }

    LocalTime getFromTime() {
        return fromTime;
    }

    LocalTime getToTime() {
        return toTime;
    }

    LocalDateTime getDate() {
        return date;
    }

    double getPrice() {
        return price;
    }

    double getBasePrice() {
        return basePrice;
    }

    int getNumberOfAdults() {
        return numberOfAdults;
    }

    int getNumberOfChildren() {
        return numberOfChildren;
    }

    ScheduleButtonType getButtonType() {
        return button;
    }
    
    ScheduleUpdateType getOnUpdate() {
        return onUpdate;
    }

    SchedulePayingMethod getPayingMethod() {
        return payingMethod;
    }

    public boolean getDisabled() {
        return disabled;
    }

    public ScheduleBuilder setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
        return this;
    }

    public ScheduleBuilder setFromPlace(String fromPlace) {
        this.fromPlace = fromPlace;
        return this;
    }

    public ScheduleBuilder setToPlace(String toPlace) {
        this.toPlace = toPlace;
        return this;
    }

    public ScheduleBuilder setFromTime(LocalTime fromTime) {
        this.fromTime = fromTime;
        return this;
    }

    public ScheduleBuilder setToTime(LocalTime toTime) {
        this.toTime = toTime;
        return this;
    }

    public ScheduleBuilder setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public ScheduleBuilder setPrice(double price) {
        this.price = price;
        return this;
    }

    public ScheduleBuilder setBasePrice(double basePrice) {
        this.basePrice = basePrice;
        return this;
    }

    public ScheduleBuilder setNumberOfAdults(int numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
        return this;
    }

    public ScheduleBuilder setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
        return this;
    }

    public ScheduleBuilder setButtonType(ScheduleButtonType button) {
        this.button = button;
        return this;
    }
    
    public ScheduleBuilder setOnUpdate(ScheduleUpdateType onUpdate){
        this.onUpdate = onUpdate;
        return this;
    }

    public ScheduleBuilder setPayingMethod(SchedulePayingMethod payingMethod) {
        this.payingMethod = payingMethod;
        return this;
    }

    public ScheduleBuilder setDisabled(boolean disabled) {
        this.disabled = disabled;
        return this;
    }
    
    public ScheduleItem buildPlaceItem(){
        return new SchedulePlaceItemImpl(this);
    }
    
    public ScheduleItem buildTimeItem(){
        return new ScheduleTimeItemImpl(this);
    }

}
