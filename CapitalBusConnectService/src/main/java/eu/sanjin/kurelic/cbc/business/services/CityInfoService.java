package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.viewmodel.city.CityInfoItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.city.CityInfoItems;
import eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItems;

import java.util.Locale;
import java.util.Optional;

public interface CityInfoService {

    CityInfoItem getCityItem(String cityName, Locale language);

    String[] getAllCityNames(Locale language);

    InfoItems getCityLines(int pageNumber, Locale language);

    int getNumberOfCityLines();

}
