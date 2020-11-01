package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.exception.InvalidSuppliedArgumentsException;
import eu.sanjin.kurelic.cbc.business.exception.InvalidUserException;
import eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItems;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public interface LoginHistoryService {

  void addUserLoginHistory(String username, String ipAddress, LocalDateTime dateTime) throws InvalidSuppliedArgumentsException, InvalidUserException;

  @Transactional
  default void addUserLoginHistory(String username, String ipAddress) throws InvalidSuppliedArgumentsException,
    InvalidUserException {
    addUserLoginHistory(username, ipAddress, LocalDateTime.now());
  }

  InfoItems getUserLoginHistory(String username, LocalDate date, int pageNumber, int limit);

  @Transactional
  default InfoItems getUserLoginHistory(String username, int pageNumber, int limit) {
    return getUserLoginHistory(username, null, pageNumber, limit);
  }

  InfoItems getAllLoginHistory(LocalDate date, int pageNumber, int limit);

  @Transactional
  default InfoItems getAllLoginHistory(int pageNumber, int limit) {
    return getAllLoginHistory(null, pageNumber, limit);
  }

  Long getAllLoginHistoryCount();

  Long getUserLoginHistoryCount(String username);

}
