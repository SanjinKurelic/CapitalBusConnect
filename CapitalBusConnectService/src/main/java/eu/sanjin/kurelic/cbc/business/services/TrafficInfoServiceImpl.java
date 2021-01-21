package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.utility.LocaleUtility;
import eu.sanjin.kurelic.cbc.business.viewmodel.traffic.TrafficInfoItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.traffic.TrafficInfoItems;
import eu.sanjin.kurelic.cbc.business.viewmodel.traffic.TrafficWarningType;
import eu.sanjin.kurelic.cbc.repo.dao.TrafficDescriptionDao;
import eu.sanjin.kurelic.cbc.repo.entity.TrafficDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Logger;

@Service
public class TrafficInfoServiceImpl implements TrafficInfoService {

  private final Logger log = Logger.getLogger(TrafficInfoServiceImpl.class.getName());
  private final TrafficDescriptionDao trafficDao;

  @Autowired
  public TrafficInfoServiceImpl(TrafficDescriptionDao trafficDao) {
    this.trafficDao = trafficDao;
  }

  @Override
  @Transactional
  public TrafficInfoItems getTrafficItems(Locale locale, int limit) {
    TrafficInfoItems items = new TrafficInfoItems();
    TrafficInfoItem item;
    // Check
    if (Objects.isNull(locale) || limit < 1) {
      return items;
    }
    // Logic
    String language = LocaleUtility.getLanguage(locale);
    List<TrafficDescription> descriptions = trafficDao.getTrafficDescriptions(LocalDate.now(), language, limit);
    for (TrafficDescription description : descriptions) {
      item = new TrafficInfoItem();
      item.setTextMessage(description.getDescription());
      item.setWarningType(TrafficWarningType.valueOf(description.getTraffic().getTrafficType().getName()));
      item.setDate(description.getTraffic().getDate());
      items.add(item);
    }
    return items;
  }

  @Override
  public void saveTrafficItems(TrafficInfoItems trafficInfoItems) {
    trafficInfoItems.forEach(trafficInfoItem ->
      log.info("Saving traffic info item (" + LocalDateTime.now().toString() + "): \"" + trafficInfoItem.getTextMessage() + "\"")
    );
  }
}
