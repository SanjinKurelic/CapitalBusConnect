import eu.sanjin.kurelic.cbc.business.configuration.ServiceConfiguration;
import eu.sanjin.kurelic.cbc.business.services.TrafficInfoService;
import eu.sanjin.kurelic.cbc.repo.configuration.RepositoryConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Locale;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfiguration.class, RepositoryConfiguration.class})
public class TrafficInfoServiceTest {

    @Autowired
    @Qualifier("trafficInfoServiceImpl")
    private TrafficInfoService service;

    @Test
    public void getTrafficItems(){
        var items = service.getTrafficItems(Locale.ENGLISH);
        for(var item : items) {
            System.out.println(item.getWarningType().name() + ": " + item.getTextMessage() + " (" + item.getDate() + ")");
        }
    }

}
