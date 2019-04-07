package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.PayingMethod;
import eu.sanjin.kurelic.cbc.repo.values.PayingMethodValues;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PayingMethodDaoImpl implements PayingMethodDao {

    private final SessionFactory sessionFactory;

    public PayingMethodDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
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
