/*
 * Created by Sanjin Kurelic (kurelic@sanjin.eu)
 */
package eu.sanjin.kurelic.cbc.business.viewmodel.schedule;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

class ScheduleTimeItemImpl implements ScheduleItem {
    
    private final int scheduleId;
    private final LocalDateTime fromDate;
    private final LocalTime fromTime;
    private final LocalTime toTime;
    private final double price;
    private final int numberOfAdults;
    private final int numberOfChildren;
    private final ScheduleButtonType scheduleButton;
    private final ScheduleUpdateType onUpdate;
    private final SchedulePayingMethod payingMethod;
    private final boolean disabled;

    ScheduleTimeItemImpl(ScheduleBuilder sb){
        scheduleId = sb.getScheduleId();
        fromDate = sb.getDate();
        fromTime = sb.getFromTime();
        toTime = sb.getToTime();
        price = sb.getPrice();
        numberOfAdults = sb.getNumberOfAdults();
        numberOfChildren = sb.getNumberOfChildren();
        scheduleButton = sb.getButtonType();
        onUpdate = sb.getOnUpdate();
        payingMethod = sb.getPayingMethod();
        disabled = sb.getDisabled();
    }
    
    @Override
    public String getLeftTitle() {
        return fromTime.withNano(0).toString();
    }

    @Override
    public String getRightTitle() {
        return toTime.withNano(0).toString();
    }

    @Override
    public String getDescription() {
        Duration duration = Duration.between(fromTime, toTime);
        return String.format("%d:%02d", duration.toHours(), duration.toMinutes() - 60 * duration.toHours());
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
    public int getScheduleId() {
        return scheduleId;
    }

    @Override
    public ScheduleButtonType getButton() {
        return scheduleButton;
    }

    @Override
    public ScheduleItemType getType() {
        return ScheduleItemType.TIME;
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
