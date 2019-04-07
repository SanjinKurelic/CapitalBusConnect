package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.TrafficDescription;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class TrafficDescriptionDaoImpl implements TrafficDescriptionDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public TrafficDescriptionDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<TrafficDescription> getTrafficDescriptions(LocalDate date, String language, int limit) {
        var session = sessionFactory.getCurrentSession();
        var hql = "FROM TrafficDescription WHERE traffic.date > :date AND id.language = :language ORDER BY traffic.date ASC";

        Query<TrafficDescription> query = session.createQuery(hql, TrafficDescription.class);
        query.setParameter("language", language);
        query.setParameter("date", date);
        query.setMaxResults(limit);

        return query.getResultList();
    }

}
