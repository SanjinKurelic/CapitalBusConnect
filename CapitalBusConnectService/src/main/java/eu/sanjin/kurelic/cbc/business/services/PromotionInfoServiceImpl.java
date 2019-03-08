package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.utility.LocaleUtility;
import eu.sanjin.kurelic.cbc.business.viewmodel.promotion.PromotionItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.promotion.PromotionItems;
import eu.sanjin.kurelic.cbc.repo.dao.DestinationInfoDao;
import eu.sanjin.kurelic.cbc.repo.entity.CityDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;

/**
 * This service does not use the database, instead it use hardcoded values.
 * This service is just for the visual component of website.
 */
@Service
public class PromotionInfoServiceImpl implements PromotionInfoService {

    private final DestinationInfoDao destinationInfoDao;
    // Hardcoded items, as this is the only example of system
    private static final Integer[] PROMOTION_ITEMS = {15, 19, 20, 26, 28, 37};
    private static final String DEFAULT_CITY = "Zagreb";

    @Autowired
    public PromotionInfoServiceImpl(DestinationInfoDao destinationInfoDao) {
        this.destinationInfoDao = destinationInfoDao;
    }

    @Override
    @Transactional
    public PromotionItems getPromotionItems(CityDescription fromCity, Locale locale) {
        PromotionItems items = new PromotionItems();
        PromotionItem item;

        String language = LocaleUtility.getLanguage(locale);
        List<CityDescription> cities = destinationInfoDao.getCityDescriptions(language, PROMOTION_ITEMS);
        for(CityDescription city : cities) {
            item = new PromotionItem();
            item.setFromCity(fromCity.getTitle());
            item.setToCity(city.getTitle());
            item.setImageUrl(city.getCity().getImageName());
            items.add(item);
        }
        return items;
    }

    /*@Override
    @Transactional
    public PromotionItems getPromotionItems(CityDescription fromCity) {
        return getPromotionItems(fromCity, Locale.forLanguageTag(fromCity.getId().getLanguage()));
    }*/

    @Override
    @Transactional
    public PromotionItems getPromotionItems(Locale locale) {
        var fromCity = destinationInfoDao.getCityDescription(DEFAULT_CITY, LocaleUtility.getLanguage(locale));
        return getPromotionItems(fromCity, locale);
    }

}
