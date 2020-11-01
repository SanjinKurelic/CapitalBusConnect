package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.utility.LocaleUtility;
import eu.sanjin.kurelic.cbc.business.viewmodel.traffic.TrafficInfoItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.traffic.TrafficInfoItems;
import eu.sanjin.kurelic.cbc.business.viewmodel.traffic.TrafficWarningType;
import eu.sanjin.kurelic.cbc.repo.dao.TrafficDescriptionRepository;
import eu.sanjin.kurelic.cbc.repo.entity.TrafficDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
public class TrafficInfoServiceImpl implements TrafficInfoService {

  private final TrafficDescriptionRepository trafficDescriptionRepository;

  @Autowired
  public TrafficInfoServiceImpl(TrafficDescriptionRepository trafficDescriptionRepository) {
    this.trafficDescriptionRepository = trafficDescriptionRepository;
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
    List<TrafficDescription> descriptions = trafficDescriptionRepository.findByIdLanguageOrderByTrafficDateAsc(language, PageRequest.of(0, limit));
    for (TrafficDescription description : descriptions) {
      item = new TrafficInfoItem();
      item.setTextMessage(description.getDescription());
      item.setWarningType(TrafficWarningType.valueOf(description.getTraffic().getTrafficType().getName()));
      item.setDate(description.getTraffic().getDate());
      items.add(item);
    }
    return items;
  }
}
