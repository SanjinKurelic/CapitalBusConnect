package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.utility.LocaleUtility;
import eu.sanjin.kurelic.cbc.business.viewmodel.city.CityInfoItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.city.CityInfoItems;
import eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItemButtonType;
import eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItemColumnType;
import eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItems;
import eu.sanjin.kurelic.cbc.repo.dao.DestinationInfoDao;
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

    private static final int ROUTE_ITEMS_LIMIT = 10;
    private final DestinationInfoDao destinationDao;

    @Autowired
    public CityInfoServiceImpl(@Qualifier("destinationInfoDaoImpl") DestinationInfoDao destinationDao) {
        this.destinationDao = destinationDao;
    }

    @Override
    @Transactional
    public CityInfoItem getCityItem(String cityName, Locale language) {
        var city = destinationDao.getCityDescription(cityName, LocaleUtility.getLanguage(language));
        return convertEntityToViewModel(city);
    }

    @Override
    @Transactional
    public String[] getAllCityNames(Locale language) {
        ArrayList<String> items = new ArrayList<>();
        var cities = destinationDao.getCityDescriptions(LocaleUtility.getLanguage(language));
        for(var city : cities) {
            items.add(city.getTitle());
        }
        return items.toArray(String[]::new);
    }

    @Override
    @Transactional
    public InfoItems getCityLines(int pageNumber, Locale language) {
        InfoItems items = new InfoItems();
        InfoItem item;
        // Get all lines
        var lines = destinationDao.getCityLines(pageNumber, ROUTE_ITEMS_LIMIT);
        // Database optimization
        HashSet<Integer> ids = new HashSet<>();
        for(BusLine line : lines) {
            ids.add(line.getCity1().getId());
            ids.add(line.getCity2().getId());
        }
        var cityDescriptions = destinationDao.getCityDescriptions(LocaleUtility.getLanguage(language), ids.toArray(Integer[]::new));
        for(BusLine line : lines) {
            item = new InfoItem();
            // City 1
            item.setColumnType1(InfoItemColumnType.TEXT);
            item.setColumn1(getCityDesc(cityDescriptions, line.getCity1().getId()));
            // ->
            item.setColumnType2(InfoItemColumnType.ARROW_ICON);
            // City 2
            item.setColumnType3(InfoItemColumnType.TEXT);
            item.setColumn3(getCityDesc(cityDescriptions, line.getCity2().getId()));
            // Button
            item.setButtonType(InfoItemButtonType.EDIT_ROUTE);
            items.add(item);
        }
        return items;
    }

    @Override
    @Transactional
    public int getNumberOfCityLines() {
        var busLines = destinationDao.getNumberOfCityLines();
        // down expression is same, but more faster and precise than: (int) Math.ceil(busLines / (double) ROUTE_ITEMS_LIMIT)
        return (busLines + ROUTE_ITEMS_LIMIT - 1) / ROUTE_ITEMS_LIMIT;
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
