package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.utility.LocaleUtility;
import eu.sanjin.kurelic.cbc.business.viewmodel.cart.CartItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.cart.CartItems;
import eu.sanjin.kurelic.cbc.business.viewmodel.schedule.*;
import eu.sanjin.kurelic.cbc.repo.dao.DestinationInfoDao;
import eu.sanjin.kurelic.cbc.repo.dao.ScheduleDao;
import eu.sanjin.kurelic.cbc.repo.dao.TravelHistoryDao;
import eu.sanjin.kurelic.cbc.repo.entity.BusSchedule;
import eu.sanjin.kurelic.cbc.repo.entity.TripHistory;
import eu.sanjin.kurelic.cbc.repo.entity.UserTravelHistory;
import eu.sanjin.kurelic.cbc.repo.values.PayingMethodValues;
import eu.sanjin.kurelic.cbc.repo.values.TripTypeValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final TravelHistoryDao historyDao;
    private final ScheduleDao scheduleDao;
    private final DestinationInfoDao destinationDao;

    @Autowired
    public ScheduleServiceImpl(@Qualifier("scheduleDaoImpl") ScheduleDao dao, @Qualifier("destinationInfoDaoImpl") DestinationInfoDao destinationDao, @Qualifier("travelHistoryDaoImpl") TravelHistoryDao historyDao) {
        this.scheduleDao = dao;
        this.destinationDao = destinationDao;
        this.historyDao = historyDao;
    }

    @Override
    @Transactional
    public ScheduleItems getBusLineSchedules(Integer fromCityId, Integer toCityId, LocalDate date) {
        ScheduleItems items = new ScheduleItems();
        ScheduleBuilder sb = new ScheduleBuilder();
        sb.setButtonType(ScheduleButtonType.ADD_TO_CART);

        var weekend = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
        List<BusSchedule> busSchedules = scheduleDao.getBusLineSchedules(fromCityId, toCityId);
        for (BusSchedule busSchedule : busSchedules) {
            // If bus does not operates continue
            if (!busSchedule.isOperates()) {
                continue;
            }
            // If weekend and bus does not ride on weekends
            if (weekend.contains(date.getDayOfWeek()) && !busSchedule.isWeekend()) {
                continue;
            }
            // If it's not weekend and bus does not ride on workdays
            else if (!busSchedule.isWorkweek()) {
                continue;
            }
            // Otherwise fill info
            sb.setScheduleId(busSchedule.getId());
            sb.setDate(LocalDateTime.of(date, busSchedule.getFromTime()));
            sb.setFromTime(busSchedule.getFromTime());
            sb.setToTime(busSchedule.getToTime());
            sb.setPrice(getTripPrice(busSchedule.getFromTime(), busSchedule.getToTime()));
            items.add(sb.buildTimeItem());
        }
        return items;
    }

    @Override
    @Transactional
    public ScheduleItems getBusLineSchedules(String fromCityName, String toCityName, LocalDate date, Locale language) {
        var fromCity = destinationDao.getCityDescription(fromCityName, LocaleUtility.getLanguage(language));
        var toCity = destinationDao.getCityDescription(toCityName, LocaleUtility.getLanguage(language));
        return getBusLineSchedules(fromCity.getId().getId(), toCity.getId().getId(), date);
    }

    @Override
    @Transactional
    public ScheduleItems getCartItemSchedules(CartItems cartItems, Locale language) {
        ScheduleItems scheduleItems = new ScheduleItems();

        if (cartItems != null && cartItems.size() > 0) {
            ScheduleBuilder sb = new ScheduleBuilder();
            sb.setButtonType(ScheduleButtonType.REMOVE_FROM_CART).setOnUpdate(ScheduleUpdateType.UPDATE_CART);
            // Database access optimization
            ArrayList<Integer> ids = new ArrayList<>();
            for (CartItem item : cartItems) {
                ids.add(item.getScheduleId());
            }
            var cities = getCities(ids, language);
            // Fill list
            for (CartItem cartItem : cartItems) {
                sb
                        .setScheduleId(cartItem.getScheduleId())
                        .setDate(cartItem.getDate())
                        .setNumberOfAdults(cartItem.getNumberOfAdults())
                        .setNumberOfChildren(cartItem.getNumberOfChildren())
                        .setFromPlace(cities.get(cartItem.getScheduleId()).getFirst())
                        .setToPlace(cities.get(cartItem.getScheduleId()).getSecond());
                scheduleItems.add(sb.buildPlaceItem());
            }
        }
        return scheduleItems;
    }

    @Override
    @Transactional
    public double getTripPrice(LocalTime fromTime, LocalTime toTime) {
        return getTripPrice(fromTime, toTime, LocalDate.now());
    }

    @Override
    @Transactional
    public double getTripPrice(LocalTime fromTime, LocalTime toTime, LocalDate fromDate) {
        //TODO remove ABS function
        int duration = (int) Math.abs(Math.ceil(ChronoUnit.MINUTES.between(fromTime, toTime) / 60.0));
        return scheduleDao.getTripPrice(duration, fromDate).getPrice();
    }

    @Override
    @Transactional
    public ScheduleItems getTravelHistory(String username, Locale language) {
        ScheduleItems items = new ScheduleItems();
        ScheduleBuilder sb = new ScheduleBuilder();
        sb.setDisabled(true).setButtonType(ScheduleButtonType.VIEW_TICKET);
        var travelItems = historyDao.getUserTravelHistory(username);
        // Database access optimization
        ArrayList<Integer> ids = new ArrayList<>();
        for (UserTravelHistory travelItem : travelItems) {
            ids.add(travelItem.getTripHistory().getBusSchedule().getId());
        }
        var cities = getCities(ids, language);
        // Build schedules
        for(UserTravelHistory travelItem : travelItems) {
            var tripHistory = travelItem.getTripHistory();
            sb
                    .setScheduleId(travelItem.getId().getId())
                    .setDate(LocalDateTime.of(tripHistory.getDate(), tripHistory.getBusSchedule().getFromTime()))
                    .setNumberOfAdults(travelItem.getNumberOfAdults())
                    .setNumberOfChildren(travelItem.getNumberOfChildren())
                    .setPrice(getTripPrice(tripHistory.getBusSchedule().getFromTime(), tripHistory.getBusSchedule().getToTime()))
                    .setFromPlace(cities.get(tripHistory.getBusSchedule().getId()).getFirst())
                    .setToPlace(cities.get(tripHistory.getBusSchedule().getId()).getSecond());
            // Set button type - could be done with valueOf
            if(travelItem.getPayingMethod().getName().equals(PayingMethodValues.PAY_PAL.name())) {
                sb.setPayingMethod(SchedulePayingMethod.PAY_PAL);
            } else if(travelItem.getPayingMethod().getName().equals(PayingMethodValues.MONEY.name())) {
                sb.setPayingMethod(SchedulePayingMethod.MONEY);
            }
            items.add(sb.buildPlaceItem());

        }
        return items;
    }

    @Override
    @Transactional
    public ScheduleItems getTravelHistory(Locale language) {
        ScheduleItems items = new ScheduleItems();
        ScheduleBuilder sb = new ScheduleBuilder();
        sb.setDisabled(true).setButtonType(ScheduleButtonType.NONE);
        var historyItems = historyDao.getTravelHistory();
        // Database access optimization
        ArrayList<Integer> ids = new ArrayList<>();
        for (TripHistory historyItem : historyItems) {
            ids.add(historyItem.getBusSchedule().getId());
        }
        var cities = getCities(ids, language);
        // Build schedules
        for(TripHistory historyItem : historyItems) {
            var capacity = historyItem.getBusSchedule().getBusType().getNumberOfSeats();
            sb
                    .setScheduleId(historyItem.getId())
                    .setDate(LocalDateTime.of(historyItem.getDate(), historyItem.getBusSchedule().getFromTime()))
                    .setNumberOfAdults(capacity - historyItem.getNumberOfSeats()) // Total number of seats - number of non occupied seats
                    .setPrice(getTripPrice(historyItem.getBusSchedule().getFromTime(), historyItem.getBusSchedule().getToTime()))
                    .setFromPlace(cities.get(historyItem.getBusSchedule().getId()).getFirst())
                    .setToPlace(cities.get(historyItem.getBusSchedule().getId()).getSecond())
                    .setNumberOfChildren(0); // Unused
            items.add(sb.buildPlaceItem());
        }
        return items;
    }

    // Utility methods
    private HashMap<Integer, Pair<String, String>> getCities(ArrayList<Integer> ids, Locale language) {
        // Get City name for every schedule
        var busSchedules = scheduleDao.getSchedules(ids.toArray(Integer[]::new));
        HashMap<Integer, Pair<String, String>> cities = new HashMap<>();
        for (var busSchedule : busSchedules) {
            var city1 = destinationDao.getCityDescription(busSchedule.getBusLine().getCity1().getId(), LocaleUtility.getLanguage(language));
            var city2 = destinationDao.getCityDescription(busSchedule.getBusLine().getCity2().getId(), LocaleUtility.getLanguage(language));
            if(busSchedule.getTripType().getName().equals(TripTypeValues.A_TO_B.name())) {
                cities.put(busSchedule.getId(), Pair.of(city1.getTitle(), city2.getTitle()));
            } else if(busSchedule.getTripType().getName().equals(TripTypeValues.B_TO_A.name())) {
                cities.put(busSchedule.getId(), Pair.of(city1.getTitle(), city2.getTitle()));
            } else {
                throw new RuntimeException("Trip type is not valid!");
            }

        }
        return cities;
    }

}
