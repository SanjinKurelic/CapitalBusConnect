package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.UserLoginHistory;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Repository
public class UserLoginInfoDaoImpl implements UserLoginInfoDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserLoginInfoDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<UserLoginHistory> getUserLoginHistory(String username, int offset, int limit) {
        var session = sessionFactory.getCurrentSession();
        var hql = "FROM UserLoginHistory WHERE id.username.username = :username ORDER BY id.dateTime DESC";

        Query<UserLoginHistory> query = session.createQuery(hql, UserLoginHistory.class);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        query.setParameter("username", username);

        return query.getResultList();
    }

    @Override
    public List<UserLoginHistory> getUserLoginHistory(String username, LocalDate date, int offset, int limit) {
        var session = sessionFactory.getCurrentSession();
        var hql = "FROM UserLoginHistory WHERE id.username.username = :username AND DATE(id.dateTime) = :date ORDER BY id.dateTime DESC";

        Query<UserLoginHistory> query = session.createQuery(hql, UserLoginHistory.class);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        query.setParameter("username", username);
        query.setParameter("date", date);

        return query.getResultList();
    }

    @Override
    public List<UserLoginHistory> getAllLoginHistory(int offset, int limit) {
        var session = sessionFactory.getCurrentSession();
        var hql = "FROM UserLoginHistory WHERE id.username.usernam != 'admin@cbc' ORDER BY id.dateTime DESC";

        Query<UserLoginHistory> query = session.createQuery(hql, UserLoginHistory.class);
        query.setFirstResult(offset);
        query.setMaxResults(limit);

        return query.getResultList();
    }

    @Override
    public List<UserLoginHistory> getAllLoginHistory(LocalDate date, int offset, int limit) {
        var session = sessionFactory.getCurrentSession();
        var hql = "FROM UserLoginHistory WHERE DATE(id.dateTime) = :date AND id.username.usernam != 'admin@cbc' ORDER BY id.dateTime DESC";

        Query<UserLoginHistory> query = session.createQuery(hql, UserLoginHistory.class);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        // DATE function in HQL use java.util.date, as date is LocalDate we need to convert it
        query.setParameter("date", Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));

        return query.getResultList();
    }

    @Override
    public int getUserLoginHistoryCount(String username) {
        var session = sessionFactory.getCurrentSession();
        // We could also use 10 lines of criteria builder and projections code instead of HQL :)
        var hql = "SELECT COUNT(*) FROM UserLoginHistory WHERE id.username.username = :username";

        Query query = session.createQuery(hql);
        query.setParameter("username", username);

        return ((Long) query.uniqueResult()).intValue();
    }

    @Override
    public int getAllLoginHistoryCount() {
        var session = sessionFactory.getCurrentSession();
        // We could also use 10 lines of criteria builder and projections code instead of HQL :)
        var hql = "SELECT COUNT(*) FROM UserLoginHistory ";

        return ((Long) session.createQuery(hql).uniqueResult()).intValue();
    }

    // Overkill for making another Util class for just 2 similar methods
    @SuppressWarnings("Duplicates")
    @Override
    public boolean addUserLoginHistory(UserLoginHistory userLoginHistory) {
        var session = sessionFactory.getCurrentSession();
        try {
            session.save(userLoginHistory);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
