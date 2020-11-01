package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.TripType;
import eu.sanjin.kurelic.cbc.repo.values.TripTypeValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripTypeDao {

  TripType getTripType(TripTypeValue value);

}
