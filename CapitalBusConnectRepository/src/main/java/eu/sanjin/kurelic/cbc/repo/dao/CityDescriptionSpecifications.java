package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.BusSchedule;
import eu.sanjin.kurelic.cbc.repo.entity.CityDescription;
import eu.sanjin.kurelic.cbc.repo.entity.CityDescription_;
import eu.sanjin.kurelic.cbc.repo.entity.composite.LanguagePrimaryKey_;
import eu.sanjin.kurelic.cbc.repo.utility.MatchMode;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.Tuple;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

public class CityDescriptionSpecifications {

  public static Specification<BusSchedule> getCityDescriptionForDifferentLanguage () {
    return (Specification<BusSchedule>) (root, criteriaQuery, criteriaBuilder) -> {
      return null;
    };
  }

  public static Specification<Tuple> searchCityDescription (String partialCityName, String language, String urlLanguage) {
    return (Specification<Tuple>) (root, criteriaQuery, criteriaBuilder) -> {
      // SELECT (SELECT title FROM CityDescription WHERE id.id = root.id.id AND id.language = :urlLanguage) AS
      // titleUrl, root
      // FROM CityDescription
      // WHERE id.language = :language AND LOWER(title) LIKE LOWER(CONCAT(:partialCityName,'%'))
      // HAVING titleUrl NOT NULL

      // Subquery
      Subquery<String> subquery = criteriaQuery.subquery(String.class);
      Root<CityDescription> subqueryRoot = subquery.from(CityDescription.class);
      subquery.select(subqueryRoot.get(CityDescription_.title));
      Predicate sameId = criteriaBuilder.equal(
        subqueryRoot.get(CityDescription_.id).get(LanguagePrimaryKey_.id),
        root.get(CityDescription_.id).get(LanguagePrimaryKey_.id)
      );
      subquery.where(criteriaBuilder.and(languageEqualPredicate(builder, subqueryRoot, urlLanguage), sameId));
      // Main query
      criteriaQuery.multiselect(subquery, root);
      // we are using only DBMS lower function because of inconsistency between Java toLowerCase and DBMS lower
      Expression<String> lowerTitle = criteriaBuilder.lower(root.get(CityDescription_.title));
      Predicate likeName = criteriaBuilder.like(
        lowerTitle,
        criteriaBuilder.lower(criteriaBuilder.literal(MatchMode.startsWith(partialCityName)))
      );
      criteriaQuery.where(criteriaBuilder.and(languageEqualPredicate(builder, root, language), likeName,
        criteriaBuilder.isNotNull(subquery)));

      return entityManager.createQuery(criteria).setMaxResults(limit).getResultList();
    };
  }

}
