package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.CityDescription;

import java.util.List;

public interface CityDescriptionDao {

    CityDescription getCityDescription(int id, String language);

    CityDescription getCityDescription(String cityName, String language);

    List<CityDescription> getCityDescriptions(String language);

    List<CityDescription> getCityDescriptions(String language, Integer ...ids);

    List<CityDescription> searchCityDescription(String partialCityName, int limit, String language);

}
