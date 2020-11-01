package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.configuration.RepositoryConfiguration;
import eu.sanjin.kurelic.cbc.repo.dao.utility.TestConstant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RepositoryConfiguration.class})
@Transactional
class TrafficDescriptionRepositoryTest {

  @Autowired
  private TrafficDescriptionRepository trafficDescriptionRepository;

  @Test
  void getTrafficDescriptionsWrongLanguageNull() {
    Assertions.assertTrue(trafficDescriptionRepository.findByIdLanguageOrderByTrafficDateAsc(
      TestConstant.LANGUAGE_NULL,
      PageRequest.of(0, TestConstant.LIMIT_VALID)
    ).isEmpty());
  }

  @Test
  void getTrafficDescriptionsWrongLanguage() {
    Assertions.assertTrue(trafficDescriptionRepository.findByIdLanguageOrderByTrafficDateAsc(
      TestConstant.LANGUAGE_EMPTY,
      PageRequest.of(0, TestConstant.LIMIT_VALID)
    ).isEmpty());
  }

  @Test
  void getTrafficDescriptionsWrongLimit() {
    Assertions.assertThrows(
      IllegalArgumentException.class,
      () -> trafficDescriptionRepository.findByIdLanguageOrderByTrafficDateAsc(
        TestConstant.LANGUAGE_VALID,
        PageRequest.of(0, TestConstant.LIMIT_INVALID)
      )
    );
  }

  @Test
  void getTrafficDescriptions() {
    // require at least one database record
    Assertions.assertFalse(trafficDescriptionRepository.findByIdLanguageOrderByTrafficDateAsc(
      TestConstant.LANGUAGE_VALID,
      PageRequest.of(0, TestConstant.LIMIT_VALID)
    ).isEmpty());
  }
}