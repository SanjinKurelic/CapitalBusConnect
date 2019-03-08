package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.utility.LocaleUtility;
import eu.sanjin.kurelic.cbc.business.viewmodel.city.CityInfoItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.city.CityInfoItems;
import eu.sanjin.kurelic.cbc.repo.dao.DestinationInfoDao;
import eu.sanjin.kurelic.cbc.repo.entity.CityDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Locale;

@Service
public class CityInfoServiceImpl implements CityInfoService {

    private final DestinationInfoDao destinationDao;

    @Autowired
    public CityInfoServiceImpl(@Qualifier("destinationInfoDaoImpl") DestinationInfoDao destinationDao) {
        this.destinationDao = destinationDao;
    }

    private CityInfoItem convertEntityToViewModel(CityDescription city) {
        CityInfoItem item = new CityInfoItem();

        item.setName(city.getTitle());
        item.setDescription(city.getDescription());
        item.setImageName(city.getCity().getImageName());

        return item;
    }

    @Override
    @Transactional
    public CityInfoItem getCityItem(String cityName, Locale language) {
        var city = destinationDao.getCityDescription(cityName, LocaleUtility.getLanguage(language));
        return convertEntityToViewModel(city);
    }

    @Override
    @Transactional
    public CityInfoItems getCityItems(Locale language) {
        CityInfoItems items = new CityInfoItems();
        var cities = destinationDao.getCityDescriptions(LocaleUtility.getLanguage(language));
        for(var city : cities) {
            items.add(convertEntityToViewModel(city));
        }
        return items;
    }
}
