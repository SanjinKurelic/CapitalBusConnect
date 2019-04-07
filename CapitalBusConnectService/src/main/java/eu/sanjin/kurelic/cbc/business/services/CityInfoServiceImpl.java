package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.utility.LocaleUtility;
import eu.sanjin.kurelic.cbc.business.viewmodel.city.CityInfoItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.info.*;
import eu.sanjin.kurelic.cbc.repo.dao.BusLineDao;
import eu.sanjin.kurelic.cbc.repo.dao.CityDescriptionDao;
import eu.sanjin.kurelic.cbc.repo.entity.BusLine;
import eu.sanjin.kurelic.cbc.repo.entity.CityDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

@Service
public class CityInfoServiceImpl implements CityInfoService {

    private final CityDescriptionDao destinationDao;
    private final BusLineDao busLineDao;

    @Autowired
    public CityInfoServiceImpl(@Qualifier("cityDescriptionDaoImpl") CityDescriptionDao destinationDao, @Qualifier("busLineDaoImpl") BusLineDao busLineDao) {
        this.destinationDao = destinationDao;
        this.busLineDao = busLineDao;
    }

    @Override
    @Transactional
    public CityInfoItem getCityItem(String cityName, Locale language) {
        var city = destinationDao.getCityDescription(cityName, LocaleUtility.getLanguage(language));
        return convertEntityToViewModel(city);
    }

    @Override
    @Transactional
    public String[] searchByCityName(String partialName, int numberOfSearchResults, Locale language) {
        if (partialName.isBlank()) {
            return new String[0];
        }
        ArrayList<String> result = new ArrayList<>();
        var cities = destinationDao.searchCityDescription(partialName, numberOfSearchResults, LocaleUtility.getLanguage(language));
        for (CityDescription city : cities) {
            result.add(city.getTitle());
        }
        return result.toArray(String[]::new);
    }

    @Override
    @Transactional
    public InfoItems getCityLines(int pageNumber, int limit, Locale language) {
        InfoItems items = new InfoItems();
        InfoItem item;
        // Page number
        pageNumber = (pageNumber - 1) * limit;
        if (pageNumber < 0) {
            return items;
        }
        // Get all lines
        var lines = busLineDao.getCityLines(pageNumber, limit);
        // Database optimization
        HashSet<Integer> ids = new HashSet<>();
        for (BusLine line : lines) {
            ids.add(line.getCity1().getId());
            ids.add(line.getCity2().getId());
        }
        var cityDescriptions = destinationDao.getCityDescriptions(LocaleUtility.getLanguage(language), ids.toArray(Integer[]::new));
        for (BusLine line : lines) {
            item = new InfoItem();
            item.addColumn(new InfoItemTextColumn(getCityDesc(cityDescriptions, line.getCity1().getId())));
            item.addColumn(new InfoItemIconColumn(InfoItemIconType.ARROW_ICON));
            item.addColumn(new InfoItemTextColumn(getCityDesc(cityDescriptions, line.getCity2().getId())));
            item.addColumn(new InfoItemButtonColumn(InfoItemButtonType.EDIT_ROUTE, ""));

            items.add(item);
        }
        return items;
    }

    @Override
    @Transactional
    public int getNumberOfCityLines() {
        return busLineDao.getNumberOfCityLines();
    }

    // Utility
    private CityInfoItem convertEntityToViewModel(CityDescription city) {
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
