package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.Authorities;
import eu.sanjin.kurelic.cbc.repo.entity.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AuthoritiesDaoImpl implements AuthoritiesDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public AuthoritiesDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public boolean addAuthorityToUser(Authorities authority) {
        var session = sessionFactory.getCurrentSession();
        try {
            session.save(authority);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
