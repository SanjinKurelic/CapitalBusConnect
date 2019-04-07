package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.configuration.RepositoryConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RepositoryConfiguration.class})
class UserTravelHistoryDaoTest {

    @Autowired
    @Qualifier("userTravelHistoryDaoImpl")
    private UserTravelHistoryDao userTravelHistoryDao;

    @Test
    void getUserTravelHistoryById() {
    }

    @Test
    void getUserTravelHistoryById1() {
    }

    @Test
    void getUserTravelHistory() {
    }

    @Test
    void getUserTravelHistory1() {
    }

    @Test
    @Transactional
    void getTopUsersByTravels() {
        Assertions.assertDoesNotThrow(() -> userTravelHistoryDao.getTopUsersByTravels(10));
    }

    @Test
    void getUserTravelHistoryCount() {
    }

    @Test
    void addUserTravelHistory() {
    }
}