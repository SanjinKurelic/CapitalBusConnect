package eu.sanjin.kurelic.cbc.business.viewmodel.cart;

import com.fasterxml.jackson.annotation.JsonFormat;
import eu.sanjin.kurelic.cbc.repo.values.TripTypeValue;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class CartItem implements Serializable {

  @NotNull(message = "ScheduleId is required.")
  @Min(value = 1, message = "ScheduleId must be positive (not 0) number.")
  private Integer scheduleId;
  @NotNull(message = "Date is required.")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
  private LocalDateTime date;
  @NotNull(message = "NumberOfAdults is required.")
  @Min(value = 1, message = "NumberOfAdults must be between 1-10.")
  @Max(value = 10, message = "NumberOfAdults must be between 1-10.")
  private Integer numberOfAdults;
  @NotNull(message = "NumberOfChildren is required.")
  @Min(value = 0, message = "NumberOfAdults must be between 0-10.")
  @Max(value = 10, message = "NumberOfChildren must be between 0-10.")
  private Integer numberOfChildren;
  @NotNull(message = "TripType is required.")
  private TripTypeValue tripType;

  public CartItem() {
  }

  public CartItem(Integer scheduleId, LocalDateTime date, Integer numberOfAdults, Integer numberOfChildren,
                  TripTypeValue tripType) {
    this.scheduleId = scheduleId;
    this.date = date;
    this.numberOfAdults = numberOfAdults;
    this.numberOfChildren = numberOfChildren;
    this.tripType = tripType;
  }

  public Integer getScheduleId() {
    return scheduleId;
  }

  public void setScheduleId(Integer scheduleId) {
    this.scheduleId = scheduleId;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  public Integer getNumberOfAdults() {
    return numberOfAdults;
  }

  public void setNumberOfAdults(Integer numberOfAdults) {
    this.numberOfAdults = numberOfAdults;
  }

  public Integer getNumberOfChildren() {
    return numberOfChildren;
  }

  public void setNumberOfChildren(Integer numberOfChildren) {
    this.numberOfChildren = numberOfChildren;
  }

  public TripTypeValue getTripType() {
    return tripType;
  }

  public void setTripType(TripTypeValue tripType) {
    this.tripType = tripType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (Objects.isNull(o) || getClass() != o.getClass()) return false;
    CartItem cartItem = (CartItem) o;
    return Objects.equals(scheduleId, cartItem.scheduleId) &&
      Objects.equals(date, cartItem.date) && tripType == cartItem.tripType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(scheduleId, date, tripType);
  }
}
