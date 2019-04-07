package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.utility.LocaleUtility;
import eu.sanjin.kurelic.cbc.business.viewmodel.info.*;
import eu.sanjin.kurelic.cbc.repo.dao.CityDescriptionDao;
import eu.sanjin.kurelic.cbc.repo.dao.TripHistoryDao;
import eu.sanjin.kurelic.cbc.repo.dao.UserDao;
import eu.sanjin.kurelic.cbc.repo.dao.UserTravelHistoryDao;
import eu.sanjin.kurelic.cbc.repo.entity.TripHistory;
import eu.sanjin.kurelic.cbc.repo.values.TripTypeValues;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import javax.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Service
public class StatisticServiceImpl implements StatisticService {

    private final int TUPLE_FIRST_ITEM = 0;
    private final int TUPLE_SECOND_ITEM = 1;

    private final UserTravelHistoryDao userTravelHistoryDao;
    private final UserDao userDao;
    private final TripHistoryDao tripHistoryDao;
    private final CityDescriptionDao cityDescriptionDao;

    public StatisticServiceImpl(UserTravelHistoryDao userTravelHistoryDao, UserDao userDao, TripHistoryDao tripHistoryDao, CityDescriptionDao cityDescriptionDao) {
        this.userTravelHistoryDao = userTravelHistoryDao;
        this.userDao = userDao;
        this.tripHistoryDao = tripHistoryDao;
        this.cityDescriptionDao = cityDescriptionDao;
    }

    @Override
    @Transactional
    public InfoItems getTopUsersByTravels(int limit) {
        InfoItems items = new InfoItems();
        InfoItem item;
        var usernameCounterTuples = userTravelHistoryDao.getTopUsersByTravels(limit);
        for (var usernameCounterTuple : usernameCounterTuples) {
            item = new InfoItem();
            // get user info - database query in for loop (statistics are not something heavily requested)
            var user = userDao.getUserInformation(usernameCounterTuple.get(TUPLE_FIRST_ITEM).toString());
            // fill info item
            item.addColumn(new InfoItemTextColumn(user.getUsername()));
            item.addColumn(new InfoItemTextColumn(user.getName()));
            item.addColumn(new InfoItemTextColumn(user.getSurname()));
            item.addColumn(new InfoItemTextColumn(usernameCounterTuple.get(TUPLE_SECOND_ITEM).toString()));
            // add to items
            items.add(item);
        }
        return items;
    }

    @Override
    @Transactional
    public InfoItems getTopBusLinesByTravelling(Locale language, int limit) {
        return getBusLineStatisticsInfoItems(tripHistoryDao.getMostTraveledSchedules(limit), language);
    }

    @Override
    @Transactional
    public InfoItems getOverbookedBusLines(Locale language, int limit) {
        return getBusLineStatisticsInfoItems(tripHistoryDao.getLastFilledTripHistory(limit), language);
    }

    @Override
    @Transactional
    public Long getTotalNumberOfPassengers() {
        return userTravelHistoryDao.getAllUserTravelHistoryCount();
    }

    @Override
    @Transactional
    public Long getTotalNumberOfTrips() {
        return tripHistoryDao.getTripHistoryCount();
    }

    // Utility
    private InfoItems getBusLineStatisticsInfoItems(List<Tuple> dataList, Locale language) {
        InfoItems items = new InfoItems();
        InfoItem item;
        Pair<String, String> cities;
        String lang = LocaleUtility.getLanguage(language);

        for (var data : dataList) {
            item = new InfoItem();
            // get city title
            cities = getCityDescription(((TripHistory) data.get(TUPLE_FIRST_ITEM)), lang);
            // fill info item
            item.addColumn(new InfoItemTextColumn(cities.getFirst()));
            item.addColumn(new InfoItemIconColumn(InfoItemIconType.ARROW_ICON));
            item.addColumn(new InfoItemTextColumn(cities.getSecond()));
            item.addColumn(new InfoItemTextColumn(((TripHistory) data.get(0)).getBusSchedule().getFromTime().format(DateTimeFormatter.ISO_TIME)));
            item.addColumn(new InfoItemTextColumn(data.get(TUPLE_SECOND_ITEM).toString()));
            // add to items
            items.add(item);
        }
        return items;
    }

    private Pair<String, String> getCityDescription(TripHistory tripHistory, String language) {
        var city1 = cityDescriptionDao.getCityDescription(tripHistory.getBusSchedule().getBusLine().getCity1().getId(), language);
        var city2 = cityDescriptionDao.getCityDescription(tripHistory.getBusSchedule().getBusLine().getCity2().getId(), language);
        if (tripHistory.getTripType().getName().equals(TripTypeValues.B_TO_A.name())) {
            return Pair.of(city2.getTitle(), city1.getTitle());
        }
        return Pair.of(city1.getTitle(), city2.getTitle());
    }
}
