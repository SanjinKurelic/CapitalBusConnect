package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.BusSchedule;

import java.util.List;

public interface BusScheduleDao {

    BusSchedule getSchedule(int id);

    List<BusSchedule> getSchedules(Integer... ids);

    List<BusSchedule> getBusLineSchedules(Integer fromCityId, Integer toCityId);

}
