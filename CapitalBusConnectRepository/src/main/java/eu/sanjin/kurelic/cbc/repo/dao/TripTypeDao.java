package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.TripType;
import eu.sanjin.kurelic.cbc.repo.values.TripTypeValues;

public interface TripTypeDao {

    TripType getTripType(TripTypeValues value);

}
