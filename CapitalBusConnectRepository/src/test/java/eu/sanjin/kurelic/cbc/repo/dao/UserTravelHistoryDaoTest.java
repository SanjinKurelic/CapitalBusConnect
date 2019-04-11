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
    void getUserTravelHistoryByIdInvalid() {
        Assertions.assertNull(dao.getUserTravelHistoryById(TestConstant.INVALID_ID));
    }

    @Test
    void getUserTravelHistoryByIdValid() {
        Assertions.assertNotNull(dao.getUserTravelHistoryById(TestConstant.VALID_ID));
    }

    @Test
    void getUserTravelHistoryByIdsNull() {
        Assertions.assertEquals(TestConstant.EMPTY_RESULT_SIZE, dao.getUserTravelHistoryByIds().size());
    }

    @Test
    void getUserTravelHistoryByIdsEmpty() {
        Assertions.assertEquals(TestConstant.EMPTY_RESULT_SIZE, dao.getUserTravelHistoryByIds(new Integer[0]).size());
    }

    @Test
    void getUserTravelHistoryByIdsValid() {
        Assertions.assertEquals(TestConstant.VALID_LIST_ID_COUNT, dao.getUserTravelHistoryByIds(TestConstant.VALID_LIST_ID).size());
    }

    @Test
    void getUserTravelHistoryValid() {
        Assertions.assertFalse(dao.getUserTravelHistory(TestConstant.VALID_USERNAME, TestConstant.VALID_OFFSET, TestConstant.VALID_LIMIT).isEmpty());
    }

    @Test
    void getUserTravelHistoryWrongUsername() {
        Assertions.assertEquals(TestConstant.EMPTY_RESULT_SIZE, dao.getUserTravelHistory(TestConstant.INVALID_USERNAME, TestConstant.VALID_OFFSET, TestConstant.VALID_LIMIT).size());
    }

    @Test
    void getUserTravelHistoryWrongOffset() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> dao.getUserTravelHistory(TestConstant.VALID_USERNAME, TestConstant.INVALID_OFFSET, TestConstant.VALID_LIMIT));
    }

    @Test
    void getUserTravelHistoryWrongLimit() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> dao.getUserTravelHistory(TestConstant.VALID_USERNAME, TestConstant.VALID_OFFSET, TestConstant.INVALID_LIMIT));
    }

    @Test
    void getUserTravelHistoryByDateValid() {
        Assertions.assertFalse(dao.getUserTravelHistory(TestConstant.VALID_USERNAME, TestConstant.VALID_DATE_TRIP, TestConstant.VALID_OFFSET, TestConstant.VALID_LIMIT).isEmpty());
    }

    @Test
    void getUserTravelHistoryByDateWrongUser() {
        Assertions.assertEquals(TestConstant.EMPTY_RESULT_SIZE, dao.getUserTravelHistory(TestConstant.INVALID_USERNAME, TestConstant.VALID_DATE_TRIP, TestConstant.VALID_OFFSET, TestConstant.VALID_LIMIT).size());
    }

    @Test
    void getUserTravelHistoryByDateWrongDate() {
        Assertions.assertEquals(TestConstant.EMPTY_RESULT_SIZE, dao.getUserTravelHistory(TestConstant.VALID_USERNAME, TestConstant.INVALID_DATE, TestConstant.VALID_OFFSET, TestConstant.VALID_LIMIT).size());
    }

    @Test
    void getUserTravelHistoryByDateWrongOffset() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> dao.getUserTravelHistory(TestConstant.VALID_USERNAME, TestConstant.VALID_DATE_TRIP, TestConstant.INVALID_OFFSET, TestConstant.VALID_LIMIT));
    }

    @Test
    void getUserTravelHistoryByDateWrongLimit() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> dao.getUserTravelHistory(TestConstant.VALID_USERNAME, TestConstant.VALID_DATE_TRIP, TestConstant.VALID_OFFSET, TestConstant.INVALID_LIMIT));
    }

    @Test
    void getTopUsersByTravels() {
        Assertions.assertNotNull(dao.getTopUsersByTravels(10));
    }

    @Test
    void getUserTravelHistoryCount() {
        Assertions.assertNotNull(dao.getAllUserTravelHistoryCount());
    }
}