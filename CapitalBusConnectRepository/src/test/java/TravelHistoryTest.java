import eu.sanjin.kurelic.cbc.repo.configuration.RepositoryConfiguration;
import eu.sanjin.kurelic.cbc.repo.dao.TravelHistoryDao;
import eu.sanjin.kurelic.cbc.repo.entity.UserTravelHistory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RepositoryConfiguration.class})
class TravelHistoryTest {

    @Autowired
    @Qualifier("travelHistoryDaoImpl")
    private TravelHistoryDao dao;

    @Test
    @Transactional
    void getTravelHistory() {
        var items = dao.getUserTravelHistory("user@example.com", 1, 25);
        int i = 0;
        for(UserTravelHistory item : items) {
            System.out.println((++i) + ". " + item.getId().getUsername());
        }
    }

}
