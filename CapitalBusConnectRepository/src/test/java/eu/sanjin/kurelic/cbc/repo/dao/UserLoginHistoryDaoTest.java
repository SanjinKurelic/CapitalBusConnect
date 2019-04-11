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
class UserLoginHistoryDaoTest {

    @Autowired
    private UserLoginHistoryDao dao;

    @Test
    void getUserLoginHistoryValidUser() {
        Assertions.assertNotNull(dao.getUserLoginHistory(TestConstant.VALID_USERNAME, TestConstant.VALID_OFFSET, TestConstant.VALID_LIMIT));
    }

    @Test
    void getUserLoginHistoryInvalidUser() {
        Assertions.assertEquals(TestConstant.EMPTY_RESULT_SIZE, dao.getUserLoginHistory(TestConstant.INVALID_USERNAME, TestConstant.VALID_OFFSET, TestConstant.VALID_LIMIT).size());
    }

    @Test
    void getUserLoginHistoryByDateValid() {
        Assertions.assertFalse(dao.getUserLoginHistory(
                TestConstant.VALID_USERNAME,
                TestConstant.VALID_DATE_LOGIN,
                TestConstant.VALID_OFFSET,
                TestConstant.VALID_LIMIT
        ).isEmpty());
    }

    @Test
    void getUserLoginHistoryByDateWrongDate() {
        Assertions.assertEquals(TestConstant.EMPTY_RESULT_SIZE, dao.getUserLoginHistory(
                TestConstant.VALID_USERNAME,
                TestConstant.INVALID_DATE,
                TestConstant.VALID_OFFSET,
                TestConstant.VALID_LIMIT
        ).size());
    }

    @Test
    void getUserLoginHistoryByDateWrongUsername() {
        Assertions.assertEquals(TestConstant.EMPTY_RESULT_SIZE, dao.getUserLoginHistory(
                TestConstant.INVALID_USERNAME,
                TestConstant.VALID_DATE_LOGIN,
                TestConstant.VALID_OFFSET,
                TestConstant.VALID_LIMIT
        ).size());
    }

    @Test
    void getAllLoginHistoryValid() {
        Assertions.assertNotNull(dao.getAllLoginHistory(TestConstant.VALID_OFFSET, TestConstant.VALID_LIMIT));
    }

    @Test
    void getAllLoginHistoryWrongOffset() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> dao.getAllLoginHistory(TestConstant.INVALID_OFFSET, TestConstant.VALID_LIMIT));
    }

    @Test
    void getAllLoginHistoryWrongLimit() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> dao.getAllLoginHistory(TestConstant.VALID_OFFSET, TestConstant.INVALID_LIMIT));
    }

    @Test
    void getAllLoginHistoryByDateValid() {
        Assertions.assertFalse(dao.getAllLoginHistory(TestConstant.VALID_DATE_LOGIN, TestConstant.VALID_OFFSET, TestConstant.VALID_LIMIT).isEmpty());
    }

    @Test
    void getAllLoginHistoryByDateInvalid() {
        Assertions.assertEquals(TestConstant.EMPTY_RESULT_SIZE, dao.getAllLoginHistory(TestConstant.INVALID_DATE, TestConstant.VALID_OFFSET, TestConstant.VALID_LIMIT).size());
    }

    @Test
    void getUserLoginHistoryCountValidUser() {
        Assertions.assertNotNull(dao.getUserLoginHistoryCount(TestConstant.VALID_USERNAME));
    }

    @Test
    void getUserLoginHistoryCountInvalidUser() {
        Assertions.assertNotNull(dao.getUserLoginHistoryCount(TestConstant.INVALID_USERNAME));
    }

    @Test
    void getAllLoginHistoryCount() {
        Assertions.assertNotNull(dao.getAllLoginHistoryCount());
    }
}