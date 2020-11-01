package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.configuration.ServiceConfiguration;
import eu.sanjin.kurelic.cbc.business.services.utility.TestConstant;
import eu.sanjin.kurelic.cbc.repo.configuration.RepositoryConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceConfiguration.class, RepositoryConfiguration.class})
class TicketServiceTest {

  @Autowired
  private TicketService service;

  @Test
  void getTicketWrongId() {
    Assertions.assertNull(
      service.getTicket(TestConstant.USERNAME_VALID, TestConstant.LANGUAGE_VALID, TestConstant.ID_INVALID)
    );
  }

  @Test
  void getTicketWrongIdNull() {
    Assertions.assertNull(
      service.getTicket(TestConstant.USERNAME_VALID, TestConstant.LANGUAGE_VALID, TestConstant.ID_NULL)
    );
  }

  @Test
  void getTicketWrongLanguageNull() {
    Assertions.assertNull(
      service.getTicket(TestConstant.USERNAME_VALID, TestConstant.LANGUAGE_NULL, TestConstant.ID_VALID)
    );
  }

  @Test
  void getTicketWrongLanguage() {
    Assertions.assertNull(
      service.getTicket(TestConstant.USERNAME_VALID, TestConstant.LANGUAGE_INVALID, TestConstant.ID_VALID)
    );
  }

  @Test
  void getTicketWrongUsernameEmpty() {
    Assertions.assertNull(
      service.getTicket(TestConstant.USERNAME_EMPTY, TestConstant.LANGUAGE_VALID, TestConstant.ID_VALID)
    );
  }

  @Test
  void getTicketWrongUsernameNull() {
    Assertions.assertNull(
      service.getTicket(TestConstant.USERNAME_NULL, TestConstant.LANGUAGE_VALID, TestConstant.ID_VALID)
    );
  }

  @Test
  void getTicketWrongUsername() {
    Assertions.assertNull(
      service.getTicket(TestConstant.USERNAME_INVALID, TestConstant.LANGUAGE_VALID, TestConstant.ID_VALID)
    );
  }

  @Test
  void getTicketViolatedSecurity() {
    // Username is valid but ticket is not registered for this user
    Assertions.assertNull(
      service.getTicket(TestConstant.USERNAME_ADMIN, TestConstant.LANGUAGE_VALID, TestConstant.ID_VALID)
    );
  }

  @Test
  void getTicket() {
    Assertions.assertNotNull(
      service.getTicket(TestConstant.USERNAME_VALID, TestConstant.LANGUAGE_VALID, TestConstant.ID_VALID)
    );
  }

}