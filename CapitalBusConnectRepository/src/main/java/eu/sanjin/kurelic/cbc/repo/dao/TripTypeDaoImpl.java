package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.TripType;
import eu.sanjin.kurelic.cbc.repo.values.TripTypeValues;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class TripTypeDaoImpl implements TripTypeDao {

    private final SessionFactory sessionFactory;

    public TripTypeDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public TripType getTripType(TripTypeValues value) {
        var session = sessionFactory.getCurrentSession();
        var hql = "FROM TripType WHERE name = :name";

        Query<TripType> query = session.createQuery(hql, TripType.class);
        query.setParameter("name", value.name());

        return query.getSingleResult();
    }

}
