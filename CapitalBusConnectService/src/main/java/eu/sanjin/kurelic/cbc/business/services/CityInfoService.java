package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.viewmodel.city.CityInfoItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.city.CityInfoItems;

import java.util.Locale;

public interface CityInfoService {

    CityInfoItem getCityItem(String cityName, Locale language);

    CityInfoItems getCityItems(Locale language);

}
