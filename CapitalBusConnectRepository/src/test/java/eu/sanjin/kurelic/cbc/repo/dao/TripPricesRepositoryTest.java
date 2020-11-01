package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.configuration.RepositoryConfiguration;
import eu.sanjin.kurelic.cbc.repo.dao.utility.TestConstant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RepositoryConfiguration.class})
@Transactional
class TripPricesRepositoryTest {

  @Autowired
  private TripPricesRepository tripPricesRepository;

  @Test
  void getTripPriceWrongDate() {
    Assertions.assertThrows(
      IllegalArgumentException.class,
      () -> tripPricesRepository.findFirstByTripDurationLessThanEqualAndFromDateLessThanEqualOrderByTripDurationDescFromDateDesc(TestConstant.TRIP_DURATION_VALID, TestConstant.DATE_INVALID)
    );
  }

  @Test
  void getTripPriceWrongDuration() {
    Assertions.assertNull(tripPricesRepository.findFirstByTripDurationLessThanEqualAndFromDateLessThanEqualOrderByTripDurationDescFromDateDesc(TestConstant.TRIP_DURATION_INVALID));
  }

  @Test
  void getTripPriceValid() {
    Assertions.assertNotNull(tripPricesRepository.findFirstByTripDurationLessThanEqualAndFromDateLessThanEqualOrderByTripDurationDescFromDateDesc(TestConstant.TRIP_DURATION_VALID));
  }
}