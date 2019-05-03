package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.utility.LocaleUtility;
import eu.sanjin.kurelic.cbc.business.viewmodel.city.CityInfoItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.info.*;
import eu.sanjin.kurelic.cbc.business.viewmodel.search.CitySearchResult;
import eu.sanjin.kurelic.cbc.business.viewmodel.search.CitySearchResults;
import eu.sanjin.kurelic.cbc.repo.dao.BusLineDao;
import eu.sanjin.kurelic.cbc.repo.dao.CityDescriptionDao;
import eu.sanjin.kurelic.cbc.repo.entity.BusLine;
import eu.sanjin.kurelic.cbc.repo.entity.CityDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
public class CityInfoServiceImpl implements CityInfoService {

    private final CityDescriptionDao cityDescriptionDao;
    private final BusLineDao busLineDao;

    @Autowired
    public CityInfoServiceImpl(CityDescriptionDao cityDescriptionDao, BusLineDao busLineDao) {
        this.cityDescriptionDao = cityDescriptionDao;
        this.busLineDao = busLineDao;
    }

    @Override
    @Transactional
    public CityInfoItem getCityItem(String cityName, Locale language) {
        // Check
        if (Objects.isNull(cityName) || cityName.isBlank() || Objects.isNull(language)) {
            return null;
        }
        // Logic
        var city = cityDescriptionDao.getCityDescription(
                cityName,
                LocaleUtility.getLanguage(language),
                LocaleUtility.getUrlDefaultLanguage()
        );
        return convertEntityToViewModel(city);
    }

    @Override
    @Transactional
    public CitySearchResults searchByCityName(String partialName, int numberOfSearchResults, Locale language) {
        CitySearchResults results = new CitySearchResults();
        CitySearchResult result;
        // Check
        if (Objects.isNull(partialName) || partialName.isBlank() || Objects.isNull(language) || numberOfSearchResults < 1) {
            return results;
        }
        // Logic
        var cities = cityDescriptionDao.searchCityDescription(
                partialName,
                numberOfSearchResults,
                LocaleUtility.getLanguage(language),
                LocaleUtility.getUrlDefaultLanguage());
        for (Tuple city : cities) {
            result = new CitySearchResult();
            result.setFriendlyCityName(((CityDescription) city.get(CityDescriptionDao.TUPLE_CITY_DESCRIPTION)).getTitle());
            result.setUrlCityName((String) city.get(CityDescriptionDao.TUPLE_CITY_NAME));
            results.add(result);
        }
        return results;
    }

    @Override
    @Transactional
    public InfoItems getCityLines(int pageNumber, int limit, Locale language) {
        InfoItems items = new InfoItems();
        InfoItem item;
        // Check
        if (Objects.isNull(language) || limit < 0) {
            return items;
        }
        // Page number
        pageNumber = (pageNumber - 1) * limit;
        if (pageNumber < 0) {
            return items;
        }
        // Logic
        var lines = busLineDao.getCityLines(pageNumber, limit);
        if (lines.isEmpty()) {
            return items;
        }
        // Database optimization
        HashSet<Integer> ids = new HashSet<>();
        for (BusLine line : lines) {
            ids.add(line.getCity1().getId());
            ids.add(line.getCity2().getId());
        }
        var cityDescriptions = cityDescriptionDao.getCityDescriptions(LocaleUtility.getLanguage(language),
                ids.toArray(Integer[]::new));
        // Wrong language
        if (cityDescriptions.isEmpty()) {
            return items;
        }
        for (BusLine line : lines) {
            item = new InfoItem();
            item.addColumn(new InfoItemTextColumn(getCityDesc(cityDescriptions, line.getCity1().getId())));
            item.addColumn(new InfoItemIconColumn(InfoItemIconType.ARROW_ICON));
            item.addColumn(new InfoItemTextColumn(getCityDesc(cityDescriptions, line.getCity2().getId())));
            item.addColumn(new InfoItemButtonColumn(InfoItemButtonType.EDIT_ROUTE, line.getId().toString()));
            items.add(item);
        }
        return items;
    }

    @Override
    @Transactional
    public Long getNumberOfCityLines() {
        return busLineDao.getNumberOfCityLines();
    }

    // Utility
    private CityInfoItem convertEntityToViewModel(CityDescription city) {
        // Check
        if (Objects.isNull(city)) {
            return null;
        }
        // Logic
        CityInfoItem item = new CityInfoItem();
        item.setName(city.getTitle());
        item.setDescription(city.getDescription());
        item.setImageName(city.getCity().getImageName());
        return item;
    }

    private String getCityDesc(List<CityDescription> cityDescriptions, int id) {
        return cityDescriptions.stream().filter(c -> c.getId().getId() == id).findFirst().orElseGet(CityDescription::new).getTitle();
    }
}
