package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.CityDescription;
import eu.sanjin.kurelic.cbc.repo.entity.composite.LanguagePrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Tuple;
import java.util.Collection;
import java.util.List;

public interface CityDescriptionDao extends JpaRepository<CityDescription, LanguagePrimaryKey> {

  int TUPLE_CITY_NAME = 0;
  int TUPLE_CITY_DESCRIPTION = 1;

  CityDescription findByIdIdAndIdLanguage(Integer id, String language);

  CityDescription findByTitleIgnoreCaseAndIdLanguage(String cityName, String language);

  List<CityDescription> findByIdLanguage(String language);

  List<CityDescription> findByIdLanguageAndIdIdIn(String language, Collection<Integer> ids);

  List<Tuple> searchCityDescription(String partialCityName, int limit, String language, String urlLanguage);

  CityDescription getCityDescription(String cityName, String language, String nameLanguage);

}
