package eu.sanjin.kurelic.cbc.business.viewmodel.schedule;

import eu.sanjin.kurelic.cbc.repo.values.TripTypeValue;

import java.time.LocalDateTime;

public interface ScheduleItem {

  int getId();

  String getLeftTitle();

  String getRightTitle();

  String getDescription();

  int getNumberOfAdults();

  int getNumberOfChildren();

  double getPrice();

  double getBasePrice();

  ScheduleButtonType getButton();

  ScheduleItemType getType();

  LocalDateTime getDate();

  ScheduleUpdateType getOnUpdate();

  SchedulePayingMethod getPayingMethod();

  TripTypeValue getTripType();

  boolean disabled();

  void setDisabled(boolean disabled);

}
