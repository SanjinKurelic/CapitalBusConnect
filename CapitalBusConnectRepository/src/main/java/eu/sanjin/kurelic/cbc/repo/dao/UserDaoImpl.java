package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User getUserInformation(String username) {
        var session = sessionFactory.getCurrentSession();
        return session.get(User.class, username);
    }

    @Override
    public boolean hasUserInformation(User user) {
        if(user == null) {
            return false;
        }
        var session = sessionFactory.getCurrentSession();
        return session.contains(user);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public boolean addUserInformation(User user) {
        var session = sessionFactory.getCurrentSession();
        try {
            session.save(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean updateUserInformation(User user) {
        var session = sessionFactory.getCurrentSession();
        try {
            session.update(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean updateUserInformationWithoutPassword(User user) {
        var session = sessionFactory.getCurrentSession();
        var hql = "UPDATE User SET name = :name, surname = :surname, dateOfBirth = :dateOfBirth, receiveNewsletter = :receiveNewsletter WHERE username = :username";

        Query query = session.createQuery(hql);
        query.setParameter("name", user.getName());
        query.setParameter("surname", user.getSurname());
        query.setParameter("dateOfBirth", user.getDateOfBirth());
        query.setParameter("receiveNewsletter", user.isReceiveNewsletter());
        query.setParameter("username", user.getUsername());

        return query.executeUpdate() == 1;
    }

    @Override
    public boolean removeUserInformation(User user) {
        var session = sessionFactory.getCurrentSession();
        try {
            session.remove(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public List<User> searchUserInformation(String partialUsername, int limit) {
        var session = sessionFactory.getCurrentSession();
        var hql = "FROM User WHERE LOWER(username) LIKE LOWER(CONCAT(:partialUsername,'%'))";

        Query<User> query = session.createQuery(hql, User.class);
        query.setMaxResults(limit);
        query.setParameter("partialUsername", partialUsername);

        return query.getResultList();
    }
}
