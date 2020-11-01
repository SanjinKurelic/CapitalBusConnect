package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.utility.LocaleUtility;
import eu.sanjin.kurelic.cbc.business.viewmodel.ticket.Ticket;
import eu.sanjin.kurelic.cbc.repo.dao.CityDescriptionDao;
import eu.sanjin.kurelic.cbc.repo.dao.UserTravelHistoryDao;
import eu.sanjin.kurelic.cbc.repo.entity.User;
import eu.sanjin.kurelic.cbc.repo.entity.UserTravelHistory;
import eu.sanjin.kurelic.cbc.repo.values.TripTypeValues;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Locale;
import java.util.Objects;

@Service
public class TicketServiceImpl implements TicketService {

  private static final char CODE_SEPARATOR = ':';
  private final UserService userService;
  private final CityDescriptionDao cityDescriptionDao;
  private final UserTravelHistoryDao userTravelHistoryDao;

  public TicketServiceImpl(UserService userService, CityDescriptionDao cityDescriptionDao,
                           UserTravelHistoryDao userTravelHistoryDao) {
    this.userService = userService;
    this.cityDescriptionDao = cityDescriptionDao;
    this.userTravelHistoryDao = userTravelHistoryDao;
  }

  @Override
  @Transactional
  public Ticket getTicket(String username, Locale language, Integer userTravelHistoryId) {
    // Check
    if (Objects.isNull(username) || username.isBlank() || Objects.isNull(language) || Objects.isNull(userTravelHistoryId)) {
      return null;
    }
    // Logic
    var travelHistory = userTravelHistoryDao.getUserTravelHistoryById(userTravelHistoryId);
    var user = userService.getUser(username);
    // Fill data
    if (Objects.isNull(travelHistory) || Objects.isNull(user)) {
      return null;
    }
    return getTicket(travelHistory, user, LocaleUtility.getLanguage(language));
  }

  private Ticket getTicket(UserTravelHistory travelHistory, User user, String language) {
    Ticket ticket = new Ticket();
    // Security check - if this users is not ticket user
    if (!travelHistory.getUsername().equals(user.getUsername())) {
      return null;
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
    // Wrong language
    if (Objects.isNull(city1) || Objects.isNull(city2)) {
      return null;
    }
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
    ticket.setEmail(user.getUsername());
    ticket.setName(user.getName());
    ticket.setSurname(user.getSurname());
    // Add QR code
    ticket.setCode(getCode(travelHistory, user.getUsername()));
    return ticket;
  }

  /**
   * Build code for QR code => timestamp:id:tripType:username
   */
  private String getCode(UserTravelHistory travelHistory, String username) {
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
