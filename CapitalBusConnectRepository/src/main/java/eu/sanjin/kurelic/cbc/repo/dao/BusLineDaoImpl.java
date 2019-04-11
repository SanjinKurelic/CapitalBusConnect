package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.BusLine;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class BusLineDaoImpl implements BusLineDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<BusLine> getCityLines(int offset, int limit) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<BusLine> criteria = builder.createQuery(BusLine.class);
        Root<BusLine> root = criteria.from(BusLine.class);
        criteria.select(root);

        TypedQuery<BusLine> query = entityManager.createQuery(criteria);
        query.setFirstResult(offset * limit);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public Long getNumberOfCityLines() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<BusLine> root = criteria.from(BusLine.class);
        criteria.select(builder.count(root));

        return entityManager.createQuery(criteria).getSingleResult();
    }

}
