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
class BusLineDaoTest {

  @Autowired
  BusLineDao dao;

  @Test
  void getCityLinesWrongOffset() {
    Assertions.assertThrows(
      IllegalArgumentException.class,
      () -> dao.getCityLines(TestConstant.OFFSET_INVALID, TestConstant.LIMIT_VALID)
    );
  }

  @Test
  void getCityLinesWrongOffsetLarge() {
    Assertions.assertTrue(dao.getCityLines(TestConstant.OFFSET_LARGE, TestConstant.LIMIT_VALID).isEmpty());
  }

  @Test
  void getCityLinesWrongLimit() {
    Assertions.assertThrows(
      IllegalArgumentException.class,
      () -> dao.getCityLines(TestConstant.OFFSET_VALID, TestConstant.LIMIT_INVALID)
    );
  }

  @Test
  void getCityLinesWrongLimitEmpty() {
    Assertions.assertTrue(dao.getCityLines(TestConstant.OFFSET_VALID, TestConstant.LIMIT_ZERO).isEmpty());
  }

  @Test
  void getCityLines() {
    // Database should be filled for this test
    Assertions.assertEquals(
      TestConstant.LIMIT_VALID,
      dao.getCityLines(TestConstant.OFFSET_VALID, TestConstant.LIMIT_VALID).size()
    );
  }

  @Test
  void getNumberOfCityLines() {
    Assertions.assertNotNull(dao.getNumberOfCityLines());
  }
}