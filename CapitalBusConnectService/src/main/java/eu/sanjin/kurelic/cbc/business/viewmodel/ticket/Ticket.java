package eu.sanjin.kurelic.cbc.business.viewmodel.ticket;

import eu.sanjin.kurelic.cbc.repo.values.TripTypeValues;

import java.time.LocalDate;
import java.time.LocalTime;

public class Ticket {

    private int id;
    private String name;
    private String surname;
    private String email;
    private String fromCity;
    private String toCity;
    private LocalDate date;
    private LocalTime time;
    private int numberOfAdults;
    private int numberOfChildren;
    private TripTypeValues tripType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
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
}
