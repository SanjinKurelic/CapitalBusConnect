package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.CityDescription;

import javax.persistence.Tuple;
import java.util.List;

public interface CityDescriptionDao {

    int TUPLE_CITY_NAME = 0;
    int TUPLE_CITY_DESCRIPTION = 1;

    CityDescription getCityDescription(int id, String language);

    CityDescription getCityDescription(String cityName, String language);

    CityDescription getCityDescription(String cityName, String language, String nameLanguage);

    List<CityDescription> getCityDescriptions(String language);

    List<CityDescription> getCityDescriptions(String language, Integer... ids);

    List<Tuple> searchCityDescription(String partialCityName, int limit, String language, String urlLanguage);

}
