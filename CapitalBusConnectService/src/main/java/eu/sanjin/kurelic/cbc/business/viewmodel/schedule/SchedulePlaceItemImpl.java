package eu.sanjin.kurelic.cbc.business.viewmodel.schedule;

import eu.sanjin.kurelic.cbc.repo.values.TripTypeValues;

import java.time.LocalDateTime;

public class SchedulePlaceItemImpl implements ScheduleItem {

    private final int id;
    private final String fromPlace;
    private final String toPlace;
    private final LocalDateTime fromDate;
    private final double price;
    private final double basePrice;
    private final int numberOfAdults;
    private final int numberOfChildren;
    private final ScheduleButtonType scheduleButton;
    private final ScheduleUpdateType onUpdate;
    private final SchedulePayingMethod payingMethod;
    private final TripTypeValues tripType;
    private boolean disabled;

    SchedulePlaceItemImpl(ScheduleBuilder sb) {
        id = sb.getId();
        fromPlace = sb.getFromPlace();
        toPlace = sb.getToPlace();
        fromDate = sb.getDate();
        price = sb.getPrice();
        basePrice = sb.getBasePrice();
        numberOfAdults = sb.getNumberOfAdults();
        numberOfChildren = sb.getNumberOfChildren();
        scheduleButton = sb.getButtonType();
        onUpdate = sb.getOnUpdate();
        payingMethod = sb.getPayingMethod();
        tripType = sb.getTripType();
        disabled = sb.getDisabled();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getLeftTitle() {
        return fromPlace;
    }

    @Override
    public String getRightTitle() {
        return toPlace;
    }

    @Override
    public String getDescription() {
        return fromDate.toString();
    }

    @Override
    public int getNumberOfAdults() {
        return numberOfAdults;
    }

    @Override
    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public double getBasePrice() {
        if (basePrice == 0) {
            return price;
        }
        return basePrice;
    }

    @Override
    public ScheduleItemType getType() {
        return ScheduleItemType.PLACE;
    }

    @Override
    public ScheduleButtonType getButton() {
        return scheduleButton;
    }

    @Override
    public LocalDateTime getDate() {
        return fromDate;
    }

    @Override
    public ScheduleUpdateType getOnUpdate() {
        return onUpdate;
    }

    @Override
    public SchedulePayingMethod getPayingMethod() {
        return payingMethod;
    }

    @Override
    public TripTypeValues getTripType() {
        return tripType;
    }

    @Override
    public boolean disabled() {
        return disabled;
    }

    @Override
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

}
