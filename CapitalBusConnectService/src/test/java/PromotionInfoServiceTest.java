import eu.sanjin.kurelic.cbc.business.configuration.ServiceConfiguration;
import eu.sanjin.kurelic.cbc.business.services.PromotionInfoService;
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
public class PromotionInfoServiceTest {

    @Autowired
    @Qualifier("promotionInfoServiceImpl")
    private PromotionInfoService service;

    @Test
    public void getPromotionItems() {
        var items = service.getPromotionItems(Locale.forLanguageTag("hr"));
        for(var item : items) {
            System.out.println(item.getFromCity() + " -> " + item.getToCity() + " [" + item.getImageUrl() + "]");
        }
    }

}
