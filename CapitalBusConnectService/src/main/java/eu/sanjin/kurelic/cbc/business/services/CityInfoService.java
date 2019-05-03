package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.viewmodel.city.CityInfoItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItems;
import eu.sanjin.kurelic.cbc.business.viewmodel.search.CitySearchResults;

import java.util.Locale;

public interface CityInfoService {

    CityInfoItem getCityItem(String cityName, Locale language);

    CitySearchResults searchByCityName(String partialName, int numberOfSearchResults, Locale language);

    InfoItems getCityLines(int pageNumber, int limit, Locale language);

    Long getNumberOfCityLines();

}
