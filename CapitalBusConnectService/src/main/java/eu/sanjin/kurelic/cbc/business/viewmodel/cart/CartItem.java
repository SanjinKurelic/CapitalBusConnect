package eu.sanjin.kurelic.cbc.business.viewmodel.cart;

import com.fasterxml.jackson.annotation.JsonFormat;
import eu.sanjin.kurelic.cbc.repo.values.TripTypeValues;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class CartItem implements Serializable {

    private int scheduleId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime date;
    private int numberOfAdults;
    private int numberOfChildren;
    private TripTypeValues tripType;

    public CartItem() {
    }

    public CartItem(int scheduleId, LocalDateTime date, int numberOfAdults, int numberOfChildren, TripTypeValues tripType) {
        this.scheduleId = scheduleId;
        this.date = date;
        this.numberOfAdults = numberOfAdults;
        this.numberOfChildren = numberOfChildren;
        this.tripType = tripType;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getNumberOfAdults() {
        return numberOfAdults;
    }

    public void setNumberOfAdults(int numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public TripTypeValues getTripType() {
        return tripType;
    }

    public void setTripType(TripTypeValues tripType) {
        this.tripType = tripType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return scheduleId == cartItem.scheduleId &&
                Objects.equals(date, cartItem.date) && tripType == cartItem.tripType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleId, date, tripType);
    }
}
