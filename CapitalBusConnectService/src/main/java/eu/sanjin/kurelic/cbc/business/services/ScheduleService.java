package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.viewmodel.cart.CartItems;
import eu.sanjin.kurelic.cbc.business.viewmodel.schedule.ScheduleItems;
import eu.sanjin.kurelic.cbc.repo.entity.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;

public interface ScheduleService {

    ScheduleItems getBusLineSchedules(Integer fromCityId, Integer toCityId, LocalDate date);

    ScheduleItems getBusLineSchedules(String fromCityName, String toCityName, LocalDate date, Locale language);

    ScheduleItems getCartItemSchedules(CartItems cartItems, Locale language);

    double getTripPrice(LocalTime fromTime, LocalTime toTime);

    double getTripPrice(LocalTime fromTime, LocalTime toTime, LocalDate fromDate);

    default ScheduleItems getTravelHistory(User user, Locale language) {
        return getTravelHistory(user.getUsername(), language);
    }

    ScheduleItems getTravelHistory(String username, Locale language);

    ScheduleItems getTravelHistory(Locale language);

}
