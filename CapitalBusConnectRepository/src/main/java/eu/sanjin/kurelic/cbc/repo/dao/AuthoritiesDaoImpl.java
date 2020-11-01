package eu.sanjin.kurelic.cbc.repo.dao;

import eu.sanjin.kurelic.cbc.repo.entity.Authorities;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class AuthoritiesDaoImpl implements AuthoritiesDao {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public void addAuthorityToUser(Authorities authority) {
    entityManager.unwrap(Session.class).save(authority);
  }
}
