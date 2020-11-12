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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.List;

@Repository
public class CityDescriptionDaoImpl implements CityDescriptionDao {

  @PersistenceContext
  private EntityManager entityManager;

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
