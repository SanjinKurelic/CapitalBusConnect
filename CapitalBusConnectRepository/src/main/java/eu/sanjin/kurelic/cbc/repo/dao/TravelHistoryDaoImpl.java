package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.PayingMethod;
import eu.sanjin.kurelic.cbc.repo.entity.TripHistory;
import eu.sanjin.kurelic.cbc.repo.entity.UserTravelHistory;
import eu.sanjin.kurelic.cbc.repo.values.PayingMethodValues;
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
    public UserTravelHistory getUserTravelHistoryById(int id) {
        var session = sessionFactory.getCurrentSession();
        return session.get(UserTravelHistory.class, id);
    }

    @Override
    public List<UserTravelHistory> getUserTravelHistoryById(Integer... ids) {
        var session = sessionFactory.getCurrentSession();
        var hql = "FROM UserTravelHistory WHERE id IN (:ids)";

        Query<UserTravelHistory> query = session.createQuery(hql, UserTravelHistory.class);
        query.setParameter("ids", ids);

        return query.getResultList();
    }

    @Override
    public List<UserTravelHistory> getUserTravelHistory(String username, LocalDate date, int offset, int limit) {
        var session = sessionFactory.getCurrentSession();
        var hql = "FROM UserTravelHistory WHERE username = :username AND tripHistory.date = :date";

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
        var hql = "FROM UserTravelHistory WHERE username = :username ORDER BY tripHistory.date DESC";

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
        var hql = "SELECT COUNT(*) FROM UserTravelHistory WHERE username = :username";

        Query query = session.createQuery(hql);
        query.setParameter("username", username);

        return ((Long) query.uniqueResult()).intValue();
    }

    @Override
    public boolean addUserTravelHistory(UserTravelHistory userTravelHistory) {
        var session = sessionFactory.getCurrentSession();
        try {
            session.save(userTravelHistory);
        } catch (Exception e) {
            throw e;
            //return false;
        }
        return true;
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

    @Override
    public PayingMethod getPayingMethodByName(PayingMethodValues value) {
        var session = sessionFactory.getCurrentSession();
        var hql = "FROM PayingMethod WHERE name = :name";

        Query<PayingMethod> query = session.createQuery(hql, PayingMethod.class);
        query.setParameter("name", value.name());

        return query.getSingleResult();
    }
}
