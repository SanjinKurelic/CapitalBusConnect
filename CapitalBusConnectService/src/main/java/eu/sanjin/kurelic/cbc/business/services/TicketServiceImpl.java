package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.utility.LocaleUtility;
import eu.sanjin.kurelic.cbc.business.viewmodel.ticket.Ticket;
import eu.sanjin.kurelic.cbc.business.viewmodel.ticket.Tickets;
import eu.sanjin.kurelic.cbc.repo.dao.DestinationInfoDao;
import eu.sanjin.kurelic.cbc.repo.dao.TravelHistoryDao;
import eu.sanjin.kurelic.cbc.repo.entity.User;
import eu.sanjin.kurelic.cbc.repo.entity.UserTravelHistory;
import eu.sanjin.kurelic.cbc.repo.values.TripTypeValues;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Locale;

@Service
public class TicketServiceImpl implements TicketService {

    private final TravelHistoryDao travelHistoryDao;
    private final UserService userService;
    private DestinationInfoDao destinationInfoDao;

    public TicketServiceImpl(@Qualifier("travelHistoryDaoImpl") TravelHistoryDao travelHistoryDao, @Qualifier("userServiceImpl") UserService userService) {
        this.travelHistoryDao = travelHistoryDao;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Ticket getTicket(String username, Locale language, Integer userTravelHistoryId) {
        Ticket ticket = new Ticket();
        // Get data
        var travelHistory = travelHistoryDao.getUserTravelHistoryById(userTravelHistoryId);
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
        var travelHistories = travelHistoryDao.getUserTravelHistoryById(userTravelHistoryIds);
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
        // Fill trip info
        ticket.setDate(travelHistory.getTripHistory().getDate());
        ticket.setId(travelHistory.getId());
        ticket.setNumberOfAdults(travelHistory.getNumberOfAdults());
        ticket.setNumberOfChildren(travelHistory.getNumberOfChildren());
        ticket.setTime(travelHistory.getTripHistory().getBusSchedule().getFromTime());
        // Fill cities names and trip direction
        var city1 = destinationInfoDao.getCityDescription(travelHistory.getTripHistory().getBusSchedule().getBusLine().getCity1().getId(), language);
        var city2 = destinationInfoDao.getCityDescription(travelHistory.getTripHistory().getBusSchedule().getBusLine().getCity2().getId(), language);
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
        return ticket;
    }
}
