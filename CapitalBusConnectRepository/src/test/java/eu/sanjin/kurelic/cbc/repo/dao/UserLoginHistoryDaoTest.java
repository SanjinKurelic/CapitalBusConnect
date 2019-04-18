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
    void getUserLoginHistory() {
        Assertions.assertNotNull(dao.getUserLoginHistory(
                TestConstant.USERNAME_VALID,
                TestConstant.OFFSET_VALID,
                TestConstant.LIMIT_VALID
        ));
    }

    @Test
    void getUserLoginHistoryWrongUser() {
        Assertions.assertTrue(dao.getUserLoginHistory(
                TestConstant.USERNAME_INVALID,
                TestConstant.OFFSET_VALID,
                TestConstant.LIMIT_VALID
        ).isEmpty());
    }

    @Test
    void getUserLoginHistoryWrongUserNull() {
        Assertions.assertTrue(dao.getUserLoginHistory(
                TestConstant.USERNAME_NULL,
                TestConstant.OFFSET_VALID,
                TestConstant.LIMIT_VALID
        ).isEmpty());
    }

    @Test
    void getUserLoginHistoryByDate() {
        Assertions.assertFalse(dao.getUserLoginHistory(
                TestConstant.USERNAME_VALID,
                TestConstant.DATE_LOGIN_VALID,
                TestConstant.OFFSET_VALID,
                TestConstant.LIMIT_VALID
        ).isEmpty());
    }

    @Test
    void getUserLoginHistoryByDateWrongDate() {
        Assertions.assertTrue(dao.getUserLoginHistory(
                TestConstant.USERNAME_VALID,
                TestConstant.DATE_INVALID,
                TestConstant.OFFSET_VALID,
                TestConstant.LIMIT_VALID
        ).isEmpty());
    }

    @Test
    void getUserLoginHistoryByDateWrongUsernameNull() {
        Assertions.assertTrue(dao.getUserLoginHistory(
                TestConstant.USERNAME_NULL,
                TestConstant.DATE_LOGIN_VALID,
                TestConstant.OFFSET_VALID,
                TestConstant.LIMIT_VALID
        ).isEmpty());
    }

    @Test
    void getUserLoginHistoryByDateWrongUsername() {
        Assertions.assertTrue(dao.getUserLoginHistory(
                TestConstant.USERNAME_INVALID,
                TestConstant.DATE_LOGIN_VALID,
                TestConstant.OFFSET_VALID,
                TestConstant.LIMIT_VALID
        ).isEmpty());
    }

    @Test
    void getAllLoginHistory() {
        Assertions.assertNotNull(dao.getAllLoginHistory(TestConstant.OFFSET_VALID, TestConstant.LIMIT_VALID));
    }

    @Test
    void getAllLoginHistoryWrongOffset() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> dao.getAllLoginHistory(TestConstant.OFFSET_INVALID, TestConstant.LIMIT_VALID)
        );
    }

    @Test
    void getAllLoginHistoryWrongLimit() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> dao.getAllLoginHistory(TestConstant.OFFSET_VALID, TestConstant.LIMIT_INVALID)
        );
    }

    @Test
    void getAllLoginHistoryByDate() {
        Assertions.assertFalse(dao.getAllLoginHistory(
                TestConstant.DATE_LOGIN_VALID,
                TestConstant.OFFSET_VALID,
                TestConstant.LIMIT_VALID
        ).isEmpty());
    }

    @Test
    void getAllLoginHistoryByDateWrongDate() {
        Assertions.assertTrue(dao.getAllLoginHistory(
                TestConstant.DATE_INVALID,
                TestConstant.OFFSET_VALID,
                TestConstant.LIMIT_VALID
        ).isEmpty());
    }

    @Test
    void getUserLoginHistoryCount() {
        Assertions.assertNotNull(dao.getUserLoginHistoryCount(TestConstant.USERNAME_VALID));
    }

    @Test
    void getUserLoginHistoryCountWrongUser() {
        Assertions.assertNotNull(dao.getUserLoginHistoryCount(TestConstant.USERNAME_INVALID));
    }

    @Test
    void getAllLoginHistoryCount() {
        Assertions.assertNotNull(dao.getAllLoginHistoryCount());
    }
}