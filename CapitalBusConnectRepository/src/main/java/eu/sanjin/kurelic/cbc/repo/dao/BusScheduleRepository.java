package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.BusSchedule;
import eu.sanjin.kurelic.cbc.repo.entity.BusSchedule_;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;
import java.util.List;

public interface BusScheduleRepository extends JpaRepository<BusSchedule, Integer>, JpaSpecificationExecutor<BusSchedule> {

  BusSchedule getById(int id);

  List<BusSchedule> getByIdIn(Collection<Integer> id);

  default List<BusSchedule> getBusLineSchedules(Integer fromCityId, Integer toCityId){
    return findAll(BusScheduleSpecifications.getBusLineSchedules(fromCityId, toCityId), JpaSort.by(Sort.Direction.ASC, BusSchedule_.fromTime.getName()));
  }

}
