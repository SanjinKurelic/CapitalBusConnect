package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.viewmodel.cart.CartItems;
import eu.sanjin.kurelic.cbc.business.viewmodel.schedule.ScheduleItems;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Locale;

@Service
public interface ScheduleService {

    ScheduleItems getBusLineSchedules(Integer fromCityId, Integer toCityId, LocalDate date);

    ScheduleItems getBusLineSchedules(String fromCityName, String toCityName, LocalDate date, Locale language);

    @Transactional
    default ScheduleItems getCartItemSchedules(CartItems cartItems, Locale language) {
        return getCartItemSchedules(cartItems, false, language);
    }

    ScheduleItems getCartItemSchedules(CartItems cartItems, boolean bought, Locale language);

    @Transactional
    default ScheduleItems getUserTravelHistory(String username, int pageNumber, int limit, Locale language) {
        return getUserTravelHistory(username, null, pageNumber, limit, language);
    }

    ScheduleItems getUserTravelHistory(String username, LocalDate date, int pageNumber, int limit, Locale language);

    Long getUserTravelHistoryCount(String username);

}
