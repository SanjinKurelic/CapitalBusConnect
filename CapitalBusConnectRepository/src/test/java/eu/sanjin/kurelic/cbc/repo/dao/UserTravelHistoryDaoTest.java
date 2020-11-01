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
class UserTravelHistoryDaoTest {

  @Autowired
  private UserTravelHistoryDao dao;

  @Test
  void getUserTravelHistoryByIdWrongId() {
    Assertions.assertNull(dao.getUserTravelHistoryById(TestConstant.ID_INVALID));
  }

  @Test
  void getUserTravelHistoryById() {
    Assertions.assertNotNull(dao.getUserTravelHistoryById(TestConstant.ID_VALID));
  }

  @Test
  void getUserTravelHistoryByIdsNull() {
    Assertions.assertTrue(dao.getUserTravelHistoryByIds(TestConstant.IDS_NULL).isEmpty());
  }

  @Test
  void getUserTravelHistoryByIdsEmpty() {
    Assertions.assertTrue(dao.getUserTravelHistoryByIds(TestConstant.IDS_EMPTY).isEmpty());
  }

  @Test
  void getUserTravelHistoryByIds() {
    Assertions.assertEquals(
      TestConstant.IDS_VALID_COUNT,
      dao.getUserTravelHistoryByIds(TestConstant.IDS_VALID).size()
    );
  }

  @Test
  void getUserTravelHistory() {
    Assertions.assertFalse(dao.getUserTravelHistory(
      TestConstant.USERNAME_VALID,
      TestConstant.OFFSET_VALID,
      TestConstant.LIMIT_VALID
    ).isEmpty());
  }

  @Test
  void getUserTravelHistoryWrongUsername() {
    Assertions.assertTrue(dao.getUserTravelHistory(
      TestConstant.USERNAME_INVALID,
      TestConstant.OFFSET_VALID,
      TestConstant.LIMIT_VALID
    ).isEmpty());
  }

  @Test
  void getUserTravelHistoryWrongOffset() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> dao.getUserTravelHistory(
      TestConstant.USERNAME_VALID,
      TestConstant.OFFSET_INVALID,
      TestConstant.LIMIT_VALID
    ));
  }

  @Test
  void getUserTravelHistoryWrongLimit() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> dao.getUserTravelHistory(
      TestConstant.USERNAME_VALID,
      TestConstant.OFFSET_VALID,
      TestConstant.LIMIT_INVALID
    ));
  }

  @Test
  void getUserTravelHistoryByDate() {
    Assertions.assertFalse(dao.getUserTravelHistory(
      TestConstant.USERNAME_VALID,
      TestConstant.DATE_TRIP_VALID,
      TestConstant.OFFSET_VALID,
      TestConstant.LIMIT_VALID
    ).isEmpty());
  }

  @Test
  void getUserTravelHistoryByDateWrongUser() {
    Assertions.assertTrue(dao.getUserTravelHistory(
      TestConstant.USERNAME_INVALID,
      TestConstant.DATE_TRIP_VALID,
      TestConstant.OFFSET_VALID,
      TestConstant.LIMIT_VALID
    ).isEmpty());
  }

  @Test
  void getUserTravelHistoryByDateWrongDate() {
    Assertions.assertTrue(dao.getUserTravelHistory(
      TestConstant.USERNAME_VALID,
      TestConstant.DATE_INVALID,
      TestConstant.OFFSET_VALID,
      TestConstant.LIMIT_VALID
    ).isEmpty());
  }

  @Test
  void getUserTravelHistoryByDateWrongOffset() {
    Assertions.assertThrows(
      IllegalArgumentException.class,
      () -> dao.getUserTravelHistory(
        TestConstant.USERNAME_VALID,
        TestConstant.DATE_TRIP_VALID,
        TestConstant.OFFSET_INVALID,
        TestConstant.LIMIT_VALID
      )
    );
  }

  @Test
  void getUserTravelHistoryByDateWrongLimit() {
    Assertions.assertThrows(
      IllegalArgumentException.class,
      () -> dao.getUserTravelHistory(
        TestConstant.USERNAME_VALID,
        TestConstant.DATE_TRIP_VALID,
        TestConstant.OFFSET_VALID,
        TestConstant.LIMIT_INVALID
      )
    );
  }

  @Test
  void getTopUsersByTravelsWrongLimit() {
    Assertions.assertThrows(IllegalArgumentException.class,
      () -> dao.getTopUsersByTravels(TestConstant.LIMIT_INVALID));
  }

  @Test
  void getTopUsersByTravels() {
    Assertions.assertNotNull(dao.getTopUsersByTravels(TestConstant.LIMIT_VALID));
  }

  @Test
  void getUserTravelHistoryCount() {
    Assertions.assertNotNull(dao.getAllUserTravelHistoryCount());
  }
}