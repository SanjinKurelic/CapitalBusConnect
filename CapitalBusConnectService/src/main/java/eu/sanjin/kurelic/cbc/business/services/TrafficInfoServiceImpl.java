package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.utility.LocaleUtility;
import eu.sanjin.kurelic.cbc.repo.entity.TrafficDescription;
import eu.sanjin.kurelic.cbc.repo.dao.TrafficDescriptionDao;
import eu.sanjin.kurelic.cbc.business.viewmodel.traffic.TrafficInfoItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.traffic.TrafficInfoItems;
import eu.sanjin.kurelic.cbc.business.viewmodel.traffic.TrafficWarningType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Service
public class TrafficInfoServiceImpl implements TrafficInfoService {

    private final TrafficDescriptionDao trafficDao;

    @Autowired
    public TrafficInfoServiceImpl(TrafficDescriptionDao trafficDao) {
        this.trafficDao = trafficDao;
    }

    @Override
    @Transactional
    public TrafficInfoItems getTrafficItems(Locale locale) {
        TrafficInfoItems items = new TrafficInfoItems();
        try {
            TrafficInfoItem item;
            String language = LocaleUtility.getLanguage(locale);
            List<TrafficDescription> descriptions = trafficDao.getTrafficDescriptions(LocalDate.now(), language);

            for (TrafficDescription description : descriptions) {
                item = new TrafficInfoItem();
                item.setTextMessage(description.getDescription());
                item.setWarningType(TrafficWarningType.valueOf(description.getTraffic().getTrafficType().getName()));
                item.setDate(description.getTraffic().getDate());
                items.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }
}
