package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.utility.LocaleUtility;
import eu.sanjin.kurelic.cbc.business.viewmodel.cart.CartItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.cart.CartItems;
import eu.sanjin.kurelic.cbc.business.viewmodel.schedule.*;
import eu.sanjin.kurelic.cbc.repo.dao.DestinationInfoDao;
import eu.sanjin.kurelic.cbc.repo.dao.ScheduleDao;
import eu.sanjin.kurelic.cbc.repo.dao.TravelHistoryDao;
import eu.sanjin.kurelic.cbc.repo.entity.BusSchedule;
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
import java.util.stream.Collectors;

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
            sb.setId(busSchedule.getId());
            sb.setDate(LocalDateTime.of(date, busSchedule.getFromTime()));
            sb.setFromTime(busSchedule.getFromTime());
            sb.setToTime(busSchedule.getToTime());
            sb.setPrice(getTripPrice(busSchedule.getFromTime(), busSchedule.getToTime()));
            // Trip type info
            if (busSchedule.getBusLine().getCity1().getId() == fromCityId) {
                sb.setTripType(TripTypeValues.A_TO_B);
            } else {
                sb.setTripType(TripTypeValues.B_TO_A);
            }
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
    public ScheduleItems getCartItemSchedules(CartItems cartItems, boolean bought, Locale language) {
        ScheduleItems scheduleItems = new ScheduleItems();

        if (cartItems != null && cartItems.size() > 0) {
            ScheduleBuilder sb = new ScheduleBuilder();
            if(!bought) {
                sb.setButtonType(ScheduleButtonType.REMOVE_FROM_CART).setOnUpdate(ScheduleUpdateType.UPDATE_CART);
            } else {
                sb.setButtonType(ScheduleButtonType.VIEW_TICKET);
            }
            // Database access optimization
            ArrayList<Integer> ids = new ArrayList<>();
            for (CartItem item : cartItems) {
                ids.add(item.getScheduleId());
            }
            var schedules = getSchedules(ids);
            var cities = getCities(schedules, language);
            double basePrice, price;
            // Fill list
            for (CartItem cartItem : cartItems) {
                // Calculate price
                var schedule = schedules.get(cartItem.getScheduleId());
                basePrice = getTripPrice(schedule.getFromTime(), schedule.getToTime());
                price = cartItem.getNumberOfAdults() * basePrice + cartItem.getNumberOfChildren() * basePrice;
                // Get correct city order
                var city1 = cities.get(cartItem.getScheduleId()).getFirst();
                var city2 = cities.get(cartItem.getScheduleId()).getSecond();
                if(cartItem.getTripType() == TripTypeValues.B_TO_A) {
                    var city = city1;
                    city1 = city2;
                    city2 = city;
                }
                // Build schedule item
                sb
                        .setId(cartItem.getScheduleId())
                        .setDate(cartItem.getDate())
                        .setNumberOfAdults(cartItem.getNumberOfAdults())
                        .setNumberOfChildren(cartItem.getNumberOfChildren())
                        .setFromPlace(city1)
                        .setToPlace(city2)
                        .setTripType(cartItem.getTripType())
                        .setBasePrice(basePrice)
                        .setPrice(price);
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

    // Travel history
    @Override
    @Transactional
    public ScheduleItems getUserTravelHistory(String username, LocalDate date, int pageNumber, int limit, Locale language) {
        List<UserTravelHistory> travelItems;
        ScheduleItems items = new ScheduleItems();
        ScheduleBuilder sb = new ScheduleBuilder();
        // Page number
        pageNumber = (pageNumber - 1) * limit;
        if (pageNumber < 0) {
            return items;
        }
        sb.setButtonType(ScheduleButtonType.VIEW_TICKET);

        if(date == null) {
            travelItems = historyDao.getUserTravelHistory(username, pageNumber, limit);
        } else {
            travelItems = historyDao.getUserTravelHistory(username, date, pageNumber, limit);
        }
        // Database access optimization
        ArrayList<Integer> ids = new ArrayList<>();
        for (UserTravelHistory travelItem : travelItems) {
            ids.add(travelItem.getTripHistory().getBusSchedule().getId());
        }
        var cities = getCities(ids, language);
        // Build schedules
        for(UserTravelHistory travelItem : travelItems) {
            var tripHistory = travelItem.getTripHistory();
            // Get correct city order
            var city1 = cities.get(tripHistory.getBusSchedule().getId()).getFirst();
            var city2 = cities.get(tripHistory.getBusSchedule().getId()).getSecond();
            if(tripHistory.getTripType().getName().equals(TripTypeValues.B_TO_A.name())) {
                var city = city1;
                city1 = city2;
                city2 = city;
            }
            sb
                    .setId(travelItem.getId())
                    .setDate(LocalDateTime.of(tripHistory.getDate(), tripHistory.getBusSchedule().getFromTime()))
                    .setNumberOfAdults(travelItem.getNumberOfAdults())
                    .setNumberOfChildren(travelItem.getNumberOfChildren())
                    // Price which was paid back then
                    .setPrice(travelItem.getPrice() * travelItem.getNumberOfAdults() + travelItem.getPrice() * travelItem.getNumberOfChildren())
                    .setTripType(TripTypeValues.valueOf(tripHistory.getTripType().getName()))
                    .setFromPlace(city1)
                    .setToPlace(city2);
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
    public int getUserTravelHistoryCount(String username) {
        return historyDao.getUserTravelHistoryCount(username);
    }

    // Utility methods
    private HashMap<Integer, Pair<String, String>> getCities(ArrayList<Integer> ids, Locale language) {
        return getCities(getSchedules(ids), language);
    }

    private HashMap<Integer, Pair<String, String>> getCities(Map<Integer, BusSchedule> busSchedules, Locale language) {
        // Get City name for every schedule
        HashMap<Integer, Pair<String, String>> cities = new HashMap<>();
        for (Map.Entry<Integer, BusSchedule> busSchedule : busSchedules.entrySet()) {
            cities.put(busSchedule.getKey(), getCityDescription(busSchedule.getValue(), language));
        }
        return cities;
    }

    private Pair<String, String> getCityDescription(BusSchedule busSchedule, Locale language) {
        var city1 = destinationDao.getCityDescription(busSchedule.getBusLine().getCity1().getId(), LocaleUtility.getLanguage(language));
        var city2 = destinationDao.getCityDescription(busSchedule.getBusLine().getCity2().getId(), LocaleUtility.getLanguage(language));
        return Pair.of(city1.getTitle(), city2.getTitle());
    }

    private Map<Integer, BusSchedule> getSchedules(ArrayList<Integer> ids) {
        var busSchedules = scheduleDao.getSchedules(ids.toArray(Integer[]::new));
        return busSchedules.stream().collect(Collectors.toMap(BusSchedule::getId, i -> i));
    }

}
