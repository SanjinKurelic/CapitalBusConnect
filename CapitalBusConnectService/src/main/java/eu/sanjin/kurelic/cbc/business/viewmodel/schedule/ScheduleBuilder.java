package eu.sanjin.kurelic.cbc.business.viewmodel.schedule;

import eu.sanjin.kurelic.cbc.repo.values.TripTypeValues;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ScheduleBuilder {

  private int id;
  private String fromPlace;
  private String toPlace;
  private LocalTime fromTime;
  private Duration duration;
  private LocalDateTime date;
  private double price;
  private double basePrice;
  private int numberOfAdults;
  private int numberOfChildren;
  private ScheduleButtonType button;
  private ScheduleUpdateType onUpdate;
  private SchedulePayingMethod payingMethod;
  private TripTypeValues tripType;
  private boolean disabled;

  public ScheduleBuilder() {
    id = 0;
    fromPlace = "";
    toPlace = "";
    fromTime = null;
    duration = null;
    date = null;
    price = 0;
    basePrice = 0;
    numberOfAdults = 0;
    numberOfChildren = 0;
    onUpdate = ScheduleUpdateType.NONE;
    button = ScheduleButtonType.NONE;
    payingMethod = SchedulePayingMethod.NONE;
    tripType = TripTypeValues.NONE;
    disabled = false;
  }

  int getId() {
    return id;
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

  Duration getDuration() {
    return duration;
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

  TripTypeValues getTripType() {
    return tripType;
  }

  public boolean getDisabled() {
    return disabled;
  }

  public ScheduleBuilder setId(int id) {
    this.id = id;
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

  public ScheduleBuilder setDuration(Duration duration) {
    this.duration = duration;
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

  public ScheduleBuilder setOnUpdate(ScheduleUpdateType onUpdate) {
    this.onUpdate = onUpdate;
    return this;
  }

  public ScheduleBuilder setPayingMethod(SchedulePayingMethod payingMethod) {
    this.payingMethod = payingMethod;
    return this;
  }

  public ScheduleBuilder setTripType(TripTypeValues tripType) {
    this.tripType = tripType;
    return this;
  }

  public ScheduleBuilder setDisabled(boolean disabled) {
    this.disabled = disabled;
    return this;
  }

  public ScheduleItem buildPlaceItem() {
    return new SchedulePlaceItemImpl(this);
  }

  public ScheduleItem buildTimeItem() {
    return new ScheduleTimeItemImpl(this);
  }

}
