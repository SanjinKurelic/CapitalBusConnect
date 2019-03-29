package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.TripHistory;
import eu.sanjin.kurelic.cbc.repo.entity.UserTravelHistory;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class TravelHistoryDaoImpl implements TravelHistoryDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public TravelHistoryDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<UserTravelHistory> getUserTravelHistory(String username, LocalDate date, int offset, int limit) {
        var session = sessionFactory.getCurrentSession();
        var hql = "FROM UserTravelHistory WHERE id.username = :username AND tripHistory.date = :date";

        Query<UserTravelHistory> query = session.createQuery(hql, UserTravelHistory.class);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        query.setParameter("username", username);
        query.setParameter("date", date);

        return query.getResultList();
    }

    @Override
    public List<UserTravelHistory> getUserTravelHistory(String username, int offset, int limit) {
        var session = sessionFactory.getCurrentSession();
        var hql = "FROM UserTravelHistory WHERE id.username = :username";

        Query<UserTravelHistory> query = session.createQuery(hql, UserTravelHistory.class);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        query.setParameter("username", username);

        return query.getResultList();
    }

    @Override
    public int getUserTravelHistoryCount(String username) {
        var session = sessionFactory.getCurrentSession();
        // We could also use 10 lines of criteria builder and projections code instead of HQL :)
        var hql = "SELECT COUNT(*) FROM UserTravelHistory WHERE id.username = :username";

        Query query = session.createQuery(hql);
        query.setParameter("username", username);

        return ((Long) query.uniqueResult()).intValue();
    }
}
