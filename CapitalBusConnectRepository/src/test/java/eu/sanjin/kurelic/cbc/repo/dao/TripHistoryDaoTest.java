package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.configuration.RepositoryConfiguration;
import eu.sanjin.kurelic.cbc.repo.dao.utility.TestConstant;
import eu.sanjin.kurelic.cbc.repo.entity.BusSchedule;
import eu.sanjin.kurelic.cbc.repo.entity.TripHistory;
import eu.sanjin.kurelic.cbc.repo.entity.TripType;
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
    void getTripHistoryValid() {
        // require at least one database record
        var a = dao.getTripHistory(TestConstant.VALID_ID);
        Assertions.assertNotNull(dao.getTripHistory(TestConstant.VALID_ID));
    }

    @Test
    void getTripHistoryInvalid() {
        Assertions.assertNull(dao.getTripHistory(TestConstant.INVALID_ID));
    }

    @Test
    void addOrUpdateTripHistory() {
        TripHistory tripHistory = new TripHistory();
        tripHistory.setId(TestConstant.VALID_ID);

        BusSchedule busSchedule = new BusSchedule();
        busSchedule.setId(TestConstant.VALID_ID);
        tripHistory.setBusSchedule(busSchedule);

        TripType tripType = new TripType();
        tripHistory.setId(TestConstant.VALID_ID);
        tripHistory.setTripType(tripType);

        Assertions.assertDoesNotThrow(() -> dao.addOrUpdateTripHistory(tripHistory));
    }

    @Test
    void hasTripHistoryWrongBusSchedule() {
        Assertions.assertNull(dao.getTripHistoryIdOrNull(TestConstant.INVALID_ID, TestConstant.VALID_DATE_TRIP, TestConstant.VALID_ID));
    }

    @Test
    void hasTripHistoryWrongDate() {
        Assertions.assertNull(dao.getTripHistoryIdOrNull(TestConstant.VALID_ID, TestConstant.INVALID_DATE, TestConstant.VALID_ID));
    }

    @Test
    void hasTripHistoryWrongTripType() {
        Assertions.assertNull(dao.getTripHistoryIdOrNull(TestConstant.VALID_ID, TestConstant.VALID_DATE_TRIP, TestConstant.INVALID_ID));
    }

    @Test
    void hasTripHistoryValid() {
        // Require exact database record
        Assertions.assertNotNull(dao.getTripHistoryIdOrNull(TestConstant.VALID_ID, TestConstant.VALID_DATE_TRIP, TestConstant.VALID_NEXT_ID));
    }

    @Test
    void getMostTraveledSchedulesValid() {
        Assertions.assertNotNull(dao.getMostTraveledSchedules(TestConstant.VALID_LIMIT));
    }

    @Test
    void getMostTraveledSchedulesInvalid() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> dao.getMostTraveledSchedules(TestConstant.INVALID_LIMIT));
    }

    @Test
    void getLastFilledTripHistoryValid() {
        Assertions.assertNotNull(dao.getLastFilledTripHistory(TestConstant.VALID_LIMIT));
    }

    @Test
    void getLastFilledTripHistoryInvalid() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> dao.getLastFilledTripHistory(TestConstant.INVALID_LIMIT));
    }

    @Test
    void getTripHistoryCount() {
        Assertions.assertNotNull(dao.getTripHistoryCount());
    }
}