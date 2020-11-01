package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.exception.InvalidSuppliedArgumentsException;
import eu.sanjin.kurelic.cbc.business.exception.InvalidUserException;
import eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItemButtonColumn;
import eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItemButtonType;
import eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItemTextColumn;
import eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItems;
import eu.sanjin.kurelic.cbc.repo.dao.UserLoginHistoryDao;
import eu.sanjin.kurelic.cbc.repo.entity.UserLoginHistory;
import eu.sanjin.kurelic.cbc.repo.entity.composite.LoginHistoryPrimaryKey;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
public class LoginHistoryServiceImpl implements LoginHistoryService {

  private static final String LOGIN_INFORMATION_PAIR = "Pair username-ipAddress: ['%s','%s']";
  private final UserLoginHistoryDao loginHistory;
  private final UserService userService;

  public LoginHistoryServiceImpl(UserLoginHistoryDao loginHistory, UserService userService) {
    this.loginHistory = loginHistory;
    this.userService = userService;
  }

  @Override
  @Transactional
  public void addUserLoginHistory(String username, String ipAddress, LocalDateTime dateTime) throws InvalidSuppliedArgumentsException, InvalidUserException {
    // Check
    if (Objects.isNull(username) || username.isBlank() || Objects.isNull(ipAddress) || ipAddress.isBlank() || Objects.isNull(dateTime)) {
      throw new InvalidSuppliedArgumentsException(String.format(LOGIN_INFORMATION_PAIR, username, ipAddress));
    }
    // Logic
    UserLoginHistory userLoginHistory = new UserLoginHistory();
    var user = userService.getUser(username); // Can throw
    if (Objects.isNull(user)) {
      throw new InvalidUserException(String.format(LOGIN_INFORMATION_PAIR, username, ipAddress));
    }
    userLoginHistory.setId(new LoginHistoryPrimaryKey(user, dateTime));
    userLoginHistory.setIpAddress(ipAddress);
    loginHistory.addUserLoginHistory(userLoginHistory); // Can throw
  }

  @Override
  @Transactional
  public InfoItems getUserLoginHistory(String username, LocalDate date, int pageNumber, int limit) {
    List<UserLoginHistory> loginHistories;
    // Check
    if (Objects.isNull(username) || username.isBlank() || limit < 1) {
      return new InfoItems();
    }
    // Page number
    pageNumber = (pageNumber - 1) * limit;
    if (pageNumber < 0) {
      return new InfoItems();
    }
    // Get login history
    if (Objects.isNull(date)) {
      loginHistories = loginHistory.getUserLoginHistory(username, pageNumber, limit);
    } else {
      loginHistories = loginHistory.getUserLoginHistory(username, date, pageNumber, limit);
    }
    // Convert to info items
    return convertHistoryToInfoItems(loginHistories);
  }

  @Override
  @Transactional
  public InfoItems getAllLoginHistory(LocalDate date, int pageNumber, int limit) {
    List<UserLoginHistory> loginHistories;
    // Check
    if (limit < 1) {
      return new InfoItems();
    }
    // Page number
    pageNumber = (pageNumber - 1) * limit;
    if (pageNumber < 0) {
      return new InfoItems();
    }
    // Logic
    if (Objects.isNull(date)) {
      loginHistories = loginHistory.getAllLoginHistory(pageNumber, limit);
    } else {
      loginHistories = loginHistory.getAllLoginHistory(date, pageNumber, limit);
    }
    // Convert to info items
    return convertHistoryToInfoItems(loginHistories);
  }

  @Override
  @Transactional
  public Long getAllLoginHistoryCount() {
    return loginHistory.getAllLoginHistoryCount();
  }

  @Override
  @Transactional
  public Long getUserLoginHistoryCount(String username) {
    // Check
    if (Objects.isNull(username) || username.isBlank()) {
      return 0L;
    }
    // Logic
    return loginHistory.getUserLoginHistoryCount(username);
  }

  private InfoItems convertHistoryToInfoItems(List<UserLoginHistory> loginHistories) {
    InfoItems items = new InfoItems();
    InfoItem item;
    for (UserLoginHistory loginHistory : loginHistories) {
      item = new InfoItem();
      item.addColumn(new InfoItemTextColumn(loginHistory.getId().getUsername().getUsername()));
      item.addColumn(new InfoItemTextColumn(loginHistory.getId().getDateTime().format(DateTimeFormatter.ISO_DATE_TIME).replace('T', ' ')));
      item.addColumn(new InfoItemTextColumn(loginHistory.getIpAddress()));
      item.addColumn(new InfoItemButtonColumn(InfoItemButtonType.BUY_INFO,
        loginHistory.getId().getUsername().getUsername()));
      // Add to items
      items.add(item);
    }
    return items;
  }

}
