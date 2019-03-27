import eu.sanjin.kurelic.cbc.business.configuration.ServiceConfiguration;
import eu.sanjin.kurelic.cbc.business.services.TrafficInfoService;
import eu.sanjin.kurelic.cbc.repo.configuration.RepositoryConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Locale;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceConfiguration.class, RepositoryConfiguration.class})
class TrafficInfoServiceTest {

    @Autowired
    @Qualifier("trafficInfoServiceImpl")
    private TrafficInfoService service;

    @Test
    void getTrafficItems(){
        var items = service.getTrafficItems(Locale.ENGLISH);
        for(var item : items) {
            System.out.println(item.getWarningType().name() + ": " + item.getTextMessage() + " (" + item.getDate() + ")");
        }
    }

}
