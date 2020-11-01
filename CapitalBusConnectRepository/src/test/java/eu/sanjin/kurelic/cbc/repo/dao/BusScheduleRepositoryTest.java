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
class BusScheduleRepositoryTest {

  @Autowired
  private BusScheduleRepository busScheduleRepository;

  @Test
  void getScheduleWrongId() {
    Assertions.assertNull(busScheduleRepository.getById(TestConstant.ID_INVALID));
  }

  @Test
  void getSchedule() {
    Assertions.assertNotNull(busScheduleRepository.getById(TestConstant.ID_VALID));
  }

  @Test
  void getSchedulesWrongIdNull() {
    Assertions.assertThrows(
      IllegalArgumentException.class,
      () -> busScheduleRepository.getByIdIn(TestConstant.IDS_NULL)
    );
  }

  @Test
  void getSchedulesWrongIdEmpty() {
    Assertions.assertTrue(busScheduleRepository.getByIdIn(TestConstant.IDS_EMPTY).isEmpty());
  }

  @Test
  void getSchedules() {
    Assertions.assertEquals(TestConstant.IDS_VALID_COUNT, busScheduleRepository.getByIdIn(TestConstant.IDS_VALID).size());
  }

  @Test
  void getBusLineSchedules() {
    var result = busScheduleRepository.getBusLineSchedules(TestConstant.ID_VALID, TestConstant.ID_VALID_NEXT);
    // If we have multiple results in database, every one of them has to point on same BusLine id
    if (result.size() > 1) {
      int firstId = result.get(0).getBusLine().getId();
      for (int i = 1, j = result.size(); i < j; i++) {
        Assertions.assertEquals(firstId, result.get(i).getBusLine().getId());
      }
    }
  }

  @Test
  void getBusLineSchedulesWrongFirstId() {
    Assertions.assertTrue(busScheduleRepository.getBusLineSchedules(TestConstant.ID_INVALID, TestConstant.ID_VALID).isEmpty());
  }

  @Test
  void getBusLineSchedulesWrongSecondId() {
    Assertions.assertTrue(busScheduleRepository.getBusLineSchedules(TestConstant.ID_VALID, TestConstant.ID_INVALID).isEmpty());
  }
}