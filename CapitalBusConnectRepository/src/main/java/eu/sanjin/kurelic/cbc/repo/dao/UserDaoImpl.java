package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.User;
import eu.sanjin.kurelic.cbc.repo.entity.User_;
import eu.sanjin.kurelic.cbc.repo.utility.MatchMode;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

@Repository
public class UserDaoImpl implements UserDao {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public User getUserInformation(String username) {
    return entityManager.unwrap(Session.class).get(User.class, username);
  }

  @Override
  public boolean hasUserInformation(User user) {
    if (Objects.isNull(user)) {
      return false;
    }
    return entityManager.unwrap(Session.class).contains(user);
  }

  @Override
  public void addUserInformation(User user) {
    entityManager.unwrap(Session.class).save(user);
  }

  @Override
  public void updateUserInformation(User user) {
    entityManager.unwrap(Session.class).update(user);
  }

  @Override
  public void updateUserInformationWithoutPassword(User user) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();

    CriteriaUpdate<User> criteria = builder.createCriteriaUpdate(User.class);
    Root<User> root = criteria.from(User.class);

    // HQL = UPDATE User SET name = :name, surname = :surname, dateOfBirth = :dateOfBirth, receiveNewsletter = :receiveNewsletter WHERE username = :username
    criteria.set(root.get(User_.name), user.getName());
    criteria.set(root.get(User_.surname), user.getSurname());
    criteria.set(root.get(User_.dateOfBirth), user.getDateOfBirth());
    criteria.set(root.get(User_.receiveNewsletter), user.isReceiveNewsletter());
    // Where
    criteria.where(builder.equal(root.get(User_.username), user.getUsername()));
    if (entityManager.createQuery(criteria).executeUpdate() != 1) {
      throw new RuntimeException(); // Rollback transaction
    }
  }

  @Override
  public void removeUserInformation(User user) {
    entityManager.unwrap(Session.class).remove(user);
  }

  @Override
  public List<User> searchUserInformation(String partialUsername, int limit) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();

    CriteriaQuery<User> criteria = builder.createQuery(User.class);
    Root<User> root = criteria.from(User.class);
    // HQL = FROM User WHERE LOWER(username) LIKE LOWER(CONCAT(:partialUsername,'%'))
    criteria.where(builder.like(builder.lower(root.get(User_.username)), builder.lower(builder.literal(MatchMode.startsWith(partialUsername)))));

    return entityManager.createQuery(criteria).setMaxResults(limit).getResultList();
  }
}
