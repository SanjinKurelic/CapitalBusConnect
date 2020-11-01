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
class BusLineRepositoryTest {

  @Autowired
  BusLineRepository dao;

  @Test
  void getCityLinesWrongOffset() {
    Assertions.assertThrows(
      IllegalArgumentException.class,
      () -> dao.findAll(PageRequest.of(TestConstant.OFFSET_INVALID, TestConstant.LIMIT_VALID))
    );
  }

  @Test
  void getCityLinesWrongOffsetLarge() {
    Assertions.assertTrue(dao.findAll(PageRequest.of(TestConstant.OFFSET_LARGE, TestConstant.LIMIT_VALID)).isEmpty());
  }

  @Test
  void getCityLinesWrongLimit() {
    Assertions.assertThrows(
      IllegalArgumentException.class,
      () -> dao.findAll(PageRequest.of(TestConstant.OFFSET_VALID, TestConstant.LIMIT_INVALID))
    );
  }

  @Test
  void getCityLinesWrongLimitEmpty() {
    Assertions.assertThrows(
      IllegalArgumentException.class,
      () -> dao.findAll(PageRequest.of(TestConstant.OFFSET_VALID, TestConstant.LIMIT_ZERO))
    );
  }

  @Test
  void getCityLines() {
    // Database should be filled for this test
    Assertions.assertEquals(
      TestConstant.LIMIT_VALID,
      dao.findAll(PageRequest.of(TestConstant.OFFSET_VALID, TestConstant.LIMIT_VALID)).getContent().size()
    );
  }
}