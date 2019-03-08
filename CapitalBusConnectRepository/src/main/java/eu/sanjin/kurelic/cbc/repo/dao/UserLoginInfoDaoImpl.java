package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.UserLoginHistory;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class UserLoginInfoDaoImpl implements UserLoginInfoDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserLoginInfoDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<UserLoginHistory> getUserLoginHistory(String username) {
        var session = sessionFactory.getCurrentSession();
        var hql = "FROM UserLoginHistory WHERE username = :username ORDER BY dateTime DESC";

        Query<UserLoginHistory> query = session.createQuery(hql, UserLoginHistory.class);
        query.setParameter("username", username);

        return query.getResultList();
    }

    @Override
    public boolean addUserLoginHistory(UserLoginHistory userLoginHistory) {
        var session = sessionFactory.getCurrentSession();
        try {
            session.save(userLoginHistory);
        } catch (HibernateException e) {
            return false;
        }
        return true;
    }

    @Override
    public List<UserLoginHistory> getAllUserLoginHistory(LocalDate fromDate) {
        var session = sessionFactory.getCurrentSession();
        var hql = "FROM UserLoginHistory WHERE dateTime >= :fromDate ORDER BY dateTime DESC";

        Query<UserLoginHistory> query = session.createQuery(hql, UserLoginHistory.class);
        query.setParameter("fromDate", fromDate);

        return query.getResultList();
    }

}
