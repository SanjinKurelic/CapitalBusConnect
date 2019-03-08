package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserInfoDaoImpl implements UserInfoDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserInfoDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User getUserInformation(String username) {
        var session = sessionFactory.getCurrentSession();
        return session.get(User.class, username);
    }

    @Override
    public boolean hasUserInformation(User user) {
        var session = sessionFactory.getCurrentSession();
        return session.contains(user);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public boolean addUserInformation(User user) {
        var session = sessionFactory.getCurrentSession();
        try {
            session.save(user);
        } catch (HibernateException e){
            return false;
        }
        return true;
    }

    @Override
    public boolean updateUserInformation(User user) {
        var session = sessionFactory.getCurrentSession();
        try {
            session.update(user);
        } catch (HibernateException e){
            return false;
        }
        return true;
    }

    @Override
    public boolean removeUserInformation(User user) {
        var session = sessionFactory.getCurrentSession();
        try {
            session.remove(user);
        } catch (HibernateException e){
            return false;
        }
        return true;
    }
}
