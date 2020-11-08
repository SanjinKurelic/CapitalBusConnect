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
public class UserInformationExtendedRepositoryImpl implements UserInformationExtendedRepository {

  @PersistenceContext
  private EntityManager entityManager;

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
}
