package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.BusLine;
import eu.sanjin.kurelic.cbc.repo.entity.CityDescription;
import eu.sanjin.kurelic.cbc.repo.entity.composite.LanguagePrimaryKey;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DestinationInfoDaoImpl implements DestinationInfoDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public DestinationInfoDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public CityDescription getCityDescription(int id, String language) {
        var session = sessionFactory.getCurrentSession();
        return session.get(CityDescription.class, new LanguagePrimaryKey(id, language));
    }

    @Override
    public CityDescription getCityDescription(String cityName, String language) {
        var session = sessionFactory.getCurrentSession();
        String hql = "FROM CityDescription WHERE language = :language AND title= :title";

        Query<CityDescription> cityQuery = session.createQuery(hql, CityDescription.class);
        cityQuery.setParameter("language", language);
        cityQuery.setParameter("title", cityName);
        var cities = cityQuery.getResultList();
        // Cities with same name in different countries are not supported by this implementation
        if(cities.size() != 1) {
            return null;
        }

        return cities.get(0);
    }

    @Override
    public List<CityDescription> getCityDescriptions(String language) {
        var session = sessionFactory.getCurrentSession();
        String hql = "FROM CityDescription WHERE id.language = :language";

        Query<CityDescription> descriptions = session.createQuery(hql, CityDescription.class);
        descriptions.setParameter("language", language);

        return descriptions.getResultList();
    }

    @Override
    public List<CityDescription> getCityDescriptions(String language, Integer... ids) {
        var session = sessionFactory.getCurrentSession();
        String hql = "FROM CityDescription WHERE id.language = :language AND id.id IN (:ids)";

        Query<CityDescription> descriptions = session.createQuery(hql, CityDescription.class);
        descriptions.setParameter("language", language);
        descriptions.setParameterList("ids", ids);

        return descriptions.getResultList();
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