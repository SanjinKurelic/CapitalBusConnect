package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.utility.LocaleUtility;
import eu.sanjin.kurelic.cbc.business.viewmodel.ticket.Ticket;
import eu.sanjin.kurelic.cbc.business.viewmodel.ticket.Tickets;
import eu.sanjin.kurelic.cbc.repo.dao.CityDescriptionDao;
import eu.sanjin.kurelic.cbc.repo.dao.TripHistoryDao;
import eu.sanjin.kurelic.cbc.repo.dao.UserTravelHistoryDao;
import eu.sanjin.kurelic.cbc.repo.entity.User;
import eu.sanjin.kurelic.cbc.repo.entity.UserTravelHistory;
import eu.sanjin.kurelic.cbc.repo.values.TripTypeValues;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Locale;

@Service
public class TicketServiceImpl implements TicketService {

    private static final char CODE_SEPARATOR = ':';
    private final TripHistoryDao tripHistoryDao;
    private final UserService userService;
    private final CityDescriptionDao cityDescriptionDao;
    private final UserTravelHistoryDao userTravelHistoryDao;

    public TicketServiceImpl(@Qualifier("tripHistoryDaoImpl") TripHistoryDao tripHistoryDao, @Qualifier("userServiceImpl") UserService userService, @Qualifier("cityDescriptionDaoImpl") CityDescriptionDao cityDescriptionDao, @Qualifier("userTravelHistoryDaoImpl") UserTravelHistoryDao userTravelHistoryDao) {
        this.tripHistoryDao = tripHistoryDao;
        this.userService = userService;
        this.cityDescriptionDao = cityDescriptionDao;
        this.userTravelHistoryDao = userTravelHistoryDao;
    }

    @Override
    @Transactional
    public Ticket getTicket(String username, Locale language, Integer userTravelHistoryId) {
        Ticket ticket = new Ticket();
        // Get data
        var travelHistory = userTravelHistoryDao.getUserTravelHistoryById(userTravelHistoryId);
        var user = userService.getUser(username);
        // Fill data
        if(travelHistory != null && user != null) {
            ticket = getTicket(travelHistory, user, username, LocaleUtility.getLanguage(language));
        }
        return ticket;
    }

    @Override
    @Transactional
    public Tickets getTickets(String username, Locale language, Integer... userTravelHistoryIds) {
        Tickets tickets = new Tickets();
        var travelHistories = userTravelHistoryDao.getUserTravelHistoryById(userTravelHistoryIds);
        var user = userService.getUser(username);
        var lang = LocaleUtility.getLanguage(language);
        // Foreach user travel history
        for(var travelHistory : travelHistories) {
            tickets.add(getTicket(travelHistory, user, username, lang));
        }
        return tickets;
    }

    private Ticket getTicket(UserTravelHistory travelHistory, User user, String username, String language) {
        Ticket ticket = new Ticket();
        if(!travelHistory.getUsername().equals(user.getName())) {
            return ticket;
        }
        // Fill trip info
        ticket.setDate(travelHistory.getTripHistory().getDate());
        ticket.setId(travelHistory.getId());
        ticket.setNumberOfAdults(travelHistory.getNumberOfAdults());
        ticket.setNumberOfChildren(travelHistory.getNumberOfChildren());
        ticket.setTime(travelHistory.getTripHistory().getBusSchedule().getFromTime());
        // Fill cities names and trip direction
        var city1 = cityDescriptionDao.getCityDescription(travelHistory.getTripHistory().getBusSchedule().getBusLine().getCity1().getId(), language);
        var city2 = cityDescriptionDao.getCityDescription(travelHistory.getTripHistory().getBusSchedule().getBusLine().getCity2().getId(), language);
        var tripType = TripTypeValues.valueOf(travelHistory.getTripHistory().getTripType().getName());
        if (tripType == TripTypeValues.B_TO_A) {
            ticket.setFromCity(city2.getTitle());
            ticket.setToCity(city1.getTitle());
        } else {
            ticket.setFromCity(city1.getTitle());
            ticket.setToCity(city2.getTitle());
        }
        ticket.setTripType(tripType);
        // Fill user info
        ticket.setEmail(username);
        ticket.setName(user.getName());
        ticket.setSurname(user.getSurname());
        // Add QR code
        ticket.setCode(getCode(travelHistory, username));
        return ticket;
    }

    /**
     * Build code for QR code => timestamp:id:tripType:username
     */
    private String getCode(UserTravelHistory travelHistory, String username){
        StringBuilder sb = new StringBuilder();
        // timestamp of trip
        var dateTime = LocalDateTime.of(travelHistory.getTripHistory().getDate(), travelHistory.getTripHistory().getBusSchedule().getFromTime());
        sb.append(dateTime.toInstant(ZoneOffset.UTC).toEpochMilli()).append(CODE_SEPARATOR);
        // id of trip
        sb.append(travelHistory.getId()).append(CODE_SEPARATOR);
        // travelType name
        sb.append(travelHistory.getTripHistory().getTripType().getName().toUpperCase()).append(CODE_SEPARATOR);
        // username
        sb.append(username);

        return DigestUtils.md5DigestAsHex(sb.toString().getBytes());
    }
}
