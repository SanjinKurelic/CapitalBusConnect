package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.configuration.RepositoryConfiguration;
import eu.sanjin.kurelic.cbc.repo.values.PayingMethodValues;
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
class PayingMethodDaoTest {

  @Autowired
  private PayingMethodDao dao;

  @Test
  void getPayingMethodByName() {
    Assertions.assertEquals(
      PayingMethodValues.MONEY.name(),
      dao.getPayingMethodByName(PayingMethodValues.MONEY).getName()
    );
  }
}