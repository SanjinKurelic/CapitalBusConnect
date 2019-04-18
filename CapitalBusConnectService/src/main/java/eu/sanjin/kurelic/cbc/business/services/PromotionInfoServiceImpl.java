package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.utility.LocaleUtility;
import eu.sanjin.kurelic.cbc.business.viewmodel.promotion.PromotionItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.promotion.PromotionItems;
import eu.sanjin.kurelic.cbc.repo.dao.CityDescriptionDao;
import eu.sanjin.kurelic.cbc.repo.entity.CityDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    private static final String DEFAULT_CITY = "Zagreb";

    @Autowired
    public PromotionInfoServiceImpl(CityDescriptionDao cityDescriptionDao) {
        this.cityDescriptionDao = cityDescriptionDao;
    }

    @Override
    @Transactional
    public PromotionItems getPromotionItems(String fromCityTitle, Locale locale) {
        PromotionItems items = new PromotionItems();
        PromotionItem item;
        // Check
        if (Objects.isNull(fromCityTitle) || Objects.isNull(locale)) {
            return items;
        }
        // DEBUG: As we hardcoded promotion items and city, this check is required
        if (!fromCityTitle.equals(DEFAULT_CITY)) {
            return items;
        }
        // Logic
        String language = LocaleUtility.getLanguage(locale);
        List<CityDescription> cities = cityDescriptionDao.getCityDescriptions(language, PROMOTION_ITEMS);
        for (CityDescription city : cities) {
            item = new PromotionItem();
            item.setFromCity(fromCityTitle);
            item.setToCity(city.getTitle());
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

}
