package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.TrafficDescription;

import java.time.LocalDate;
import java.util.List;

public interface TrafficDescriptionDao {

    List<TrafficDescription> getTrafficDescriptions(LocalDate date, String language, int limit);

}
