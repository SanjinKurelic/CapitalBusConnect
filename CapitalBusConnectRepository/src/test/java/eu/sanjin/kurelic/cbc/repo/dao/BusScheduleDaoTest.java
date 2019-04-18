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
    void getScheduleWrongId() {
        Assertions.assertNull(dao.getSchedule(TestConstant.ID_INVALID));
    }

    @Test
    void getSchedule() {
        Assertions.assertNotNull(dao.getSchedule(TestConstant.ID_VALID));
    }

    @Test
    void getSchedulesWrongIdNull() {
        Assertions.assertTrue(dao.getSchedules(TestConstant.IDS_NULL).isEmpty());
    }

    @Test
    void getSchedulesWrongIdEmpty() {
        Assertions.assertTrue(dao.getSchedules(TestConstant.IDS_EMPTY).isEmpty());
    }

    @Test
    void getSchedules() {
        Assertions.assertEquals(TestConstant.IDS_VALID_COUNT, dao.getSchedules(TestConstant.IDS_VALID).size());
    }

    @Test
    void getBusLineSchedules() {
        var result = dao.getBusLineSchedules(TestConstant.ID_VALID, TestConstant.ID_VALID_NEXT);
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
        Assertions.assertTrue(dao.getBusLineSchedules(TestConstant.ID_INVALID, TestConstant.ID_VALID).isEmpty());
    }

    @Test
    void getBusLineSchedulesWrongSecondId() {
        Assertions.assertTrue(dao.getBusLineSchedules(TestConstant.ID_VALID, TestConstant.ID_INVALID).isEmpty());
    }
}