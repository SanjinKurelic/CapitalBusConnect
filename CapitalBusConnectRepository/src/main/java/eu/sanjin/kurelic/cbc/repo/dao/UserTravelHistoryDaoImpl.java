package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.UserTravelHistory;
import eu.sanjin.kurelic.cbc.repo.entity.UserTravelHistory_;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;

@Repository
public class UserTravelHistoryDaoImpl implements UserTravelHistoryDao {

    private final SessionFactory sessionFactory;

    public UserTravelHistoryDaoImpl(SessionFactory sessionFactory) {
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

    /**
     * Get users that travel the most.
     *
     * @param limit - show only limited number of list
     * @return List of Tuple classes. First parameter is username (email of user), second parameter is number of travels
     */
    @Override
    public List<Tuple> getTopUsersByTravels(int limit) {
        var session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<Tuple> criteria = builder.createQuery(Tuple.class);
        Root<UserTravelHistory> root = criteria.from(UserTravelHistory.class);
        Expression counter = builder.count(root);

        criteria.multiselect(root.get(UserTravelHistory_.username), counter);
        criteria.groupBy(root.get(UserTravelHistory_.username));
        criteria.orderBy(builder.desc(counter));

        return session.createQuery(criteria).setMaxResults(limit).getResultList();
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
    public Long getAllUserTravelHistoryCount() {
        var session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<UserTravelHistory> root = criteria.from(UserTravelHistory.class);
        criteria.select(builder.count(root));

        return session.createQuery(criteria).getSingleResult();
    }

    @Override
    public boolean addUserTravelHistory(UserTravelHistory userTravelHistory) {
        var session = sessionFactory.getCurrentSession();
        try {
            session.save(userTravelHistory);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
