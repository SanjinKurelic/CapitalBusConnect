package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.BusSchedule;
import eu.sanjin.kurelic.cbc.repo.entity.TripPrices;
import eu.sanjin.kurelic.cbc.repo.entity.TripType;
import eu.sanjin.kurelic.cbc.repo.values.TripTypeValues;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ScheduleDaoImpl implements ScheduleDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public ScheduleDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public BusSchedule getSchedule(Integer id) {
        var session = sessionFactory.getCurrentSession();
        return session.get(BusSchedule.class, id);
    }

    @Override
    public List<BusSchedule> getSchedules(Integer... ids) {
        // If there is no ID-s supplied
        if(ids.length < 1){
            return new ArrayList<>();
        }
        var session = sessionFactory.getCurrentSession();
        var hql = "FROM BusSchedule WHERE id IN (:ids)";
        Query<BusSchedule> schedules = session.createQuery(hql, BusSchedule.class);
        schedules.setParameterList("ids", ids);
        return schedules.getResultList();
    }

    @Override
    public List<BusSchedule> getBusLineSchedules(Integer fromCityId, Integer toCityId) {
        // If there are no cities supplied
        if(fromCityId == null | toCityId == null) {
            return new ArrayList<>();
        }
        var session = sessionFactory.getCurrentSession();
        var hql = "FROM BusSchedule WHERE " +
                // AB trip
                "(tripType.name = :abTrip AND busLine.city1.id = :city1 AND busLine.city2.id = :city2) OR " +
                // BA trip
                "(tripType.name = :baTrip AND busLine.city1.id = :city2 AND busLine.city2.id = :city1) OR " +
                // Round trip
                "(tripType.name = :roundTrip AND " +
                "(busLine.city1.id = :city1 AND busLine.city2.id = :city2) OR " +
                "(busLine.city1.id = :city2 AND busLine.city2.id = :city1)) AND " +
                // Operates
                "operates = 1 ORDER BY fromTime ASC";

        Query<BusSchedule> query = session.createQuery(hql, BusSchedule.class);
        query.setParameter("city1", fromCityId);
        query.setParameter("city2", toCityId);
        query.setParameter("abTrip", TripTypeValues.A_TO_B.name());
        query.setParameter("baTrip", TripTypeValues.B_TO_A.name());
        query.setParameter("roundTrip", TripTypeValues.ROUND_TRIP.name());
        return query.getResultList();
    }

    @Override
    public TripPrices getTripPrice(Integer tripDuration) {
        return getTripPrice(tripDuration, LocalDate.now());
    }

    @Override
    public TripPrices getTripPrice(Integer tripDuration, LocalDate date) {
        var session = sessionFactory.getCurrentSession();
        var hql = "FROM TripPrices WHERE tripDuration <= :duration AND fromDate <= :date ORDER BY tripDuration DESC, fromDate DESC";

        Query<TripPrices> query = session.createQuery(hql, TripPrices.class);
        query.setParameter("duration", tripDuration);
        query.setParameter("date", date);

        return query.getResultList().get(0); // or throw null pointer exception
    }

    @Override
    public TripType getTripType(TripTypeValues value) {
        var session = sessionFactory.getCurrentSession();
        var hql = "FROM TripType WHERE name = :name";

        Query<TripType> query = session.createQuery(hql, TripType.class);
        query.setParameter("name", value.name());

        return query.getSingleResult();
    }

}
