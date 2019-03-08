package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.TrafficDescription;

import java.time.LocalDate;
import java.util.List;

public interface TrafficInfoDescriptionDao {

    int TRAFFIC_ITEMS_LIMIT = 25;

    List<TrafficDescription> getTrafficDescriptions(LocalDate date, String language, int limit);

    default List<TrafficDescription> getTrafficDescriptions(LocalDate date, String language){
        return getTrafficDescriptions(date, language, TRAFFIC_ITEMS_LIMIT);
    }

}
