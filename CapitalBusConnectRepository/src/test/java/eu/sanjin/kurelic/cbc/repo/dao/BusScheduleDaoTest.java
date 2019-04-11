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
class BusScheduleDaoTest {

    @Autowired
    private BusScheduleDao dao;

    @Test
    void getScheduleInvalid() {
        Assertions.assertNull(dao.getSchedule(TestConstant.INVALID_ID));
    }

    @Test
    void getScheduleValid() {
        Assertions.assertNotNull(dao.getSchedule(TestConstant.VALID_ID));
    }

    @Test
    void getSchedulesNull() {
        Assertions.assertEquals(TestConstant.EMPTY_RESULT_SIZE, dao.getSchedules().size());
    }

    @Test
    void getSchedulesEmpty() {
        Assertions.assertEquals(TestConstant.EMPTY_RESULT_SIZE, dao.getSchedules((new Integer[0])).size());
    }

    @Test
    void getSchedulesValid() {
        Assertions.assertEquals(TestConstant.VALID_LIST_ID_COUNT, dao.getSchedules(TestConstant.VALID_LIST_ID).size());
    }

    @Test
    void getBusLineSchedulesValidIds() {
        var result = dao.getBusLineSchedules(TestConstant.VALID_ID, TestConstant.VALID_NEXT_ID);
        // If we have multiple results in database, every one of them has to point on same BusLine id
        if (result.size() > 1) {
            int firstId = result.get(0).getBusLine().getId();
            for (int i = 1, j = result.size(); i < j; i++) {
                Assertions.assertEquals(firstId, result.get(i).getBusLine().getId());
            }
        }
    }

    @Test
    void getBusLineSchedulesInvalidFirstId() {
        Assertions.assertEquals(TestConstant.EMPTY_RESULT_SIZE, dao.getBusLineSchedules(TestConstant.INVALID_ID, TestConstant.VALID_ID).size());
    }

    @Test
    void getBusLineSchedulesInvalidSecondId() {
        Assertions.assertEquals(TestConstant.EMPTY_RESULT_SIZE, dao.getBusLineSchedules(TestConstant.VALID_ID, TestConstant.INVALID_ID).size());
    }
}