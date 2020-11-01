package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.utility.LocaleUtility;
import eu.sanjin.kurelic.cbc.business.viewmodel.promotion.PromotionItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.promotion.PromotionItems;
import eu.sanjin.kurelic.cbc.repo.dao.CityDescriptionDao;
import eu.sanjin.kurelic.cbc.repo.entity.CityDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * This service does not use the database, instead it use hardcoded values.
 * This service is just for the visual component of website.
 */
@Service
public class PromotionInfoServiceImpl implements PromotionInfoService {

  private final CityDescriptionDao cityDescriptionDao;
  // Hardcoded items, as this is the only example of system
  private static final Integer[] PROMOTION_ITEMS = {15, 19, 20, 26, 28, 37};
  private static final String DEFAULT_CITY = "zagreb";

  @Autowired
  public PromotionInfoServiceImpl(CityDescriptionDao cityDescriptionDao) {
    this.cityDescriptionDao = cityDescriptionDao;
  }

  @Override
  @Transactional
  public PromotionItems getPromotionItems(String fromCityUrl, Locale locale) {
    PromotionItems items = new PromotionItems();
    PromotionItem item;
    // Check
    if (Objects.isNull(fromCityUrl) || Objects.isNull(locale)) {
      return items;
    }
    // DEBUG: As we hardcoded promotion items and city, this check is required
    if (!fromCityUrl.equals(DEFAULT_CITY)) {
      return items;
    }
    // Logic
    String language = LocaleUtility.getLanguage(locale);
    var fromCityName = cityDescriptionDao.getCityDescription(
      fromCityUrl,
      language,
      LocaleUtility.getUrlDefaultLanguage()
    );
    if (Objects.isNull(fromCityName)) {
      return items;
    }
    List<CityDescription> cities = cityDescriptionDao.getCityDescriptions(language, PROMOTION_ITEMS);
    HashMap<Integer, String> urls = getCitiesUrl(cities);
    for (CityDescription city : cities) {
      item = new PromotionItem();
      item.setFromCity(fromCityName.getTitle());
      item.setFromCityUrl(fromCityUrl.toLowerCase());
      item.setToCity(city.getTitle());
      item.setToCityUrl(urls.get(city.getId().getId()));
      item.setImageUrl(city.getCity().getImageName());
      items.add(item);
    }
    return items;
  }

  @Override
  @Transactional
  public PromotionItems getPromotionItems(Locale locale) {
    // Check
    if (Objects.isNull(locale)) {
      return new PromotionItems();
    }
    // Logic
    return getPromotionItems(DEFAULT_CITY, locale);
  }

  // Utility
  private HashMap<Integer, String> getCitiesUrl(List<CityDescription> cities) {
    HashMap<Integer, String> result = new HashMap<>();
    // Database optimization
    HashSet<Integer> ids = new HashSet<>();
    for (CityDescription city : cities) {
      ids.add(city.getId().getId());
    }
    var defaultCities = cityDescriptionDao.getCityDescriptions(
      LocaleUtility.getUrlDefaultLanguage(),
      ids.toArray(Integer[]::new)
    );
    for (CityDescription city : defaultCities) {
      result.put(city.getId().getId(), city.getTitle());
    }
    return result;
  }

}
