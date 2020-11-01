package eu.sanjin.kurelic.cbc.business.viewmodel.schedule;

import eu.sanjin.kurelic.cbc.repo.values.TripTypeValue;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

class ScheduleTimeItemImpl implements ScheduleItem {

  private static final String DURATION_PATTERN = "%d:%02d";
  private final int scheduleId;
  private final LocalDateTime fromDate;
  private final LocalTime fromTime;
  private final Duration duration;
  private final double price;
  private final double basePrice;
  private final int numberOfAdults;
  private final int numberOfChildren;
  private final ScheduleButtonType scheduleButton;
  private final ScheduleUpdateType onUpdate;
  private final SchedulePayingMethod payingMethod;
  private final TripTypeValue tripType;
  private boolean disabled;

  ScheduleTimeItemImpl(ScheduleBuilder sb) {
    scheduleId = sb.getId();
    fromDate = sb.getDate();
    fromTime = sb.getFromTime();
    duration = sb.getDuration();
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
  public String getLeftTitle() {
    return fromTime.withNano(0).toString();
  }

  @Override
  public String getRightTitle() {
    return fromTime.plus(duration).withNano(0).toString();
  }

  // Not in Locale format for given hour, business decision :)
  @Override
  public String getDescription() {
    return String.format(DURATION_PATTERN, duration.toHours(), duration.toMinutesPart());
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
  public int getId() {
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
  public TripTypeValue getTripType() {
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
