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
class UserDaoTest {

    @Autowired
    private UserDao dao;

    @Test
    void getUserInformationWrongIdNull() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> dao.getUserInformation(TestConstant.USERNAME_NULL));
    }

    @Test
    void getUserInformationWrongId() {
        Assertions.assertNull(dao.getUserInformation(TestConstant.USERNAME_INVALID));
    }

    @Test
    void getUserInformation() {
        Assertions.assertNotNull(dao.getUserInformation(TestConstant.USERNAME_VALID));
    }

    @Test
    void hasUserInformation() {
        Assertions.assertTrue(dao.hasUserInformation(TestConstant.USERNAME_VALID));
    }

    @Test
    void hasUserInformationWrongUsername() {
        Assertions.assertFalse(dao.hasUserInformation(TestConstant.USERNAME_INVALID));
    }

    @Test
    void hasUserInformationWrongUsernameNull() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> dao.hasUserInformation(TestConstant.USERNAME_NULL));
    }

    @Test
    void searchUserInformation() {
        Assertions.assertFalse(dao.searchUserInformation(
                TestConstant.SEARCH_USER_VALID,
                TestConstant.LIMIT_VALID
        ).isEmpty());
    }

    @Test
    void searchUserInformationWrongUser() {
        Assertions.assertTrue(dao.searchUserInformation(
                TestConstant.SEARCH_USER_INVALID,
                TestConstant.LIMIT_VALID
        ).isEmpty());
    }

    @Test
    void searchUserInformationWrongUserNull() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> dao.searchUserInformation(TestConstant.USERNAME_NULL, TestConstant.LIMIT_INVALID)
        );
    }

    @Test
    void searchUserInformationWrongLimit() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> dao.searchUserInformation(TestConstant.SEARCH_USER_VALID, TestConstant.LIMIT_INVALID)
        );
    }

}