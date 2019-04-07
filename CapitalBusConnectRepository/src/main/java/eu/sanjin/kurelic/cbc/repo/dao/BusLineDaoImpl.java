package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.BusLine;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BusLineDaoImpl implements BusLineDao {

    private final SessionFactory sessionFactory;

    public BusLineDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<BusLine> getCityLines(int offset, int limit) {
        var session = sessionFactory.getCurrentSession();
        var hql = "FROM BusLine";

        Query<BusLine> query = session.createQuery(hql, BusLine.class);
        query.setFirstResult(offset * limit);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public int getNumberOfCityLines() {
        var session = sessionFactory.getCurrentSession();
        // We could also use 10 lines of criteria builder and projections code instead of HQL :)
        var hql = "SELECT COUNT(*) FROM BusLine";

        return ((Long) session.createQuery(hql).uniqueResult()).intValue();
    }

}
