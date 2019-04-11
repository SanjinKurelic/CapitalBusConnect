package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.BusLine;

import java.util.List;

public interface BusLineDao {

    List<BusLine> getCityLines(int offset, int limit);

    Long getNumberOfCityLines();

}
