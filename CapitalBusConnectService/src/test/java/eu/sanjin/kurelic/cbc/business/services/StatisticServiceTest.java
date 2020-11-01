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
class StatisticServiceTest {

  @Autowired
  private StatisticService service;

  @Test
  void getTopUsersByTravelsWrongLimit() {
    Assertions.assertTrue(service.getTopUsersByTravels(TestConstant.LIMIT_INVALID).isEmpty());
  }

  @Test
  void getTopUsersByTravels() {
    // Require filled database
    Assertions.assertFalse(service.getTopUsersByTravels(TestConstant.LIMIT_VALID).isEmpty());
  }

  @Test
  void getTopBusLinesByTravellingWrongLanguageNull() {
    Assertions.assertTrue(
      service.getTopBusLinesByTravelling(TestConstant.LANGUAGE_NULL, TestConstant.LIMIT_VALID).isEmpty()
    );
  }

  @Test
  void getTopBusLinesByTravellingWrongLanguage() {
    Assertions.assertTrue(
      service.getTopBusLinesByTravelling(TestConstant.LANGUAGE_INVALID, TestConstant.LIMIT_VALID).isEmpty()
    );
  }

  @Test
  void getTopBusLinesByTravellingWrongLimit() {
    Assertions.assertTrue(
      service.getTopBusLinesByTravelling(TestConstant.LANGUAGE_VALID, TestConstant.LIMIT_INVALID).isEmpty()
    );
  }

  @Test
  void getTopBusLinesByTravelling() {
    Assertions.assertFalse(
      service.getTopBusLinesByTravelling(TestConstant.LANGUAGE_VALID, TestConstant.LIMIT_VALID).isEmpty()
    );
  }

  @Test
  void getOverbookedBusLinesWrongLanguage() {
    Assertions.assertTrue(
      service.getOverbookedBusLines(TestConstant.LANGUAGE_INVALID, TestConstant.LIMIT_VALID).isEmpty()
    );
  }

  @Test
  void getOverbookedBusLinesWrongLanguageNull() {
    Assertions.assertTrue(
      service.getOverbookedBusLines(TestConstant.LANGUAGE_NULL, TestConstant.LIMIT_VALID).isEmpty()
    );
  }

  @Test
  void getOverbookedBusLinesWrongLimit() {
    Assertions.assertTrue(
      service.getOverbookedBusLines(TestConstant.LANGUAGE_VALID, TestConstant.LIMIT_INVALID).isEmpty()
    );
  }

  @Test
  void getOverbookedBusLines() {
    Assertions.assertFalse(
      service.getOverbookedBusLines(TestConstant.LANGUAGE_VALID, TestConstant.LIMIT_VALID).isEmpty()
    );
  }

  @Test
  void getTotalNumberOfPassengers() {
    Assertions.assertNotNull(service.getTotalNumberOfPassengers());
  }

  @Test
  void getTotalNumberOfTrips() {
    Assertions.assertNotNull(service.getTotalNumberOfTrips());
  }
}