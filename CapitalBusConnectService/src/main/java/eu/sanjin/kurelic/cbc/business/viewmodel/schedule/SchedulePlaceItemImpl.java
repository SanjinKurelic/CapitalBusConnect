/*
 * Created by Sanjin Kurelic (kurelic@sanjin.eu)
 */

package eu.sanjin.kurelic.cbc.business.viewmodel.schedule;

import java.time.LocalDateTime;

public class SchedulePlaceItemImpl implements ScheduleItem {

    private final int scheduleId;
    private final String fromPlace;
    private final String toPlace;
    private final LocalDateTime fromDate;
    private final double price;
    private final int numberOfAdults;
    private final int numberOfChildren;
    private final ScheduleButtonType scheduleButton;
    private final ScheduleUpdateType onUpdate;
    private final SchedulePayingMethod payingMethod;
    private final boolean disabled;
    
    SchedulePlaceItemImpl(ScheduleBuilder sb){
        scheduleId = sb.getScheduleId();
        fromPlace = sb.getFromPlace();
        toPlace = sb.getToPlace();
        fromDate = sb.getDate();
        price = sb.getPrice();
        numberOfAdults = sb.getNumberOfAdults();
        numberOfChildren = sb.getNumberOfChildren();
        scheduleButton = sb.getButtonType();
        onUpdate = sb.getOnUpdate();
        payingMethod = sb.getPayingMethod();
        disabled = sb.getDisabled();
    }

    @Override
    public int getScheduleId() {
        return scheduleId;
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
    public boolean disabled() {
        return disabled;
    }

}
