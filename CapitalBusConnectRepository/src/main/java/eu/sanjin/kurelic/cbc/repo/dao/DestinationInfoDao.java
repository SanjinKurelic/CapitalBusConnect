package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.BusLine;
import eu.sanjin.kurelic.cbc.repo.entity.CityDescription;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public interface DestinationInfoDao {

    CityDescription getCityDescription(int id, String language);

    CityDescription getCityDescription(String cityName, String language);

    List<CityDescription> getCityDescriptions(String language);

    List<CityDescription> getCityDescriptions(String language, Integer ...ids);

    List<BusLine> getCityLines(int offset, int limit);

    int getNumberOfCityLines();

}
