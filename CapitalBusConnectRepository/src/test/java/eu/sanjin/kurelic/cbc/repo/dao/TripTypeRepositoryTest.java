package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.configuration.RepositoryConfiguration;
import eu.sanjin.kurelic.cbc.repo.dao.utility.TestConstant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RepositoryConfiguration.class})
@Transactional
class TripTypeRepositoryTest {

  @Autowired
  private TripTypeRepository tripTypeRepository;

  @Test
  void getTripTypeWrongType() {
    Assertions.assertNull(tripTypeRepository.findByTripTypeValue(TestConstant.TRIP_TYPE_NULL));
  }

  @Test
  void getTripType() {
    Assertions.assertNotNull(tripTypeRepository.findByTripTypeValue(TestConstant.TRIP_TYPE));
  }
}