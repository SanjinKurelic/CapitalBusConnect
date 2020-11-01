package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.UserLoginHistory;
import eu.sanjin.kurelic.cbc.repo.entity.UserLoginHistory_;
import eu.sanjin.kurelic.cbc.repo.entity.User_;
import eu.sanjin.kurelic.cbc.repo.entity.composite.LoginHistoryPrimaryKey_;
import eu.sanjin.kurelic.cbc.repo.utility.RowCounter;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Repository
public class UserLoginHistoryDaoImpl implements UserLoginHistoryDao {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<UserLoginHistory> getUserLoginHistory(String username, int offset, int limit) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();

    CriteriaQuery<UserLoginHistory> criteria = builder.createQuery(UserLoginHistory.class);
    Root<UserLoginHistory> root = criteria.from(UserLoginHistory.class);

    // HQL = FROM UserLoginHistory WHERE id.username.username = :username ORDER BY id.dateTime DESC
    criteria.where(usernameEqualPredicate(builder, root, username));
    criteria.orderBy(orderByDateTime(builder, root));

    return entityManager.createQuery(criteria).setFirstResult(offset).setMaxResults(limit).getResultList();
  }

  @Override
  public List<UserLoginHistory> getUserLoginHistory(String username, LocalDate date, int offset, int limit) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();

    CriteriaQuery<UserLoginHistory> criteria = builder.createQuery(UserLoginHistory.class);
    Root<UserLoginHistory> root = criteria.from(UserLoginHistory.class);

    // HQL = FROM UserLoginHistory WHERE id.username.username = :username AND id.dateTime BETWEEN :startDate AND :endDate ORDER BY id.dateTime DESC
    criteria.where(builder.and(usernameEqualPredicate(builder, root, username), datePredicate(builder, root, date)));
    criteria.orderBy(orderByDateTime(builder, root));

    return entityManager.createQuery(criteria).setFirstResult(offset).setMaxResults(limit).getResultList();
  }

  @Override
  public List<UserLoginHistory> getAllLoginHistory(int offset, int limit) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();

    CriteriaQuery<UserLoginHistory> criteria = builder.createQuery(UserLoginHistory.class);
    Root<UserLoginHistory> root = criteria.from(UserLoginHistory.class);

    // HQL = FROM UserLoginHistory ORDER BY id.dateTime DESC
    criteria.orderBy(orderByDateTime(builder, root));

    return entityManager.createQuery(criteria).setFirstResult(offset).setMaxResults(limit).getResultList();
  }

  @Override
  public List<UserLoginHistory> getAllLoginHistory(LocalDate date, int offset, int limit) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();

    CriteriaQuery<UserLoginHistory> criteria = builder.createQuery(UserLoginHistory.class);
    Root<UserLoginHistory> root = criteria.from(UserLoginHistory.class);

    // HQL = FROM UserLoginHistory WHERE id.dateTime BETWEEN :startDate AND :endDate ORDER BY id.dateTime DESC
    criteria.where(datePredicate(builder, root, date));
    criteria.orderBy(orderByDateTime(builder, root));

    return entityManager.createQuery(criteria).setFirstResult(offset).setMaxResults(limit).getResultList();
  }

  @Override
  public Long getUserLoginHistoryCount(String username) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();

    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
    Root<UserLoginHistory> root = criteria.from(UserLoginHistory.class);
    // HQL = SELECT COUNT(*) FROM UserLoginHistory WHERE id.username.username = :username
    criteria.select(builder.count(root));
    criteria.where(usernameEqualPredicate(builder, root, username));

    return entityManager.createQuery(criteria).getSingleResult();
  }

  @Override
  public Long getAllLoginHistoryCount() {
    return RowCounter.countNumberOfRows(entityManager.unwrap(Session.class), UserLoginHistory.class);
  }

  @Override
  public void addUserLoginHistory(UserLoginHistory userLoginHistory) {
    entityManager.unwrap(Session.class).save(userLoginHistory);
  }

  // Utility
  private Predicate usernameEqualPredicate(CriteriaBuilder builder, Root<UserLoginHistory> root, String username) {
    return builder.equal(root.get(UserLoginHistory_.id).get(LoginHistoryPrimaryKey_.username).get(User_.username), username);
  }

  private Predicate datePredicate(CriteriaBuilder builder, Root<UserLoginHistory> root, LocalDate date) {
    LocalDateTime fromDate = (Objects.isNull(date) ? LocalDateTime.MIN : date.atStartOfDay());
    LocalDateTime toDate = (Objects.isNull(date) ? LocalDateTime.MIN : date.atTime(LocalTime.MAX));
    return builder.between(root.get(UserLoginHistory_.id).get(LoginHistoryPrimaryKey_.dateTime), fromDate, toDate);
  }

  private Order orderByDateTime(CriteriaBuilder builder, Root<UserLoginHistory> root) {
    return builder.desc(root.get(UserLoginHistory_.id).get(LoginHistoryPrimaryKey_.dateTime));
  }

}
