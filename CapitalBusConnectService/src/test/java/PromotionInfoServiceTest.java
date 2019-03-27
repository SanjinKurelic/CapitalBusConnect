import eu.sanjin.kurelic.cbc.business.configuration.ServiceConfiguration;
import eu.sanjin.kurelic.cbc.business.services.PromotionInfoService;
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
class PromotionInfoServiceTest {

    @Autowired
    @Qualifier("promotionInfoServiceImpl")
    private PromotionInfoService service;

    @Test
    void getPromotionItems() {
        var items = service.getPromotionItems(Locale.forLanguageTag("hr"));
        for(var item : items) {
            System.out.println(item.getFromCity() + " -> " + item.getToCity() + " [" + item.getImageUrl() + "]");
        }
    }

}
