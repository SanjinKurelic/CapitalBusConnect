package eu.sanjin.kurelic.cbc.repo.utility;

import org.hibernate.Session;

public class RowCounter {

    public static Long countNumberOfRows(Session session, Class<?> entity) {
        var builder = session.getCriteriaBuilder();
        var criteria = builder.createQuery(Long.class);
        var root = criteria.from(entity);
        criteria.select(builder.count(root));
        return session.createQuery(criteria).getSingleResult();
    }

}
