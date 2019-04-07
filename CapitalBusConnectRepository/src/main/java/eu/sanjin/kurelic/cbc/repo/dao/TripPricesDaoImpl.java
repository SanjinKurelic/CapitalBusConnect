package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.TripPrices;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class TripPricesDaoImpl implements TripPricesDao {

    private final SessionFactory sessionFactory;

    public TripPricesDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
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

}
