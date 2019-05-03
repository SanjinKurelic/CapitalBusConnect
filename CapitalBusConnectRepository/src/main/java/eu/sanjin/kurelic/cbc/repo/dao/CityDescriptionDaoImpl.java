package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.CityDescription;
import eu.sanjin.kurelic.cbc.repo.entity.CityDescription_;
import eu.sanjin.kurelic.cbc.repo.entity.composite.LanguagePrimaryKey;
import eu.sanjin.kurelic.cbc.repo.entity.composite.LanguagePrimaryKey_;
import eu.sanjin.kurelic.cbc.repo.utility.MatchMode;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class CityDescriptionDaoImpl implements CityDescriptionDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CityDescription getCityDescription(int id, String language) {
        return entityManager.unwrap(Session.class).get(CityDescription.class, new LanguagePrimaryKey(id, language));
    }

    @Override
    public CityDescription getCityDescription(String cityName, String language) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CityDescription> criteria = getCriteria(builder);
        Root<CityDescription> root = getRoot(criteria);

        // HQL = FROM CityDescription WHERE id.language = :language AND LOWER(title) = LOWER(:title)
        Predicate equalName = builder.equal(
                builder.lower(root.get(CityDescription_.title)),
                builder.lower(builder.literal(cityName))
        );
        criteria.where(builder.and(languageEqualPredicate(builder, root, language), equalName));

        var cities = entityManager.createQuery(criteria).getResultList();
        // Cities with same name in different countries are not supported by this implementation
        if (cities.size() != 1) {
            return null;
        }

        return cities.get(0);
    }

    /**
     * Get city description using city name in different language (ex. city name in url is on english but we need
     * german description)
     * NOTE: If cityName is not valid, subquery could return more than one row and throw SQLException
     *
     * @param cityName     - name of city on different language
     * @param language     - language for returned description
     * @param nameLanguage - language of city name
     * @return - CityDescription or SQLException (see note)
     */
    @Override
    public CityDescription getCityDescription(String cityName, String language, String nameLanguage) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        // Main query
        CriteriaQuery<CityDescription> criteria = getCriteria(builder);
        Root<CityDescription> root = getRoot(criteria);
        // Subquery
        Subquery<Integer> subquery = criteria.subquery(Integer.class);
        Root<CityDescription> subqueryRoot = subquery.from(CityDescription.class);

        // HQL = FROM CityDescription WHERE id.language = :language AND id.id = <subquery>
        // HQL <subquery> = SELECT id.id FROM CityDescription WHERE id.language = :language AND LOWER(title) = LOWER
        // (:title)

        // SELECT id.id
        subquery.select(subqueryRoot.get(CityDescription_.id).get(LanguagePrimaryKey_.id));
        // <equal_name> = LOWER(title) = LOWER(:title)
        Predicate equalName = builder.equal(
                builder.lower(subqueryRoot.get(CityDescription_.title)),
                builder.lower(builder.literal(cityName))
        );
        // <subquery> = WHERE id.language = :language AND <equal_name>
        subquery.where(builder.and(languageEqualPredicate(builder, subqueryRoot, nameLanguage), equalName));
        // <equal_id> = id.id = <subquery>
        Predicate equalId = builder.equal(root.get(CityDescription_.id).get(LanguagePrimaryKey_.id), subquery);
        // FROM CityDescription WHERE id.language = :language AND <equal_id>
        criteria.where(builder.and(languageEqualPredicate(builder, root, language), equalId));

        var cities = entityManager.createQuery(criteria).getResultList();
        // Cities with same name in different countries are not supported by this implementation
        if (cities.size() != 1) {
            return null;
        }

        return cities.get(0);
    }

    @Override
    public List<CityDescription> getCityDescriptions(String language) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CityDescription> criteria = getCriteria(builder);
        Root<CityDescription> root = getRoot(criteria);

        // HQL = FROM CityDescription WHERE id.language = :language
        criteria.where(languageEqualPredicate(builder, root, language));

        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    public List<CityDescription> getCityDescriptions(String language, Integer... ids) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CityDescription> criteria = getCriteria(builder);
        Root<CityDescription> root = getRoot(criteria);

        // HQL = FROM CityDescription WHERE id.language = :language AND id.id IN (:ids)
        Predicate inId = root.get(CityDescription_.id).get(LanguagePrimaryKey_.id).in((Object[]) ids);
        criteria.where(builder.and(languageEqualPredicate(builder, root, language), inId));

        return entityManager.createQuery(criteria).getResultList();
    }

    /**
     * @param partialCityName - partial name, we search city titles that starts with this argument
     * @param limit           - limit number of result
     * @param language        - language of city
     * @param urlLanguage     - language of first item (what we append in url)
     * @return Tuple - first item is City name URL, second is CityDescription
     */
    @Override
    public List<Tuple> searchCityDescription(String partialCityName, int limit, String language, String urlLanguage) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteria = builder.createQuery(Tuple.class);
        Root<CityDescription> root = getRoot(criteria);

        // SELECT (SELECT title FROM CityDescription WHERE id.id = root.id.id AND id.language = :urlLanguage) AS
        // titleUrl, root
        // FROM CityDescription
        // WHERE id.language = :language AND LOWER(title) LIKE LOWER(CONCAT(:partialCityName,'%'))
        // HAVING titleUrl NOT NULL

        // Subquery
        Subquery<String> subquery = criteria.subquery(String.class);
        Root<CityDescription> subqueryRoot = subquery.from(CityDescription.class);
        subquery.select(subqueryRoot.get(CityDescription_.title));
        Predicate sameId = builder.equal(
                subqueryRoot.get(CityDescription_.id).get(LanguagePrimaryKey_.id),
                root.get(CityDescription_.id).get(LanguagePrimaryKey_.id)
        );
        subquery.where(builder.and(languageEqualPredicate(builder, subqueryRoot, urlLanguage), sameId));
        // Main query
        criteria.multiselect(subquery, root);
        // we are using only DBMS lower function because of inconsistency between Java toLowerCase and DBMS lower
        Expression<String> lowerTitle = builder.lower(root.get(CityDescription_.title));
        Predicate likeName = builder.like(
                lowerTitle,
                builder.lower(builder.literal(MatchMode.startsWith(partialCityName)))
        );
        criteria.where(builder.and(languageEqualPredicate(builder, root, language), likeName,
                builder.isNotNull(subquery)));

        return entityManager.createQuery(criteria).setMaxResults(limit).getResultList();
    }

    // Utility
    private CriteriaQuery<CityDescription> getCriteria(CriteriaBuilder builder) {
        return builder.createQuery(CityDescription.class);
    }

    private Root<CityDescription> getRoot(CriteriaQuery<?> criteria) {
        return criteria.from(CityDescription.class);
    }

    private Predicate languageEqualPredicate(CriteriaBuilder builder, Root<CityDescription> root, String language) {
        return builder.equal(root.get(CityDescription_.id).get(LanguagePrimaryKey_.language), language);
    }
}
