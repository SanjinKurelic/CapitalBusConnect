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
class TripHistoryDaoTest {

    @Autowired
    private TripHistoryDao dao;

    @Test
    void getTripHistory() {
        // require at least one database record
        Assertions.assertNotNull(dao.getTripHistory(TestConstant.ID_VALID));
    }

    @Test
    void getTripHistoryWrongId() {
        Assertions.assertNull(dao.getTripHistory(TestConstant.ID_INVALID));
    }

    @Test
    void addOrUpdateTripHistory() {
        Assertions.assertDoesNotThrow(() -> dao.addOrUpdateTripHistory(TestConstant.TRIP_HISTORY_VALID));
    }

    @Test
    void hasTripHistoryWrongBusSchedule() {
        Assertions.assertNull(dao.getTripHistoryIdOrNull(
                TestConstant.ID_INVALID,
                TestConstant.DATE_TRIP_VALID,
                TestConstant.ID_VALID
        ));
    }

    @Test
    void hasTripHistoryWrongDate() {
        Assertions.assertNull(dao.getTripHistoryIdOrNull(
                TestConstant.ID_VALID,
                TestConstant.DATE_INVALID,
                TestConstant.ID_VALID
        ));
    }

    @Test
    void hasTripHistoryWrongTripType() {
        Assertions.assertNull(dao.getTripHistoryIdOrNull(
                TestConstant.ID_VALID,
                TestConstant.DATE_TRIP_VALID,
                TestConstant.ID_INVALID
        ));
    }

    @Test
    void hasTripHistory() {
        // Require exact database record
        Assertions.assertNotNull(dao.getTripHistoryIdOrNull(
                TestConstant.ID_VALID,
                TestConstant.DATE_TRIP_VALID,
                TestConstant.ID_VALID_NEXT
        ));
    }

    @Test
    void getMostTraveledSchedules() {
        Assertions.assertNotNull(dao.getMostTraveledSchedules(TestConstant.LIMIT_VALID));
    }

    @Test
    void getMostTraveledSchedulesWrongLimit() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> dao.getMostTraveledSchedules(TestConstant.LIMIT_INVALID)
        );
    }

    @Test
    void getLastFilledTripHistory() {
        Assertions.assertNotNull(dao.getLastFilledTripHistory(TestConstant.LIMIT_VALID));
    }

    @Test
    void getLastFilledTripHistoryWrongLimit() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> dao.getLastFilledTripHistory(TestConstant.LIMIT_INVALID)
        );
    }

    @Test
    void getTripHistoryCount() {
        Assertions.assertNotNull(dao.getTripHistoryCount());
    }
}