import eu.sanjin.kurelic.cbc.repo.configuration.RepositoryConfiguration;
import eu.sanjin.kurelic.cbc.repo.dao.BusScheduleDao;
import eu.sanjin.kurelic.cbc.repo.dao.TripHistoryDao;
import eu.sanjin.kurelic.cbc.repo.dao.TripTypeDao;
import eu.sanjin.kurelic.cbc.repo.dao.UserTravelHistoryDao;
import eu.sanjin.kurelic.cbc.repo.entity.TripHistory;
import eu.sanjin.kurelic.cbc.repo.entity.UserTravelHistory;
import eu.sanjin.kurelic.cbc.repo.values.TripTypeValues;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RepositoryConfiguration.class})
class TravelHistoryTest {

    @Autowired
    @Qualifier("tripHistoryDaoImpl")
    private TripHistoryDao dao;
    @Autowired
    @Qualifier("busScheduleDaoImpl")
    private BusScheduleDao busScheduleDao;
    @Autowired
    @Qualifier("tripTypeDaoImpl")
    private TripTypeDao tripTypeDao;
    @Autowired
    @Qualifier("userTravelHistoryDaoImpl")
    private UserTravelHistoryDao userTravelHistoryDao;

    @Test
    @Transactional
    void getTravelHistory() {
        var items = userTravelHistoryDao.getUserTravelHistory("user@example.com", 1, 10);
        int i = 0;
        for(UserTravelHistory item : items) {
            System.out.println((++i) + ". " + item.getUsername() + " -> " + item.getPrice());
        }
    }

    @Test
    @Transactional
    void addTripHistory() {
        TripHistory th = new TripHistory();
        th.setNumberOfSeats(12);;
        th.setBusSchedule(busScheduleDao.getSchedule(817));
        th.setTripType(tripTypeDao.getTripType(TripTypeValues.A_TO_B));
        th.setDate(LocalDate.of(2019, 4, 5));
        dao.addOrUpdateTripHistory(th);
        System.out.println(dao.getTripHistory(th.getId()).getDate());
    }

    @Test
    @Transactional
    void topUsersTest() {
        var tuples = userTravelHistoryDao.getTopUsersByTravels(10);
        for(var tuple : tuples) {
            System.out.println(tuple.get(0) + " => " + tuple.get(1));
        }
    }

}
