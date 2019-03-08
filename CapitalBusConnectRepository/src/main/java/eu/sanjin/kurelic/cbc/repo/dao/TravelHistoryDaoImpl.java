package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.TripHistory;
import eu.sanjin.kurelic.cbc.repo.entity.UserTravelHistory;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TravelHistoryDaoImpl implements TravelHistoryDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public TravelHistoryDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<UserTravelHistory> getUserTravelHistory(String username) {
        var session = sessionFactory.getCurrentSession();
        var hql = "FROM UserTravelHistory WHERE id.username = :username";

        Query<UserTravelHistory> query = session.createQuery(hql, UserTravelHistory.class);
        query.setParameter("username", username);

        return query.getResultList();
    }

    @Override
    public List<TripHistory> getTravelHistory() {
        var session = sessionFactory.getCurrentSession();
        Query<TripHistory> query = session.createQuery("FROM TripHistory", TripHistory.class);
        return query.getResultList();
    }
}
