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
    void getUserInformationInvalid() {
        Assertions.assertNull(dao.getUserInformation(TestConstant.INVALID_USERNAME));
    }

    @Test
    void getUserInformationValid() {
        Assertions.assertNotNull(dao.getUserInformation(TestConstant.VALID_USERNAME));
    }

    @Test
    void hasUserInformationTrue() {
        Assertions.assertTrue(dao.hasUserInformation(TestConstant.VALID_USERNAME));
    }

    @Test
    void hasUserInformationFalse() {
        Assertions.assertFalse(dao.hasUserInformation(TestConstant.INVALID_USERNAME));
    }

    @Test
    void searchUserInformationValidWithResult() {
        Assertions.assertFalse(dao.searchUserInformation(TestConstant.SEARCH_USER_WITH_RESULT, TestConstant.VALID_LIMIT).isEmpty());
    }

    @Test
    void searchUserInformationValidNoResult() {
        Assertions.assertEquals(TestConstant.EMPTY_RESULT_SIZE, dao.searchUserInformation(TestConstant.SEARCH_USER_NO_RESULT, TestConstant.VALID_LIMIT).size());
    }

    @Test
    void searchUserInformationWrongLimit() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> dao.searchUserInformation(TestConstant.SEARCH_USER_WITH_RESULT, TestConstant.INVALID_LIMIT));
    }


}