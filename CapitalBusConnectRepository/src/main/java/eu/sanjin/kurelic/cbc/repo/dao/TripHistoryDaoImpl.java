package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.TripHistory;
import eu.sanjin.kurelic.cbc.repo.entity.TripHistory_;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;

@Repository
public class TripHistoryDaoImpl implements TripHistoryDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public TripHistoryDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public TripHistory getTripHistory(int id) {
        var session = sessionFactory.getCurrentSession();
        return session.get(TripHistory.class, id);
    }

    @Override
    public void addOrUpdateTripHistory(TripHistory tripHistory) {
        var session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(tripHistory);
    }

    @Override
    public int hasTripHistory(int busScheduleId, LocalDate date, int tripTypeId) {
        var session = sessionFactory.getCurrentSession();
        var hql = "FROM TripHistory WHERE busSchedule.id = :busScheduleId AND date = :date AND tripType.id = :tripTypeId";

        Query<TripHistory> query = session.createQuery(hql, TripHistory.class);
        query.setParameter("busScheduleId", busScheduleId);
        query.setParameter("date", date);
        query.setParameter("tripTypeId", tripTypeId);

        var list = query.getResultList();
        if(list.size() != 1 ){
            return 0;
        }
        return list.get(0).getId();
    }

    /**
     *
     * @param limit
     * @return Tuple - first parameter is TripHistory and second parameter is counter
     */
    @Override
    public List<Tuple> getMostTraveledSchedules(int limit) {
        var session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<Tuple> criteria = builder.createQuery(Tuple.class);
        Root<TripHistory> root = criteria.from(TripHistory.class);
        Expression counter = builder.count(root);

        criteria.multiselect(root, counter);
        criteria.groupBy(root.get(TripHistory_.busSchedule), root.get(TripHistory_.tripType));
        criteria.orderBy(builder.desc(counter));

        return session.createQuery(criteria).setMaxResults(limit).getResultList();
    }

    /**
     *
     * @param limit
     * @return Tuple - first parameters is TripHistory and second parameter is counter
     */
    @Override
    public List<Tuple> getLastFilledTripHistory(int limit) {
        var session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<Tuple> criteria = builder.createQuery(Tuple.class);
        Root<TripHistory> root = criteria.from(TripHistory.class);

        criteria.multiselect(root, builder.count(root)); // TripHistory => counter
        criteria.where(builder.equal(root.get(TripHistory_.numberOfSeats), 0)); // where number of left seats = 0
        criteria.groupBy(root.get(TripHistory_.busSchedule), root.get(TripHistory_.tripType)); // group by schedule & direction
        criteria.orderBy(builder.desc(root.get(TripHistory_.date))); // get last results by ordering date

        return session.createQuery(criteria).setMaxResults(limit).getResultList();
    }

    @Override
    public Long getTripHistoryCount() {
        var session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<TripHistory> root = criteria.from(TripHistory.class);
        criteria.select(builder.count(root));

        return session.createQuery(criteria).getSingleResult();
    }

}
