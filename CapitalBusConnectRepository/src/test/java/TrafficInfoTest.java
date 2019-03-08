import eu.sanjin.kurelic.cbc.repo.configuration.RepositoryConfiguration;
import eu.sanjin.kurelic.cbc.repo.dao.TrafficInfoDescriptionDao;
import eu.sanjin.kurelic.cbc.repo.entity.TrafficDescription;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RepositoryConfiguration.class})
public class TrafficInfoTest {

    @Autowired
    @Qualifier("trafficInfoDescriptionDaoImpl")
    TrafficInfoDescriptionDao dao;

    @Test
    @Transactional
    public void getTrafficInfo() {
        var items = dao.getTrafficDescriptions(LocalDate.now(), "hr");
        for(TrafficDescription item : items) {
            System.out.println(item.getTraffic().getDate() + " [" + item.getTraffic().getTrafficType().getName() + "]: " + item.getDescription());
        }
    }

}
