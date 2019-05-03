package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.TripHistory_;
import eu.sanjin.kurelic.cbc.repo.entity.UserTravelHistory;
import eu.sanjin.kurelic.cbc.repo.entity.UserTravelHistory_;
import eu.sanjin.kurelic.cbc.repo.utility.RowCounter;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class UserTravelHistoryDaoImpl implements UserTravelHistoryDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserTravelHistory getUserTravelHistoryById(int id) {
        return entityManager.unwrap(Session.class).get(UserTravelHistory.class, id);
    }

    @Override
    public List<UserTravelHistory> getUserTravelHistoryByIds(Integer... ids) {
        if (Objects.isNull(ids) || ids.length == 0) {
            return new ArrayList<>();
        }
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<UserTravelHistory> criteria = builder.createQuery(UserTravelHistory.class);
        Root<UserTravelHistory> root = criteria.from(UserTravelHistory.class);
        //HQL = FROM UserTravelHistory WHERE id IN (:ids)
        criteria.where(root.get(UserTravelHistory_.id).in((Object[]) ids));

        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    public List<UserTravelHistory> getUserTravelHistory(String username, LocalDate date, int offset, int limit) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<UserTravelHistory> criteria = builder.createQuery(UserTravelHistory.class);
        Root<UserTravelHistory> root = criteria.from(UserTravelHistory.class);

        // HQL = FROM UserTravelHistory WHERE username = :username AND tripHistory.date = :date
        Predicate datePredicate = builder.equal(root.get(UserTravelHistory_.tripHistory).get(TripHistory_.date), date);
        criteria.where(builder.and(usernameEqualPredicate(builder, root, username), datePredicate));

        return entityManager.createQuery(criteria).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public List<UserTravelHistory> getUserTravelHistory(String username, int offset, int limit) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<UserTravelHistory> criteria = builder.createQuery(UserTravelHistory.class);
        Root<UserTravelHistory> root = criteria.from(UserTravelHistory.class);

        // HQL = FROM UserTravelHistory WHERE username = :username ORDER BY tripHistory.date DESC
        criteria.where(usernameEqualPredicate(builder, root, username));
        criteria.orderBy(builder.desc(root.get(UserTravelHistory_.tripHistory).get(TripHistory_.date)));

        return entityManager.createQuery(criteria).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    /**
     * Get users that travel the most.
     *
     * @param limit - show only limited number of list
     * @return List of Tuple classes. First parameter is username (email of user), second parameter is number of travels
     */
    @Override
    public List<Tuple> getTopUsersByTravels(int limit) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Tuple> criteria = builder.createQuery(Tuple.class);
        Root<UserTravelHistory> root = criteria.from(UserTravelHistory.class);
        Expression counter = builder.count(root);

        criteria.multiselect(root.get(UserTravelHistory_.username), counter);
        criteria.groupBy(root.get(UserTravelHistory_.username));
        criteria.orderBy(builder.desc(counter));

        return entityManager.createQuery(criteria).setMaxResults(limit).getResultList();
    }

    @Override
    public Long getUserTravelHistoryCount(String username) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<UserTravelHistory> root = criteria.from(UserTravelHistory.class);

        // HQL = SELECT COUNT(*) FROM UserTravelHistory WHERE username = :username
        criteria.select(builder.count(root));
        criteria.where(usernameEqualPredicate(builder, root, username));

        return entityManager.createQuery(criteria).getSingleResult();
    }

    @Override
    public Long getAllUserTravelHistoryCount() {
        return RowCounter.countNumberOfRows(entityManager.unwrap(Session.class), UserTravelHistory.class);
    }

    @Override
    public void addUserTravelHistory(UserTravelHistory userTravelHistory) {
        entityManager.unwrap(Session.class).save(userTravelHistory);
        entityManager.flush();
    }

    // Utility
    private Predicate usernameEqualPredicate(CriteriaBuilder builder, Root<UserTravelHistory> root, String username) {
        return builder.equal(root.get(UserTravelHistory_.username), username);
    }

}
